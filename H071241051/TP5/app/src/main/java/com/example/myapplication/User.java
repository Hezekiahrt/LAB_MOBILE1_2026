package com.example.myapplication;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String username;
    private String bio;
    private String profileImageUri;
    private int profileImageRes = -1;

    public User(String name, String username, String bio, String profileImageUri) {
        this.name = name;
        this.username = username;
        this.bio = bio;
        this.profileImageUri = profileImageUri;
    }

    public User(String name, String username, String bio, int profileImageRes) {
        this.name = name;
        this.username = username;
        this.bio = bio;
        this.profileImageRes = profileImageRes;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getProfileImageUri() { return profileImageUri; }
    public void setProfileImageUri(String profileImageUri) { this.profileImageUri = profileImageUri; }
    public int getProfileImageRes() { return profileImageRes; }
    public void setProfileImageRes(int profileImageRes) { this.profileImageRes = profileImageRes; }
}