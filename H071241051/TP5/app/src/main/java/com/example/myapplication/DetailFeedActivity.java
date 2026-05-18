package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class DetailFeedActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionManager(this);
        // Terapkan Tema
        if (session.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        ImageView btnBack = findViewById(R.id.btnDetailBack);
        ImageView ivProfile = findViewById(R.id.ivItemProfile);
        ImageView ivPost = findViewById(R.id.ivItemPost);
        TextView tvUsername = findViewById(R.id.tvItemUsername);
        TextView tvCaption = findViewById(R.id.tvItemCaption);

        Post post = (Post) getIntent().getSerializableExtra("post");

        if (post != null) {
            tvUsername.setText(post.getUsername());
            tvCaption.setText(post.getCaption());
            
            // Post Image
            if (post.getPostImageRes() != -1) {
                ivPost.setImageResource(post.getPostImageRes());
            } else if (post.getPostImageUri() != null) {
                ivPost.setImageURI(Uri.parse(post.getPostImageUri()));
            }

            // Profile Image
            if (post.getProfileImageRes() != -1) {
                ivProfile.setImageResource(post.getProfileImageRes());
            } else if (post.getProfileImageUri() != null) {
                ivProfile.setImageURI(Uri.parse(post.getProfileImageUri()));
            } else {
                ivProfile.setImageResource(android.R.drawable.presence_online);
            }
        }

        btnBack.setOnClickListener(v -> finish());
    }
}