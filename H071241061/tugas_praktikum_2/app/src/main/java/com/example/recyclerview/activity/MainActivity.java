package com.example.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.DataRepository;
import com.example.recyclerview.R;
import com.example.recyclerview.adapter.HomeFeedAdapter;
import com.example.recyclerview.adapter.StoryAdapter;
import com.example.recyclerview.model.Story;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataRepository.initData();

        ArrayList<Story> storyWithMine = new ArrayList<>();

        Story myStory = new Story(R.drawable.profil, "Your story");
        myStory.setMyStory(true);
        storyWithMine.add(myStory);

        storyWithMine.addAll(DataRepository.storyHomeList);

        RecyclerView rvStories = findViewById(R.id.rvStories);
        rvStories.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvStories.setAdapter(new StoryAdapter(this, storyWithMine));

        RecyclerView rvHomeFeed = findViewById(R.id.rvHomeFeed);
        rvHomeFeed.setLayoutManager(new LinearLayoutManager(this));
        rvHomeFeed.setAdapter(new HomeFeedAdapter(this, DataRepository.homeList));

        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("username", "naailamzya");
            startActivity(intent);
        });

        findViewById(R.id.nav_add).setOnClickListener(v ->
                startActivity(new Intent(this, PostActivity.class)));

        findViewById(R.id.nav_home).setOnClickListener(v ->
                rvHomeFeed.scrollToPosition(0));
    }
}