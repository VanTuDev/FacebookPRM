package com.example.fb_v2.Adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fb_v2.Activity.HomeActivity;
import com.example.fb_v2.Database.DatabasePost;
import com.example.fb_v2.Model.Post;
import com.example.fb_v2.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.postUserName.setText(post.getUserName());
        holder.postText.setText(post.getContent());
        holder.likeCount.setText(post.getLikeCount() + " likes");
        holder.commentCount.setText(post.getCommentCount() + " comments");

        holder.likeIcon.setImageResource(post.isLiked() ? R.drawable.ic_liked : R.drawable.ic_unliked);

        holder.likeIcon.setOnClickListener(v -> {
            post.toggleLike();
            holder.likeCount.setText(post.getLikeCount() + " likes");  // Cập nhật ngay lập tức trên UI

            DatabasePost db = new DatabasePost(holder.itemView.getContext());
            boolean result = db.toggleLike(post.getId(), post.isLiked(), post.getLikeCount());  // Cập nhật trạng thái Like trong database

            Toast.makeText(holder.itemView.getContext(),
                    post.isLiked() ? "Liked" : "Unliked", Toast.LENGTH_SHORT).show();
        });

        if (post.getImageUri() != null && !post.getImageUri().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(post.getImageUri())
                    .placeholder(R.drawable.ic_person_placeholder)
                    .into(holder.postImage);
        } else {
            holder.postImage.setVisibility(View.GONE);
        }

        holder.deleteIcon.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Delete Post")
                    .setMessage("Are you sure you want to delete this post?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        if (holder.itemView.getContext() instanceof HomeActivity) {
                            ((HomeActivity) holder.itemView.getContext()).deletePost(post.getId(), position);
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView postUserName, postText, likeCount, commentCount;
        ImageView postImage, likeIcon, deleteIcon;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postUserName = itemView.findViewById(R.id.postUserName);
            postText = itemView.findViewById(R.id.postText);
            postImage = itemView.findViewById(R.id.postImage);
            likeIcon = itemView.findViewById(R.id.likeIcon);
            likeCount = itemView.findViewById(R.id.likeCount);
            commentCount = itemView.findViewById(R.id.commentCount);
            deleteIcon = itemView.findViewById(R.id.deleteIcon); // Tham chiếu deleteIcon đúng
        }
    }


}