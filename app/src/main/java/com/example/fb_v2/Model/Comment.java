package com.example.fb_v2.Model;

public class Comment {
    private int id;
    private int postId;
    private String userName;
    private String content;
    private String timestamp;

    public Comment(int id, int postId, String userName, String content, String timestamp) {
        this.id = id;
        this.postId = postId;
        this.userName = userName;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Comment(int postId, String userName, String content, String timestamp) {
        this.postId = postId;
        this.userName = userName;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
