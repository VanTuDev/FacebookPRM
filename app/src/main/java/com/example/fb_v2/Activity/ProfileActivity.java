package com.example.fb_v2.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.fb_v2.Adapter.UserPostsAdapter;
import com.example.fb_v2.Database.DatabasePost;
import com.example.fb_v2.Database.DatabaseUser;
import com.example.fb_v2.Model.Post;
import com.example.fb_v2.R;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements UserPostsAdapter.OnPostDeletedListener {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView userProfileImage;
    private TextView userName;
    private RecyclerView userPostsRecyclerView;
    private UserPostsAdapter userPostsAdapter;
    private List<Post> userPostsList;
    private DatabasePost databasePost;
    private DatabaseUser databaseUser;
    private Button changeAvatarButton, changeNameButton;
    private Uri selectedImageUri;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        userProfileImage = findViewById(R.id.userProfileImage);
        userName = findViewById(R.id.userName);
        userPostsRecyclerView = findViewById(R.id.userPostsRecyclerView);
        changeNameButton = findViewById(R.id.changeNameButton);

        // Initialize database objects
        databasePost = new DatabasePost(this);
        databaseUser = new DatabaseUser(this);

        // Get the current user name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("fb_v2", MODE_PRIVATE);
        currentUser = sharedPreferences.getString("current_user", "Guest");

        // Set user profile information
        userName.setText(currentUser);
        int profileImageResId = R.drawable.avata; // Sử dụng ảnh đại diện mặc định
        userProfileImage.setImageResource(profileImageResId);

        // Load user posts and set up RecyclerView
        userPostsList = databasePost.getUserPosts(currentUser);
        userPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userPostsAdapter = new UserPostsAdapter(this, userPostsList, this);
        userPostsRecyclerView.setAdapter(userPostsAdapter);

        // Set up listeners
        changeNameButton.setOnClickListener(v -> showChangeNameDialog());
    }




    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            userProfileImage.setImageURI(selectedImageUri);

            // Update database and SharedPreferences
            boolean isUpdated = databaseUser.updateUserProfile(currentUser, currentUser, selectedImageUri.toString());
            if (isUpdated) {
                SharedPreferences sharedPreferences = getSharedPreferences("fb_v2", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profile_image", selectedImageUri.toString());
                editor.apply();
                Toast.makeText(this, "Đã cập nhật ảnh đại diện", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Không thể cập nhật ảnh đại diện", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showChangeNameDialog() {
        EditText newNameInput = new EditText(this);
        newNameInput.setHint("Nhập tên mới");

        new AlertDialog.Builder(this)
                .setTitle("Đổi tên")
                .setView(newNameInput)
                .setPositiveButton("Đổi", (dialog, which) -> {
                    String newUserName = newNameInput.getText().toString().trim();

                    if (!newUserName.isEmpty()) {
                        // Kiểm tra nếu tên mới đã tồn tại
                        if (databaseUser.isUserNameExists(newUserName)) {
                            Toast.makeText(this, "Tên đã tồn tại, vui lòng chọn tên khác", Toast.LENGTH_SHORT).show();
                        } else {
                            // Cập nhật tên người dùng và tất cả các bài đăng của họ
                            boolean isUserNameUpdated = databaseUser.updateUserName(currentUser, newUserName);
                            boolean isUserNameUpdatedInPosts = databasePost.updateUserNameInPosts(currentUser, newUserName);

                            if (isUserNameUpdated && isUserNameUpdatedInPosts) {
                                userName.setText(newUserName);

                                // Cập nhật SharedPreferences với tên người dùng mới
                                SharedPreferences sharedPreferences = getSharedPreferences("fb_v2", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("current_user", newUserName);
                                editor.apply();

                                currentUser = newUserName; // Cập nhật biến currentUser
                                Toast.makeText(this, "Tên đã được thay đổi", Toast.LENGTH_SHORT).show();

                                // Tải lại bài đăng với tên mới và cập nhật RecyclerView
                                userPostsList.clear();
                                userPostsList.addAll(databasePost.getUserPosts(currentUser));
                                userPostsAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(this, "Không thể đổi tên", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }


    @Override
    public void onPostDeleted() {
        userPostsList.clear();
        userPostsList.addAll(databasePost.getUserPosts(currentUser));
        userPostsAdapter.notifyDataSetChanged();
    }

}


