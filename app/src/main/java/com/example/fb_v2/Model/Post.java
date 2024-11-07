package com.example.fb_v2.Model;

public class Post {
    public int id; // Thêm thuộc tính id để lưu postId
    public String userName;
    public String content;
    public String imageUri;
    public int likeCount;
    public int commentCount;

    public Post(int id, String userName, String content, String imageUri, int likeCount, int commentCount) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.imageUri = imageUri;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    // Constructor không có id (sử dụng khi tạo bài đăng mới trước khi lưu vào cơ sở dữ liệu)
    public Post(String userName, String content, String imageUri, int likeCount, int commentCount) {
        this.userName = userName;
        this.content = content;
        this.imageUri = imageUri;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }
}
