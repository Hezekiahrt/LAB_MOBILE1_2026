package com.example.tuprak2;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private String username;
    private int profileImage;
    private Integer postImage;
    private Uri postUri;
    private String caption;

    public Post(String username, int profileImage, Integer postImage, String caption) {
        this.username = username; this.profileImage = profileImage;
        this.postImage = postImage; this.caption = caption;
    }
    public Post(String username, int profileImage, Uri postUri, String caption) {
        this.username = username; this.profileImage = profileImage;
        this.postUri = postUri; this.caption = caption;
    }
    protected Post(Parcel in) {
        username = in.readString(); profileImage = in.readInt();
        if (in.readByte() == 0) postImage = null; else postImage = in.readInt();
        postUri = in.readParcelable(Uri.class.getClassLoader());
        caption = in.readString();
    }
    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override public Post createFromParcel(Parcel in) { return new Post(in); }
        @Override public Post[] newArray(int size) { return new Post[size]; }
    };
    @Override public int describeContents() { return 0; }
    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username); dest.writeInt(profileImage);
        if (postImage == null) dest.writeByte((byte) 0); else { dest.writeByte((byte) 1); dest.writeInt(postImage); }
        dest.writeParcelable(postUri, flags); dest.writeString(caption);
    }
    public String getUsername() { return username; }
    public int getProfileImage() { return profileImage; }
    public Integer getPostImage() { return postImage; }
    public Uri getPostUri() { return postUri; }
    public String getCaption() { return caption; }
}