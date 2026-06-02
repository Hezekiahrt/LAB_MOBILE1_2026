package com.example.myapplication;

import java.io.Serializable;

public class Post implements Serializable {
    private String username;
    private String profileImageUri;
    private int profileImageRes = -1;
    private String postImageUri;
    private int postImageRes = -1;
    private String caption;

    public Post(String username, String profileImageUri, String postImageUri, String caption) {
        this.username = username;
        this.profileImageUri = profileImageUri;
        this.postImageUri = postImageUri;
        this.caption = caption;
    }

    public Post(String username, String profileImageUri, int postImageRes, String caption) {
        this.username = username;
        this.profileImageUri = profileImageUri;
        this.postImageRes = postImageRes;
        this.caption = caption;
    }
    
    public Post(String username, int profileImageRes, int postImageRes, String caption) {
        this.username = username;
        this.profileImageRes = profileImageRes;
        this.postImageRes = postImageRes;
        this.caption = caption;
    }

    public Post(String username, int profileImageRes, String postImageUri, String caption) {
        this.username = username;
        this.profileImageRes = profileImageRes;
        this.postImageUri = postImageUri;
        this.caption = caption;
    }

    public String getUsername() { return username; }
    public String getProfileImageUri() { return profileImageUri; }
    public int getProfileImageRes() { return profileImageRes; }
    public String getPostImageUri() { return postImageUri; }
    public int getPostImageRes() { return postImageRes; }
    public String getCaption() { return caption; }
}