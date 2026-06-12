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

    @Query("SELECT COUNT(*) FROM posts WHERE isBookmarked = 1")
    LiveData<Integer> getBookmarkedCount();

    @Query("SELECT COUNT(*) FROM posts WHERE isLiked = 1")
    LiveData<Integer> getLikedCount();

    @Query("SELECT COUNT(*) FROM posts WHERE authorName = :authorName")
    LiveData<Integer> getPostCountByAuthor(String authorName);

    @Query("SELECT DISTINCT authorName FROM posts")
    LiveData<List<String>> getAllAuthors();

    @Query("SELECT * FROM posts WHERE authorName = :authorName ORDER BY id DESC")
    LiveData<List<PostEntity>> getPostsByAuthor(String authorName);

    @Query("SELECT * FROM posts WHERE authorName LIKE '%' || :query || '%' OR placeName LIKE '%' || :query || '%' OR placeType LIKE '%' || :query || '%' ORDER BY id DESC")
    LiveData<List<PostEntity>> searchPosts(String query);

    @Query("DELETE FROM posts")
    void clearAllPosts();

    @androidx.room.Delete
    void deletePost(PostEntity post);
}