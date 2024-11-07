// MenuActivity.java
package com.example.fb_v2.Activity;

import android.content.Intent;
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
        setContentView(R.layout.activity_menu);

        // Initialize RecyclerView
        menuRecyclerView = findViewById(R.id.menuRecyclerView);
        menuRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Set 2 columns

        // Load menu items
        menuItemList = loadMenuItems();
        menuAdapter = new MenuAdapter(menuItemList);
        menuRecyclerView.setAdapter(menuAdapter);

        // Set item click listener
        menuAdapter.setOnItemClickListener(item -> {
            if ("Bạn bè".equals(item.getName())) {  // Check if the "Friends" item was clicked
                Intent intent = new Intent(MenuActivity.this, FriendRequestsActivity.class);
                startActivity(intent);
            }
            // Add more cases if other menu items need special handling
        });

        // Initialize and set click listener for back button
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Optional: close MenuActivity so it won’t stay in the back stack
        });
    }

    private List<MenuItems> loadMenuItems() {
        List<MenuItems> items = new ArrayList<>();
        items.add(new MenuItems("Tin nhắn", R.drawable.ic_messenger));
        items.add(new MenuItems("Bạn bè", R.drawable.ic_friends)); // "Friends" item
        items.add(new MenuItems("Video", R.drawable.ic_video));
        // Add more items as per your design
        return items;
    }
}
