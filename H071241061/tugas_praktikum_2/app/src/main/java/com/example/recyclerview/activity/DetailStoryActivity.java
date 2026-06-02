package com.example.recyclerview.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclerview.R;

public class DetailStoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        int imageResId = getIntent().getIntExtra("imageResId", R.drawable.post1);
        int profileResId = getIntent().getIntExtra("profileResId", R.drawable.ic_profile);
        String label   = getIntent().getStringExtra("label");
        if (label == null) label = "Story";

        ImageView imgStory   = findViewById(R.id.imgDetailStory);
        ImageView imgProfile = findViewById(R.id.imgProfileStory);
        TextView tvLabel     = findViewById(R.id.tvDetailStoryLabel);
        TextView tvTime      = findViewById(R.id.tvStoryTime);
        ImageView btnBack    = findViewById(R.id.btnBackStory);

        imgStory.setImageResource(imageResId);
        imgProfile.setImageResource(profileResId);
        tvLabel.setText(label);
        tvTime.setText("Baru saja");

        btnBack.setOnClickListener(v -> finish());

        setFullscreen();
    }

    private void setFullscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars());
                controller.setSystemBarsBehavior(
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                );
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }
    }
}