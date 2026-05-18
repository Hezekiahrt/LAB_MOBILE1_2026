package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    private TextView tvUsernameTop, tvName, tvBio;
    private MaterialButton btnEditProfile;
    private ShapeableImageView ivProfileImage, ivProfileBottom;
    private RecyclerView rvHighlights, rvProfileFeed;
    private StoryAdapter storyAdapter;
    private ProfileFeedAdapter profileFeedAdapter;
    private ImageView btnHome, btnAddNav;
    private SessionManager session;

    private final ActivityResultLauncher<Intent> addPostLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (profileFeedAdapter != null) {
                    profileFeedAdapter.notifyDataSetChanged();
                }
            }
    );

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    updateUI();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(this);

        tvUsernameTop = findViewById(R.id.tvUsernameTop);
        tvName = findViewById(R.id.tvName);
        tvBio = findViewById(R.id.tvBio);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        ivProfileBottom = findViewById(R.id.ivProfileBottom);
        rvHighlights = findViewById(R.id.rvHighlights);
        rvProfileFeed = findViewById(R.id.rvProfileFeed);
        btnHome = findViewById(R.id.btnHome);
        btnAddNav = findViewById(R.id.btnAddNav);

        updateUI();

        storyAdapter = new StoryAdapter(DataRepository.getInstance().getUserStories(), story -> {
            Intent intent = new Intent(MainActivity.this, DetailStoryActivity.class);
            intent.putExtra("story", story);
            startActivity(intent);
        });
        rvHighlights.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvHighlights.setAdapter(storyAdapter);

        profileFeedAdapter = new ProfileFeedAdapter(DataRepository.getInstance().getUserPosts(), post -> {
            Intent intent = new Intent(MainActivity.this, DetailFeedActivity.class);
            intent.putExtra("post", post);
            startActivity(intent);
        });
        rvProfileFeed.setLayoutManager(new GridLayoutManager(this, 3));
        rvProfileFeed.setAdapter(profileFeedAdapter);

        btnEditProfile.setOnClickListener(v -> {
            User user = DataRepository.getInstance().getCurrentUser();
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("name", user.getName());
            intent.putExtra("username", user.getUsername());
            intent.putExtra("bio", user.getBio());
            intent.putExtra("imageUri", user.getProfileImageUri());
            editProfileLauncher.launch(intent);
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnAddNav.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
            addPostLauncher.launch(intent);
        });

        ImageView btnSettingsMenu = findViewById(R.id.btnSettingsMenu);
        if (btnSettingsMenu != null) {
            btnSettingsMenu.setOnClickListener(v -> {
                boolean isDarkMode = session.isDarkMode();
                boolean newMode = !isDarkMode;
                session.setDarkMode(newMode);
                
                // Ganti tema secara global - Ini akan otomatis merestart activity
                AppCompatDelegate.setDefaultNightMode(newMode ? 
                    AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            });

            btnSettingsMenu.setOnLongClickListener(v -> {
                session.logoutUser();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            });
        }
    }

    private void updateUI() {
        User user = DataRepository.getInstance().getCurrentUser();
        tvName.setText(user.getName());
        tvUsernameTop.setText(user.getUsername());
        tvBio.setText(user.getBio());

        if (user.getProfileImageRes() != -1) {
            ivProfileImage.setImageResource(user.getProfileImageRes());
            ivProfileBottom.setImageResource(user.getProfileImageRes());
        } else if (user.getProfileImageUri() != null) {
            Uri uri = Uri.parse(user.getProfileImageUri());
            ivProfileImage.setImageURI(uri);
            ivProfileBottom.setImageURI(uri);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (profileFeedAdapter != null) {
            profileFeedAdapter.notifyDataSetChanged();
        }
    }
}