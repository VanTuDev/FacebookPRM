package com.example.fb_v2.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fb_v2.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String userName = getIntent().getStringExtra("userName");
        setTitle("Chat with " + userName);
    }
}