package com.example.fb_v2.Model;

public class Video {
    private String videoUrl, title, desc, username;
    private int avatarUrl;

    public Video(String videoUrl, String title, String desc, String username, int avatarUrl) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.desc = desc;
        this.username = username;
        this.avatarUrl = avatarUrl;
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

    public int getAvatarUrl() {
        return avatarUrl;
    }
}
