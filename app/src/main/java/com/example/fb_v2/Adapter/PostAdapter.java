package com.example.fb_v2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        holder.postUserName.setText(post.userName);
        holder.postText.setText(post.content);
        holder.likeCount.setText(post.likeCount + " likes");
        holder.commentCount.setText(post.commentCount + " comments");

        // Load image from URI using Glide
        if (post.imageUri != null && !post.imageUri.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(post.imageUri)
                    .placeholder(R.drawable.ic_person_placeholder) // Optional placeholder image
                    .into(holder.postImage);
        } else {
            holder.postImage.setVisibility(View.GONE); // Hide ImageView if there's no image
        }


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView postUserName, postText, likeCount, commentCount;
        ImageView postImage, likeIcon;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postUserName = itemView.findViewById(R.id.postUserName);
            postText = itemView.findViewById(R.id.postText);
            postImage = itemView.findViewById(R.id.postImage);
            likeIcon = itemView.findViewById(R.id.likeIcon);
            likeCount = itemView.findViewById(R.id.likeCount);
            commentCount = itemView.findViewById(R.id.commentCount);
        }
    }
}
