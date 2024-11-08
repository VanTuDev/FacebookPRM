package com.example.fb_v2.Model;

public class Music {
    private String title;
    private String artist;
    private int fileResId;

    public Music(String title, String artist, int fileResId) {
        this.title = title;
        this.artist = artist;
        this.fileResId = fileResId;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getFileResId() { return fileResId; }
}
