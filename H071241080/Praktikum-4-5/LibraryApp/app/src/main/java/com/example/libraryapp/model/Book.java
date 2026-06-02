package com.example.libraryapp.model;

import android.net.Uri;

public class Book {
    private static int idCounter = 1;
    private int id;
    private String title, author, year, blurb, genre;
    private float rating;
    private boolean liked;
    private Uri coverUri;
    private int drawableResId;

    public Book(String title, String author, String year, String blurb, String genre, float rating) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.genre = genre;
        this.rating = rating;
        this.liked = false;
        this.coverUri = null;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getBlurb() { return blurb; }
    public void setBlurb(String blurb) { this.blurb = blurb; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
    public Uri getCoverUri() { return coverUri; }
    public void setCoverUri(Uri coverUri) { this.coverUri = coverUri; }
    public int getDrawableResId() { return drawableResId; }
    public void setDrawableResId(int id) { this.drawableResId = id; }
}