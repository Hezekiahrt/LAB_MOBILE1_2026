package com.example.recyclerview.model;

public class User {

    private String username;
    private String bio;
    private int profileImageResId;
    private int followers;
    private int following;
    private int posts;

    public User(String username, String bio, int profileImageResId) {
        this.username = username;
        this.bio = bio;
        this.profileImageResId = profileImageResId;
        this.followers = (int) (Math.random() * 1000 + 100);
        this.following = (int) (Math.random() * 500 + 50);
        this.posts = (int) (Math.random() * 50 + 5);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }

    public void setProfileImageResId(int profileImageResId) {
        this.profileImageResId = profileImageResId;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public int getPosts() {
        return posts;
    }
}