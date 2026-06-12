package com.example.dropby.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts")
public class PostEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String authorName;
    public String placeName;
    public String placeType;
    public int rating;
    public String caption;
    public String imageUrl;
    public String location;
    public boolean isBookmarked;
    public boolean isLiked;

    // Constructor kosong dibutuhkan oleh Room
    public PostEntity() {}
}