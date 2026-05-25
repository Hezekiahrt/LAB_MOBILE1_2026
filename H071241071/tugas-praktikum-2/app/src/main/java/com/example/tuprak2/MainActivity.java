package com.example.tuprak2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.tuprak2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (DataRepository.homeFeeds.isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                int resId = getResources().getIdentifier("img" + i, "drawable", getPackageName());
                DataRepository.homeFeeds.add(new Post("fadeeru", R.drawable.pfp_sample, resId != 0 ? resId : R.drawable.pfp_sample, "Caption " + i));
            }
        }

        binding.rvHome.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHome.setAdapter(new FeedAdapter(DataRepository.homeFeeds, () ->
                startActivity(new Intent(this, ProfileActivity.class))));

        binding.btnGoToPost.setOnClickListener(v -> startActivity(new Intent(this, PostActivity.class)));
    }
}