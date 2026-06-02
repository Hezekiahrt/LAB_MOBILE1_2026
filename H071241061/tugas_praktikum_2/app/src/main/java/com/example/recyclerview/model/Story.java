package com.example.recyclerview.model;

public class Story {

    private int     imageResId;
    private String  label;
    private boolean isViewed;
    private boolean isMyStory;

    public Story(int imageResId, String label) {
        this.imageResId = imageResId;
        this.label      = label;
        this.isViewed   = false;
        this.isMyStory  = false;
    }

    public int     getImageResId()          { return imageResId; }
    public void    setImageResId(int v)     { imageResId = v; }

    public String  getLabel()               { return label; }
    public void    setLabel(String v)       { label = v; }

    public boolean isViewed()               { return isViewed; }
    public void    setViewed(boolean v)     { isViewed = v; }

    public boolean isMyStory()              { return isMyStory; }
    public void    setMyStory(boolean v)    { isMyStory = v; }
}