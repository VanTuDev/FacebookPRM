package com.example.fb_v2.Model;

public class User {
    private String name;
    private int profileImage;
    private String password;

    public User(String name, int profileImage, String password) {
        this.name = name;
        this.profileImage = profileImage;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getProfileImage() {
        return profileImage;
    }
}
