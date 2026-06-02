package com.example.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.DataRepository;
import com.example.recyclerview.R;
import com.example.recyclerview.adapter.ProfileFeedAdapter;
import com.example.recyclerview.adapter.StoryAdapter;
import com.example.recyclerview.model.Post;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView rvProfileFeed, rvStory;
    private ProfileFeedAdapter feedAdapter;
    private ArrayList<Post> profilePostList = new ArrayList<>();
    private ActivityResultLauncher<Intent> postLauncher;
    private TextView tvPostCount;

    private static final String MY_USERNAME  = "naailamzya";
    private static final String MY_FULL_NAME = "Naailah Mazaya";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DataRepository.initData();

        String username = getIntent().getStringExtra("username");
        if (username == null || username.trim().isEmpty()) username = MY_USERNAME;

        boolean isMyProfile = username.equals(MY_USERNAME);

        ImageView    imgProfile         = findViewById(R.id.imgProfileHeader);
        TextView     tvUsername         = findViewById(R.id.tvProfileUsername);
        TextView     tvFullName         = findViewById(R.id.tvProfileName);
        TextView     tvBio              = findViewById(R.id.tvProfileBio);
        tvPostCount                     = findViewById(R.id.tvPostCount);
        ImageView    btnBack            = findViewById(R.id.btnBack);
        ImageView    btnAddPost         = findViewById(R.id.btnAddPost);
        LinearLayout layoutMyButtons    = findViewById(R.id.layoutMyButtons);
        LinearLayout layoutOtherButtons = findViewById(R.id.layoutOtherButtons);

        if (isMyProfile) {
            imgProfile.setImageResource(R.drawable.profil);
            tvUsername.setText(MY_USERNAME);
            tvFullName.setText(MY_FULL_NAME);
            tvBio.setText("Sistem Informasi '24");

            layoutMyButtons.setVisibility(View.VISIBLE);
            layoutOtherButtons.setVisibility(View.GONE);
            btnAddPost.setVisibility(View.VISIBLE);

            profilePostList.clear();
            profilePostList.addAll(DataRepository.getProfilePosts());

            rvStory = findViewById(R.id.rvStory);
            rvStory.setLayoutManager(
                    new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rvStory.setAdapter(new StoryAdapter(this, DataRepository.getStories()));

            postLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            int    imgResId = result.getData().getIntExtra("imageResId", R.drawable.post1);
                            String caption  = result.getData().getStringExtra("caption");

                            Post newPost = new Post(imgResId, caption, MY_USERNAME, R.drawable.profil);
                            profilePostList.add(0, newPost);
                            feedAdapter.notifyItemInserted(0);
                            rvProfileFeed.scrollToPosition(0);
                            updatePostCount();
                        }
                    }
            );

            btnAddPost.setOnClickListener(v ->
                    postLauncher.launch(new Intent(this, PostActivity.class)));

            View navAdd = findViewById(R.id.nav_add_profile);
            if (navAdd != null) {
                navAdd.setOnClickListener(v ->
                        postLauncher.launch(new Intent(this, PostActivity.class)));
            }

        } else {
            int otherPhoto = R.drawable.ic_profile;
            for (Post p : DataRepository.homeList) {
                if (p.getUsername().equals(username)) {
                    otherPhoto = p.getProfileImageResId();
                    break;
                }
            }
            imgProfile.setImageResource(otherPhoto);
            tvUsername.setText(username);
            tvFullName.setText(username);
            tvBio.setText("This is " + username + "'s official account.");

            layoutMyButtons.setVisibility(View.GONE);
            layoutOtherButtons.setVisibility(View.VISIBLE);
            btnAddPost.setVisibility(View.GONE);

            profilePostList.clear();
            profilePostList.addAll(DataRepository.getPostsByUsername(username));

            rvStory = findViewById(R.id.rvStory);
            rvStory.setLayoutManager(
                    new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rvStory.setAdapter(new StoryAdapter(this, DataRepository.getStoriesByUsername(username)));
        }

        updatePostCount();

        rvProfileFeed = findViewById(R.id.rvProfileFeed);
        rvProfileFeed.setLayoutManager(new GridLayoutManager(this, 3));
        rvProfileFeed.setHasFixedSize(false);
        feedAdapter = new ProfileFeedAdapter(this, profilePostList);
        rvProfileFeed.setAdapter(feedAdapter);

        btnBack.setOnClickListener(v -> finish());

        View navHome = findViewById(R.id.nav_home_profile);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }
    }

    private void updatePostCount() {
        if (tvPostCount != null) {
            tvPostCount.setText(String.valueOf(profilePostList.size()));
        }
    }
}