package com.example.fb_v2.Model;

public class Post {
    private int id;
    private String userName;
    private String content;
    private String imageUri;
    private int likeCount;
    private int commentCount;
    private boolean isLiked;

    // Constructor with all parameters, including id and isLiked
    public Post(int id, String userName, String content, String imageUri, int likeCount, int commentCount, boolean isLiked) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.imageUri = imageUri;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isLiked = isLiked;
    }

    // Constructor without id and isLiked, for new posts
    public Post(String userName, String content, String imageUri, int likeCount, int commentCount) {
        this.userName = userName;
        this.content = content;
        this.imageUri = imageUri;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isLiked = false; // Default value for new posts
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    // Method to toggle like status and adjust like count
    public void toggleLike() {
        isLiked = !isLiked;
        likeCount += isLiked ? 1 : -1;
    }

    // Method to update content and image URI
    public void updateContent(String newContent, String newImageUri) {
        this.content = newContent;
        this.imageUri = newImageUri;
    }

    // Method to increment comment count
    public void incrementCommentCount() {
        this.commentCount++;
    }

    // Method to decrement comment count
    public void decrementCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", likeCount=" + likeCount +
                ", commentCount=" + commentCount +
                ", isLiked=" + isLiked +
                '}';
    }
}
