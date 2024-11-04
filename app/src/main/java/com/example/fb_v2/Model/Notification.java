package com.example.fb_v2.Model;

public class Notification {
    private String content;
    private String time;
    private int profileImageResId;

    public Notification(String content, String time, int profileImageResId) {
        this.content = content;
        this.time = time;
        this.profileImageResId = profileImageResId;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }
}
