package com.example.fb_v2.Model;

public class Post {
    public int id;
    public String userName;
    public String content;
    public String imageUri;
    public int likeCount;
    public int commentCount;
    public boolean isLiked;

    // Constructor with all parameters, including id and isLiked
    public Post(int id, String userName, String content, String imageUri, int likeCount, int commentCount, boolean isLiked) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.imageUri = imageUri;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isLiked = isLiked;
    }

    // Constructor without id and isLiked, for new posts
    public Post(String userName, String content, String imageUri, int likeCount, int commentCount) {
        this.userName = userName;
        this.content = content;
        this.imageUri = imageUri;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isLiked = false; // Default value for new posts
    }

    public int getId() {
        return id;
    }

    public void toggleLike() {
        isLiked = !isLiked;
        likeCount += isLiked ? 1 : -1;
    }
}
