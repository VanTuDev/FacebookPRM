package com.example.fb_v2.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fb_v2.Adapter.MusicAdapter;
import com.example.fb_v2.Model.Music;
import com.example.fb_v2.R;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {

    private RecyclerView musicRecyclerView;
    private MusicAdapter musicAdapter;
    private List<Music> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music); // Gắn layout cho MusicActivity

        // Khởi tạo RecyclerView
        musicRecyclerView = findViewById(R.id.musicRecyclerView);
        musicRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo dữ liệu giả cho danh sách nhạc
        musicList = loadMusicItems();
        musicAdapter = new MusicAdapter(musicList);
        musicRecyclerView.setAdapter(musicAdapter);
    }

    private List<Music> loadMusicItems() {
        List<Music> items = new ArrayList<>();
        items.add(new Music("Piano ", "Coong An", R.raw.m1));
        items.add(new Music("Rap Viet", "MCK", R.raw.m1));
        items.add(new Music("Chim sáo mồ côi", "Cẩm ly", R.raw.m1));
        items.add(new Music("Cơn mưa ngang qua", "Sơn Tùng MTP", R.raw.m1));

        return items;
    }
}
