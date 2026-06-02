package com.example.recyclerview.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclerview.R;

public class DetailFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        int imageResId    = getIntent().getIntExtra("imageResId", R.drawable.post1);
        int profileResId  = getIntent().getIntExtra("profileResId", R.drawable.ic_profile);
        String caption    = getIntent().getStringExtra("caption");
        String username   = getIntent().getStringExtra("username");

        if (caption == null)  caption  = "";
        if (username == null) username = "user";

        ImageView imgDetail  = findViewById(R.id.imgDetailFeed);
        ImageView imgProfile = findViewById(R.id.imgDetailProfile);
        TextView tvUsername  = findViewById(R.id.tvDetailUsername);
        TextView tvCaption   = findViewById(R.id.tvDetailCaption);
        TextView tvLikes     = findViewById(R.id.tvDetailLikes);
        TextView tvTime      = findViewById(R.id.tvDetailTime);
        ImageView btnBack    = findViewById(R.id.btnBackDetail);

        imgDetail.setImageResource(imageResId);
        imgProfile.setImageResource(profileResId);
        tvUsername.setText(username);
        tvCaption.setText(username + "  " + caption);
        tvLikes.setText("1.234 suka");
        tvTime.setText("1 JAM YANG LALU");

        btnBack.setOnClickListener(v -> finish());
    }
}