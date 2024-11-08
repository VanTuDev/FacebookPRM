package com.example.fb_v2.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fb_v2.Database.DatabaseUser;
import com.example.fb_v2.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DatabaseUser db;

    private void saveCurrentUser(String username, String profileImageUri) {
        SharedPreferences sharedPreferences = getSharedPreferences("fb_v2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_user", username);
        editor.putString("profile_image", profileImageUri); // Lưu dưới dạng String
        editor.apply();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DatabaseUser
        db = new DatabaseUser(this);

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
        TextView signupText = findViewById(R.id.signupText);

        // Set initial visibility of toast messages to GONE
        toastError.setVisibility(View.GONE);
        toastDone.setVisibility(View.GONE);
        toastWrong.setVisibility(View.GONE);

        // Login button click listener
        loginButton.setOnClickListener(v -> {
            String usernameText = username.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                showSnackbar(v, "Bạn phải nhập đầy đủ vào", toastError);
            } else if (db.checkUser(usernameText, passwordText)) {
                // Trong phần xử lý đăng nhập:
                String profileImageUri = db.getUserProfileImage(usernameText);
                saveCurrentUser(usernameText, profileImageUri);
                // Lưu cả tên và hình ảnh người dùng

                showSnackbar(v, "Đăng nhập thành công", toastDone);
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }, 1000);
            } else {
                showSnackbar(v, "Có thể sai tài khoản hoặc mật khẩu", toastWrong);
            }
        });

        // Signup text click listener to go to RegisterActivity
        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
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