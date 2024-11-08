// ProfileActivity.java
package com.example.fb_v2.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.fb_v2.Adapter.UserPostsAdapter;
import com.example.fb_v2.Database.DatabasePost;
import com.example.fb_v2.Model.Post;
import com.example.fb_v2.R;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements UserPostsAdapter.OnPostDeletedListener {

    private ImageView userProfileImage;
    private TextView userName;
    private RecyclerView userPostsRecyclerView;
    private UserPostsAdapter userPostsAdapter;
    private List<Post> userPostsList;
    private DatabasePost databasePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        userProfileImage = findViewById(R.id.userProfileImage);
        userName = findViewById(R.id.userName);
        userPostsRecyclerView = findViewById(R.id.userPostsRecyclerView);

        // Get the current user name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("fb_v2", MODE_PRIVATE);
        String currentUser = sharedPreferences.getString("current_user", "Guest");

        // Set user profile information
        userName.setText(currentUser);
        Glide.with(this).load(sharedPreferences.getInt("profile_image", R.drawable.ic_profile)).into(userProfileImage);

        // Initialize database
        databasePost = new DatabasePost(this);

        // Load user posts
        userPostsList = databasePost.getUserPosts(currentUser);

        // Setup RecyclerView
        userPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userPostsAdapter = new UserPostsAdapter(this, userPostsList, this);
        userPostsRecyclerView.setAdapter(userPostsAdapter);
    }

    // Method from OnPostDeletedListener interface
    @Override
    public void onPostDeleted() {
        // Refresh the list of posts after a post is deleted
        SharedPreferences sharedPreferences = getSharedPreferences("fb_v2", MODE_PRIVATE);
        String currentUser = sharedPreferences.getString("current_user", "Guest");
        userPostsList = databasePost.getUserPosts(currentUser);
        userPostsAdapter.notifyDataSetChanged();
    }
}