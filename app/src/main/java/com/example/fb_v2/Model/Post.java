package com.example.fb_v2.Model;

public class Post {
    public String userName;
    public String content;
    public String imageUri; // Lưu URI thay vì resource ID
    public int likeCount;
    public int commentCount;
    public boolean isLiked; // Trạng thái "like"

    public Post(String userName, String content, String imageUri, int likeCount, int commentCount) {
        this.userName = userName;
        this.content = content;
        this.imageUri = imageUri;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isLiked = false; // Mặc định là chưa "like"
    }
}
