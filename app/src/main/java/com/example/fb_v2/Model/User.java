package com.example.fb_v2.Model;

public class User {
    private String name;
    private int profileImage;

    public User(String name, int profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public int getProfileImage() {
        return profileImage;
    }
}
