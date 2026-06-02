package com.example.recyclerview.model;

public class Post {

    private int imageResId;
    private String caption;
    private String username;
    private int profileImageResId;
    private boolean isLiked;
    private int likeCount;
    private int commentCount;
    private String timeAgo;

    public Post(int imageResId, String caption, String username, int profileImageResId) {
        this.imageResId = imageResId;
        this.caption = caption;
        this.username = username;
        this.profileImageResId = profileImageResId;
        this.isLiked = false;
        this.likeCount = (int) (Math.random() * 500 + 1);
        this.commentCount = (int) (Math.random() * 100 + 1);
        this.timeAgo = "1 hour ago";
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }

    public void setProfileImageResId(int profileImageResId) {
        this.profileImageResId = profileImageResId;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
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

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }
}