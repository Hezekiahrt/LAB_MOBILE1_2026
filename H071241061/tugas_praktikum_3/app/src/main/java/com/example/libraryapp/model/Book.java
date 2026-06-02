package com.example.libraryapp.model;

import java.io.Serializable;

public class Book implements Serializable {
    private static int idCounter = 1;

    private int id;
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String genre;
    private float rating;
    private int coverResId;
    private String coverUriString;
    private boolean liked;
    private long addedTime;

    public Book(String title, String author, int year, String blurb,
                String genre, float rating, int coverResId) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.genre = genre;
        this.rating = rating;
        this.coverResId = coverResId;
        this.coverUriString = null;
        this.liked = false;
        this.addedTime = System.currentTimeMillis() - ((idCounter - 1) * 1000L);
    }
    public Book(String title, String author, int year, String blurb,
                String genre, float rating, String coverUriString) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.genre = genre;
        this.rating = rating;
        this.coverResId = 0;
        this.coverUriString = coverUriString;
        this.liked = false;
        this.addedTime = System.currentTimeMillis();
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getBlurb() { return blurb; }
    public void setBlurb(String blurb) { this.blurb = blurb; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public int getCoverResId() { return coverResId; }
    public void setCoverResId(int coverResId) { this.coverResId = coverResId; }
    public String getCoverUriString() { return coverUriString; }
    public void setCoverUriString(String coverUriString) { this.coverUriString = coverUriString; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
    public long getAddedTime() { return addedTime; }
    public void setAddedTime(long addedTime) { this.addedTime = addedTime; }
}