package com.example.fb_v2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fb_v2.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button loginButton = findViewById(R.id.loginButton);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        LinearLayout toastError = findViewById(R.id.toastError);
        LinearLayout toastDone = findViewById(R.id.toastDone);
        LinearLayout toastWrong = findViewById(R.id.toastWrong);

        // Set initial visibility of toast messages to GONE        toastError.setVisibility(View.GONE);
        toastDone.setVisibility(View.GONE);
        toastWrong.setVisibility(View.GONE);


        loginButton.setOnClickListener(v -> {
            String usernameText = username.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            Log.d(TAG, "Username: " + usernameText + ", Password: " + passwordText);


            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                showSnackbar(v, "Bạn phải nhập đầy đủ vào", toastError);
            }
            else if (usernameText.equals("0399604816") && passwordText.equals("123456")) {
                showSnackbar(v, "Đăng nhập thành công", toastDone);
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }, 1000);
            }
            else {
                showSnackbar(v, "Có thể sai tài khoản hoặc mật khẩu", toastWrong);
            }
        });
    }


    private void showSnackbar(View view, String message, LinearLayout toastType) {
        toastType.setVisibility(View.VISIBLE);
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();

        new Handler().postDelayed(() -> {
            toastType.setVisibility(View.GONE);
        }, 3000);
    }
}
