package com.example.fb_v2.Model;

public class Notification {
    private String userName;    // Tên người dùng
    private String content;     // Nội dung bài đăng
    private String time;        // Thời gian đăng

    public Notification(String userName, String content, String time) {
        this.userName = userName;
        this.content = content;
        this.time = time;
    }

    // Getter cho các thuộc tính
    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}
