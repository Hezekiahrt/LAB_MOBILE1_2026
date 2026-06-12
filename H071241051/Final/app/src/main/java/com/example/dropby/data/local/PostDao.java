package com.example.dropby.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<PostEntity> posts);

    @Update
    void updatePost(PostEntity post);

    @Query("SELECT * FROM posts ORDER BY id DESC")
    LiveData<List<PostEntity>> getAllPosts();

    @Query("SELECT * FROM posts WHERE isBookmarked = 1")
    LiveData<List<PostEntity>> getBookmarkedPosts();

    @Query("DELETE FROM posts")
    void clearAllPosts();
}