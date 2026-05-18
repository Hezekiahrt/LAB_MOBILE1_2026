package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvHomeFeed;
    private FeedAdapter adapter;
    private ImageView btnNavAdd, btnNavProfile;
    private SessionManager session;

    private final ActivityResultLauncher<Intent> addPostLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(this);

        // Proteksi Halaman: Jika belum login, tendang ke LoginActivity
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_home);

        rvHomeFeed = findViewById(R.id.rvHomeFeed);
        btnNavAdd = findViewById(R.id.btnNavAdd);
        btnNavProfile = findViewById(R.id.btnNavProfile);

        adapter = new FeedAdapter(DataRepository.getInstance().getAllPosts(), post -> {
            // Interaction
        });

        rvHomeFeed.setLayoutManager(new LinearLayoutManager(this));
        rvHomeFeed.setAdapter(adapter);

        btnNavAdd.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddPostActivity.class);
            addPostLauncher.launch(intent);
        });

        btnNavProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}