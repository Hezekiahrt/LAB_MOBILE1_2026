package com.example.dropby;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dropby.data.local.AppDatabase;
import com.example.dropby.data.local.PostEntity;
import java.util.concurrent.Executors;

public class DummyProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_profile);

        String authorName = getIntent().getStringExtra("authorName");
        if (authorName == null) authorName = "Unknown";

        TextView tvProfileName = findViewById(R.id.tvProfileName);
        ImageButton btnBack = findViewById(R.id.btnBack);
        RecyclerView rvAuthorPosts = findViewById(R.id.rvAuthorPosts);
        
        TextView tvPostCount = findViewById(R.id.tvPostCount);
        TextView tvFriendCount = findViewById(R.id.tvFriendCount);
        TextView tvDropCount = findViewById(R.id.tvDropCount);

        tvProfileName.setText(authorName);

        btnBack.setOnClickListener(v -> finish());

        rvAuthorPosts.setLayoutManager(new LinearLayoutManager(this));
        PostAdapter adapter = new PostAdapter();
        adapter.setListener(new PostAdapter.OnPostInteractionListener() {
            @Override
            public void onLikeClick(PostEntity post) {
                post.isLiked = !post.isLiked;
                Executors.newSingleThreadExecutor().execute(() -> 
                    AppDatabase.getInstance(DummyProfileActivity.this).postDao().updatePost(post)
                );
            }

            @Override
            public void onBookmarkClick(PostEntity post) {
                post.isBookmarked = !post.isBookmarked;
                Executors.newSingleThreadExecutor().execute(() -> 
                    AppDatabase.getInstance(DummyProfileActivity.this).postDao().updatePost(post)
                );
            }

            @Override
            public void onAuthorClick(String name) {
                // Already in their profile
            }

            @Override
            public void onPostLongClick(PostEntity post) {
                String currentUsername = new com.example.dropby.utils.TokenManager(DummyProfileActivity.this).getUsername();
                if (currentUsername == null) currentUsername = "";
                if ("Anda".equals(post.authorName) || currentUsername.equals(post.authorName)) {
                    com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = new com.google.android.material.bottomsheet.BottomSheetDialog(DummyProfileActivity.this);
                    android.view.View bottomSheetView = android.view.LayoutInflater.from(DummyProfileActivity.this).inflate(R.layout.dialog_bottom_sheet_delete, null);
                    bottomSheetDialog.setContentView(bottomSheetView);

                    bottomSheetDialog.setOnShowListener(dialog -> {
                        com.google.android.material.bottomsheet.BottomSheetDialog d = (com.google.android.material.bottomsheet.BottomSheetDialog) dialog;
                        android.widget.FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                        if (bottomSheet != null) {
                            bottomSheet.setBackgroundColor(android.graphics.Color.TRANSPARENT);
                        }
                    });

                    bottomSheetView.findViewById(R.id.btnConfirmDelete).setOnClickListener(v -> {
                        java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
                            com.example.dropby.data.local.AppDatabase.getInstance(DummyProfileActivity.this).postDao().deletePost(post);
                        });
                        android.widget.Toast.makeText(DummyProfileActivity.this, "Postingan dihapus", android.widget.Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    });

                    bottomSheetView.findViewById(R.id.btnCancelDelete).setOnClickListener(v -> {
                        bottomSheetDialog.dismiss();
                    });

                    bottomSheetDialog.show();
                }
            }
        });
        rvAuthorPosts.setAdapter(adapter);

        com.example.dropby.data.local.AppDatabase db = AppDatabase.getInstance(this);

        db.postDao().getPostsByAuthor(authorName).observe(this, posts -> {
            if (posts != null) {
                adapter.setPosts(posts);
                tvPostCount.setText(String.valueOf(posts.size()));
                
                // Calculate dummy "Drop" (Likes) for this author's posts
                int totalDrops = 0;
                for (PostEntity post : posts) {
                    totalDrops += (post.isLiked ? 1 : 0);
                }
                tvDropCount.setText(String.valueOf(totalDrops));
            }
        });

        db.postDao().getAllAuthors().observe(this, authors -> {
            if (authors != null) {
                // Dummy friend count
                tvFriendCount.setText(String.valueOf(authors.size() > 1 ? authors.size() - 1 : 0));
            }
        });
    }
}
