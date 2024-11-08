// UserPostsAdapter.java
package com.example.fb_v2.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.fb_v2.Database.DatabasePost;
import com.example.fb_v2.Model.Post;
import com.example.fb_v2.R;

import java.util.List;

public class UserPostsAdapter extends RecyclerView.Adapter<UserPostsAdapter.UserPostViewHolder> {

    private List<Post> userPostsList;
    private Context context;
    private DatabasePost databasePost;
    private OnPostDeletedListener postDeletedListener;

    public UserPostsAdapter(Context context, List<Post> userPostsList, OnPostDeletedListener postDeletedListener) {
        this.context = context;
        this.userPostsList = userPostsList;
        this.databasePost = new DatabasePost(context);
        this.postDeletedListener = postDeletedListener;
    }

    @NonNull
    @Override
    public UserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new UserPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostViewHolder holder, int position) {
        Post post = userPostsList.get(position);
        holder.postUserName.setText(post.userName);
        holder.postText.setText(post.content);
        holder.likeCount.setText(post.likeCount + " likes");
        holder.commentCount.setText(post.commentCount + " comments");

        // Load post image if available
        if (post.imageUri != null && !post.imageUri.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(post.imageUri)
                    .placeholder(R.drawable.ic_person_placeholder)  // Placeholder image
                    .into(holder.postImage);
        } else {
            holder.postImage.setVisibility(View.GONE);
        }

        // Set up delete button
        holder.deleteIcon.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa bài đăng")
                    .setMessage("Bạn có chắc chắn muốn xóa bài đăng này không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // Xóa bài đăng từ cơ sở dữ liệu
                        boolean isDeleted = databasePost.deletePost(post.getId());
                        if (isDeleted) {
                            userPostsList.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Bài đăng đã được xóa", Toast.LENGTH_SHORT).show();
                            if (postDeletedListener != null) {
                                postDeletedListener.onPostDeleted();
                            }
                        } else {
                            Toast.makeText(context, "Xóa bài đăng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return userPostsList.size();
    }

    static class UserPostViewHolder extends RecyclerView.ViewHolder {

        TextView postUserName, postText, likeCount, commentCount;
        ImageView postImage, deleteIcon;

        public UserPostViewHolder(@NonNull View itemView) {
            super(itemView);
            postUserName = itemView.findViewById(R.id.postUserName);
            postText = itemView.findViewById(R.id.postText);
            postImage = itemView.findViewById(R.id.postImage);
            likeCount = itemView.findViewById(R.id.likeCount);
            commentCount = itemView.findViewById(R.id.commentCount);
            deleteIcon = itemView.findViewById(R.id.deleteIcon); // Thêm icon delete trong layout
        }
    }

    // Interface để thông báo khi một bài đăng bị xóa
    public interface OnPostDeletedListener {
        void onPostDeleted();
    }
}