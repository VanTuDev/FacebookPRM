package com.example.fb_v2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fb_v2.Adapter.ChatActivity;
import com.example.fb_v2.Model.Post;
import com.example.fb_v2.Model.User;
import com.example.fb_v2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView postsRecyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private ImageView notificationIcon;
    private ImageView messengerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize RecyclerView for posts
        postsRecyclerView = findViewById(R.id.postsRecyclerView);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postList = loadPosts();
        postAdapter = new PostAdapter(postList);
        postsRecyclerView.setAdapter(postAdapter);

        // Initialize Notification Icon
        notificationIcon = findViewById(R.id.notificationIcon);
        notificationIcon.setOnClickListener(v -> showNotificationDialog());

        // Initialize Messenger Icon
        messengerIcon = findViewById(R.id.messengerIcon);
        messengerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // Initialize Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                // Already on Home, handle accordingly
                return true;
            } else if (itemId == R.id.navigation_video) {
                // Handle video action
                return true;
            } else if (itemId == R.id.navigation_friends) {
                // Handle friends action
                return true;
            } else if (itemId == R.id.navigation_notifications) {
                showNotificationDialog();
                return true;
            } else if (itemId == R.id.navigation_menu) {
                // Handle menu action
                return true;
            }

            return false;
        });
    }

    // Sample method to load posts
    private List<Post> loadPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("Văn Tú", "Nhà Hàng này tuyệt vời !, Đồ ăn ngon ", R.drawable.post1, 25, 10));
        posts.add(new Post("Nguyễn An", "Đi suối nè anh em !!!!", R.drawable.post2, 15, 5));
        posts.add(new Post("Minh Hoàng", "Thử một quán cafe mới hôm nay, view rất đẹp!", R.drawable.post3, 20, 8));
        posts.add(new Post("Lê Hương", "Ngày hôm nay thật tuyệt vời!", R.drawable.post4, 30, 12));
        return posts;
    }

    // Method to show notification dialog
    private void showNotificationDialog() {
        List<String> notifications = loadNotifications();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_notifications, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.notificationsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NotificationAdapter(notifications));

        builder.setView(dialogView)
                .setPositiveButton("Đóng", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Sample method to load notifications
    private List<String> loadNotifications() {
        List<String> notifications = new ArrayList<>();
        notifications.add("Bạn có 5 lượt thích mới trên bài đăng của bạn.");
        notifications.add("Nguyễn An đã bình luận: 'Tuyệt vời!'");
        notifications.add("Minh Hoàng đã gửi cho bạn một tin nhắn mới.");
        notifications.add("Lê Hương đã nhắc đến bạn trong một bình luận.");
        return notifications;
    }

    // Adapter for displaying notifications
    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
        private List<String> notificationList;

        public NotificationAdapter(List<String> notificationList) {
            this.notificationList = notificationList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.notificationText.setText(notificationList.get(position));
        }

        @Override
        public int getItemCount() {
            return notificationList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView notificationText;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                notificationText = itemView.findViewById(R.id.notificationText);
            }
        }
    }
}
