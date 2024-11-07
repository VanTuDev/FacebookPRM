// HomeActivity.java

package com.example.fb_v2.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import com.bumptech.glide.request.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.example.fb_v2.Adapter.NotificationAdapter;
import com.example.fb_v2.Adapter.PostAdapter;
import com.example.fb_v2.Database.DatabaseNotification;
import com.example.fb_v2.Database.DatabasePost;
import com.example.fb_v2.Model.Notification;
import com.example.fb_v2.Model.Post;
import com.example.fb_v2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    private DatabasePost databasePost;
    private DatabaseNotification databaseNotification;
    private RecyclerView postsRecyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private ImageView notificationIcon;
    private ImageView messengerIcon;
    private ImageView mapIcon;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private ImageView selectedImageView;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final String CHANNEL_ID = "post_notifications";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String currentUser = getCurrentUser();

        // Initialize the database helper
        databasePost = new DatabasePost(this);
        databaseNotification = new DatabaseNotification(this);

        // Initialize RecyclerView for posts
        postsRecyclerView = findViewById(R.id.postsRecyclerView);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postList = loadPosts();
        postAdapter = new PostAdapter(postList);
        postsRecyclerView.setAdapter(postAdapter);

        LinearLayout statusUpdateSection = findViewById(R.id.statusUpdateSection);
        statusUpdateSection.setOnClickListener(v -> showStatusUpdateDialog());
        // Initialize Notification Icon
        notificationIcon = findViewById(R.id.notificationIcon);
        notificationIcon.setOnClickListener(v -> showNotificationDialog());

        // Initialize Messenger Icon
        messengerIcon = findViewById(R.id.messengerIcon);
        messengerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        mapIcon = findViewById(R.id.mapIcon);
        mapIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
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
                // Navigate to FriendRequestsActivity
                Intent intent = new Intent(HomeActivity.this, FriendRequestsActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.navigation_notifications) {
                showNotificationDialog();
                return true;
            } else if (itemId == R.id.navigation_menu) {
                // Navigate to MenuActivity
                Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });
    }

    private void showStatusUpdateDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_status_update);

        EditText statusEditText = dialog.findViewById(R.id.statusEditText);
        Button selectImageButton = dialog.findViewById(R.id.selectImageButton);
        Button postButton = dialog.findViewById(R.id.postButton);
        selectedImageView = dialog.findViewById(R.id.selectedImageView);

        // Set up image picker button
        selectImageButton.setOnClickListener(v -> openImagePicker());

        // Set up post button
        postButton.setOnClickListener(v -> {
            String statusText = statusEditText.getText().toString().trim();
            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            String imageUriString = selectedImageUri != null ? selectedImageUri.toString() : null;
            String currentUser = getCurrentUser();  // Lấy tên người dùng hiện tại

            if (!statusText.isEmpty()) {
                executorService.execute(() -> {
                    boolean isInserted = databasePost.insertPost(currentUser, statusText, imageUriString);
                    runOnUiThread(() -> {
                        if (isInserted) {
                            Toast.makeText(this, "Status posted!", Toast.LENGTH_SHORT).show();
                            postList.add(new Post(currentUser, statusText, imageUriString, 0, 0));
                            postAdapter.notifyDataSetChanged();
                            dialog.dismiss();

                            // Lưu vào bảng notifications
                            Notification notification = new Notification(currentUser, statusText, currentTime);
                            databaseNotification.insertNotification(notification);

                            sendPostNotification(currentUser, statusText, imageUriString);
                        } else {
                            Toast.makeText(this, "Failed to post status", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            } else {
                Toast.makeText(this, "Please enter a status", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                selectedImageView.setImageBitmap(bitmap);
                selectedImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCurrentUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("fb_v2", MODE_PRIVATE);
        return sharedPreferences.getString("current_user", "Guest");
    }

    // Method to load posts from database
    private List<Post> loadPosts() {
        List<Post> posts = new ArrayList<>();
        Cursor cursor = databasePost.getAllPosts();

        if (cursor != null && cursor.moveToFirst()) {
            int userNameIndex = cursor.getColumnIndex(DatabasePost.COLUMN_USER_NAME);
            int contentIndex = cursor.getColumnIndex(DatabasePost.COLUMN_CONTENT);
            int imageUriIndex = cursor.getColumnIndex(DatabasePost.COLUMN_IMAGE_URI);

            do {
                String userName = cursor.getString(userNameIndex);
                String content = cursor.getString(contentIndex);
                String imageUri = cursor.getString(imageUriIndex);

                posts.add(new Post(userName, content, imageUri, 0, 0));
            } while (cursor.moveToNext());

            cursor.close();
        }
        return posts;
    }

    @SuppressLint("NotificationPermission")
    private void sendPostNotification(String userName, String content, String imageUri) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Post Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for new posts");
            notificationManager.createNotificationChannel(channel);
        }

        String notificationContent = "Bài đăng mới của " + userName + ": " + content;

        // Tạo builder cho thông báo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Bài đăng thành công")
                .setContentText(notificationContent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationContent))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Nếu có hình ảnh, tải ảnh và thêm vào thông báo
        if (imageUri != null && !imageUri.isEmpty()) {
            Glide.with(this)
                    .asBitmap()
                    .load(imageUri)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                            builder.setStyle(new NotificationCompat.BigPictureStyle()
                                    .bigPicture(resource)
                                    .bigLargeIcon((Icon) null));

                            // Hiển thị thông báo sau khi hình ảnh đã tải xong
                            notificationManager.notify(1, builder.build());
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        } else {
            // Nếu không có hình ảnh, chỉ hiển thị thông báo với nội dung văn bản
            notificationManager.notify(1, builder.build());
        }
    }

    // Method to show notification dialog
    private void showNotificationDialog() {
        List<Notification> notifications = databaseNotification.getAllNotifications();
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
        List<Notification> notificationList = databaseNotification.getAllNotifications();

        for (Notification notification : notificationList) {
            String displayText = notification.getContent() + " at " + notification.getTime();
            notifications.add(displayText);
        }

        return notifications;
    }


}
