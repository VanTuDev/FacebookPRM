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

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView postsRecyclerView;
    private RecyclerView usersRecyclerView;
    private PostAdapter postAdapter;
    private UserAdapter userAdapter;
    private List<Post> postList;
    private List<User> userList;
    private ImageView notificationIcon;
    private ImageView messengerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize RecyclerView for posts
        postsRecyclerView = findViewById(R.id.postsRecyclerView);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load posts
        postList = loadPosts();
        postAdapter = new PostAdapter(postList);
        postsRecyclerView.setAdapter(postAdapter);

        // Initialize RecyclerView for users
        usersRecyclerView = findViewById(R.id.usersRecyclerView);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Load users
        userList = loadUsers();
        userAdapter = new UserAdapter(userList);
        usersRecyclerView.setAdapter(userAdapter);

        // Initialize Notification Icon
        notificationIcon = findViewById(R.id.notificationIcon);
        notificationIcon.setOnClickListener(v -> showNotificationDialog());

        // Initialize Messenger Icon
        messengerIcon = findViewById(R.id.messengerIcon);
        messengerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
            startActivity(intent);
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

    // Sample method to load users
    private List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("User A", R.drawable.ic_person_placeholder)); // Use an icon or image
        users.add(new User("User B", R.drawable.ic_person_placeholder));
        return users;
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

    // Adapter for displaying users
    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
        private List<User> userList;

        public UserAdapter(List<User> userList) {
            this.userList = userList;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            User user = userList.get(position);
            holder.userName.setText(user.getName());
            holder.userProfileImage.setImageResource(user.getProfileImage());

            // Handle user selection for chat
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                intent.putExtra("userName", user.getName());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        public class UserViewHolder extends RecyclerView.ViewHolder {
            TextView userName;
            ImageView userProfileImage;

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                userName = itemView.findViewById(R.id.userName);
                userProfileImage = itemView.findViewById(R.id.userProfileImage);
            }
        }
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
