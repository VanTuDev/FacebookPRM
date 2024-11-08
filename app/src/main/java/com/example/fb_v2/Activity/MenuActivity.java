// MenuActivity.java
package com.example.fb_v2.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fb_v2.Adapter.MenuAdapter;
import com.example.fb_v2.Model.MenuItems;
import com.example.fb_v2.R;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView menuRecyclerView;
    private MenuAdapter menuAdapter;
    private List<MenuItems> menuItemList;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // Gắn layout activity_menu

        // Khởi tạo RecyclerView
        menuRecyclerView = findViewById(R.id.menuRecyclerView);
        menuRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Hiển thị dưới dạng lưới với 2 cột

        // Tải dữ liệu cho menu
        menuItemList = loadMenuItems();
        menuAdapter = new MenuAdapter(menuItemList);
        menuRecyclerView.setAdapter(menuAdapter);

        // Xử lý sự kiện nhấn vào mục menu
        menuAdapter.setOnItemClickListener(item -> {
            switch (item.getName()) {
                case "Bạn bè":
                    startActivity(new Intent(MenuActivity.this, FriendRequestsActivity.class));
                    break;

                case "Profile":
                case "Add Friend": // Dẫn đến ProfileActivity cho cả hai
                    startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
                    break;

                case "Đăng xuất":
                    handleLogout();
                    break;

                case "Nghe nhạc":
                    startActivity(new Intent(MenuActivity.this, MusicActivity.class));
                    break;

                default:
                    // Xử lý các mục khác nếu có
                    break;
            }
        });

        // Khởi tạo và gán sự kiện cho nút quay lại
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Đóng MenuActivity để không còn trong stack quay lại
        });
    }

    // Hàm để tải danh sách các mục menu
    private List<MenuItems> loadMenuItems() {
        List<MenuItems> items = new ArrayList<>();
        items.add(new MenuItems("Tin nhắn", R.drawable.ic_messenger));
        items.add(new MenuItems("Bạn bè", R.drawable.ic_friends));
        items.add(new MenuItems("Video", R.drawable.ic_video));
        items.add(new MenuItems("Profile", R.drawable.ic_profile));
        items.add(new MenuItems("Add Friend", R.drawable.ic_friends));
        items.add(new MenuItems("Đăng xuất", R.drawable.ic_messenger));
        items.add(new MenuItems("Nghe nhạc", R.drawable.ic_music));
        return items;
    }

    // Xử lý đăng xuất
    private void handleLogout() {
        SharedPreferences sharedPreferences = getSharedPreferences("fb_v2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", false);
        editor.apply();

        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Đóng MenuActivity để quay lại MainActivity sau khi đăng xuất
    }
}
