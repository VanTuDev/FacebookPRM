package com.example.fb_v2.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fb_v2.Activity.HomeActivity;
import com.example.fb_v2.Database.DatabaseComment;
import com.example.fb_v2.Database.DatabasePost;
import com.example.fb_v2.Model.Comment;
import com.example.fb_v2.Model.Post;
import com.example.fb_v2.R;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    private final CommentClickListener commentClickListener;

    // Define the CommentClickListener interface
    public interface CommentClickListener {
        void onCommentClick(Post post);
    }

    // Modify constructor to accept CommentClickListener
    public PostAdapter(List<Post> postList, CommentClickListener commentClickListener) {
        this.postList = postList;
        this.commentClickListener = commentClickListener;
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
        holder.postUserName.setText(post.userName);
        holder.postText.setText(post.content);
        holder.likeCount.setText(post.likeCount + " likes");
        holder.commentCount.setText(post.commentCount + " comments");

        holder.commentIcon.setOnClickListener(v -> commentClickListener.onCommentClick(post));

        holder.likeIcon.setImageResource(post.isLiked ? R.drawable.ic_liked : R.drawable.ic_unliked);

        holder.likeIcon.setOnClickListener(v -> {
            post.toggleLike();
            notifyItemChanged(position);

            DatabasePost db = new DatabasePost(holder.itemView.getContext());
            boolean result = db.toggleLike(post.getId(), post.isLiked);

            Toast.makeText(holder.itemView.getContext(),
                    post.isLiked ? "Liked" : "Unliked", Toast.LENGTH_SHORT).show();
        });

        if (post.imageUri != null && !post.imageUri.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(post.imageUri)
                    .placeholder(R.drawable.ic_person_placeholder)
                    .into(holder.postImage);
        } else {
            holder.postImage.setVisibility(View.GONE);
        }

        // Delete button click listener
        holder.deleteIcon.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Xóa Post")
                    .setMessage("Bạn có muốn xóa bài post này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        if (holder.itemView.getContext() instanceof HomeActivity) {
                            ((HomeActivity) holder.itemView.getContext()).deletePost(post.getId(), position);
                        }
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                    .show();
        });





        if (post.imageUri != null && !post.imageUri.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(post.imageUri)
                    .placeholder(R.drawable.ic_person_placeholder)
                    .into(holder.postImage);
        } else {
            holder.postImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView postUserName, postText, likeCount, commentCount;
        ImageView postImage, likeIcon, deleteIcon, commentIcon;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postUserName = itemView.findViewById(R.id.postUserName);
            postText = itemView.findViewById(R.id.postText);
            postImage = itemView.findViewById(R.id.postImage);
            likeIcon = itemView.findViewById(R.id.likeIcon);
            likeCount = itemView.findViewById(R.id.likeCount);
            commentIcon = itemView.findViewById(R.id.commentIcon);
            commentCount = itemView.findViewById(R.id.commentCount);
            deleteIcon = itemView.findViewById(R.id.deleteIcon); // Tham chiếu deleteIcon đúng
        }
    }
}