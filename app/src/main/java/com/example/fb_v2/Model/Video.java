package com.example.fb_v2.Model;

public class Video {
    private String videoUrl, title, desc, username;

    public Video(String videoUrl, String title, String desc, String username) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.desc = desc;
        this.username = username;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getUsername() {return  username; }
}
