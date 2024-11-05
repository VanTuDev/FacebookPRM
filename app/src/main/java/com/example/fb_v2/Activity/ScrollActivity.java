package com.example.fb_v2.Activity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fb_v2.Adapter.VideoAdapter;
import com.example.fb_v2.Model.Video;
import com.example.fb_v2.R;

import java.util.ArrayList;
import java.util.List;

public class ScrollActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private List<Video> videoList;
    private VideoAdapter videoAdapter;

    private boolean isMuted = false;  // Track mute state
    private ImageButton muteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scroll);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        videoList = new ArrayList<>();
        viewPager2 = findViewById(R.id.viewPager2);
        ImageButton backBtn = findViewById(R.id.btnBack);
        muteBtn = findViewById(R.id.btnMute);

        videoList.add(new Video("android.resource://" + getPackageName() + "/" + R.raw.a, "New Title", "This video is amazing!!!!!", "Vantu"));
        videoList.add(new Video("android.resource://" + getPackageName() + "/" + R.raw.b, "New Title", "This video is amazing!!!!!", "Vantu" ));
        videoList.add(new Video("android.resource://" + getPackageName() + "/" + R.raw.c, "New Title", "This video is amazing!!!!!" , "Vantu"));
        videoList.add(new Video("android.resource://" + getPackageName() + "/" + R.raw.d, "New Title", "This video is amazing!!!!!", "Vantu" ));
        videoList.add(new Video("android.resource://" + getPackageName() + "/" + R.raw.e, "New Title", "This video is amazing!!!!!", "Vantu" ));
        videoList.add(new Video("android.resource://" + getPackageName() + "/" + R.raw.f, "New Title", "This video is amazing!!!!!", "Vantu" ));
        videoList.add(new Video("android.resource://" + getPackageName() + "/" + R.raw.g, "New Title", "This video is amazing!!!!!", "Vantu" ));
        videoList.add(new Video("android.resource://" + getPackageName() + "/" + R.raw.h, "New Title", "This video is amazing!!!!!", "Vantu" ));

        videoAdapter = new VideoAdapter(videoList);
        viewPager2.setAdapter(videoAdapter);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ScrollActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        // Toggle mute state
        muteBtn.setOnClickListener(v -> {
            isMuted = !isMuted;
            muteBtn.setImageResource(isMuted ? R.drawable.baseline_volume_off_24 : R.drawable.baseline_volume_down_24);
            toggleMute();
        });
    }

    private void toggleMute() {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, isMuted);
    }
}