package com.example.dropby.data.remote;

import com.example.dropby.data.local.PostEntity;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Multipart;

public interface ApiService {
    // Sesuaikan URL endpoint dengan API nyata Anda nanti
    @POST("auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    @GET("posts")
    Call<List<PostEntity>> getPosts();

    @Multipart
    @POST("profile/update")
    Call<AuthResponse> updateProfile(
        @retrofit2.http.Part okhttp3.MultipartBody.Part profilePicture,
        @retrofit2.http.Part("username") okhttp3.RequestBody username
    );

    // Endpoint lainnya (addPost, like, bookmark) akan ditambahkan di sini
}