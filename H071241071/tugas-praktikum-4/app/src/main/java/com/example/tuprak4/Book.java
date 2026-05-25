package com.example.tuprak4;

import android.net.Uri;

public class Book {
    private String id;
    private String title;
    private String author;
    private String year;
    private String blurb;
    private Uri coverImageUri;
    private int coverImageResId;
    private boolean isLiked;
    private float rating;
    private String genre;

    public Book(String id, String title, String author, String year, String blurb, int coverImageResId, float rating, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.coverImageResId = coverImageResId;
        this.rating = rating;
        this.genre = genre;
        this.isLiked = false;
    }

    public Book(String id, String title, String author, String year, String blurb, Uri coverImageUri, float rating, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.coverImageUri = coverImageUri;
        this.rating = rating;
        this.genre = genre;
        this.isLiked = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getYear() { return year; }
    public String getBlurb() { return blurb; }
    public Uri getCoverImageUri() { return coverImageUri; }
    public int getCoverImageResId() { return coverImageResId; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
    public float getRating() { return rating; }
    public String getGenre() { return genre; }
}