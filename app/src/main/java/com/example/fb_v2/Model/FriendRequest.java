package com.example.fb_v2.Model;

public class FriendRequest {
    private String name;
    private int mutualFriendsCount;
    private int profileImageUrl;
    private String timestamp;
    private boolean isAccepted;

    public FriendRequest(String name, int mutualFriendsCount, int profileImageUrl, String timestamp) {
        this.name = name;
        this.mutualFriendsCount = mutualFriendsCount;
        this.profileImageUrl = profileImageUrl;
        this.timestamp = timestamp;
        this.isAccepted = false;
    }

    public String getName() { return name; }
    public int getMutualFriendsCount() { return mutualFriendsCount; }
    public int getProfileImageUrl() { return profileImageUrl; }
    public String getTimestamp() { return timestamp; }
    public boolean isAccepted() { return isAccepted; }
    public void setAccepted(boolean accepted) { isAccepted = accepted; }
}
