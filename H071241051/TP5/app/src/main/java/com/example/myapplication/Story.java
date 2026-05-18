package com.example.myapplication;

import java.io.Serializable;

public class Story implements Serializable {
    private String title;
    private int imageRes; // Using resource for dummy

    public Story(String title, int imageRes) {
        this.title = title;
        this.imageRes = imageRes;
    }

    public String getTitle() { return title; }
    public int getImageRes() { return imageRes; }
}