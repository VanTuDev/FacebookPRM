package com.example.fb_v2.Model;

public class Post {
    public String userName;
    public String content;
    public int imageResource;
    public int likeCount;
    public int commentCount;

    public Post(String userName, String content, int imageResource, int likeCount, int commentCount) {
        this.userName = userName;
        this.content = content;
        this.imageResource = imageResource;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
