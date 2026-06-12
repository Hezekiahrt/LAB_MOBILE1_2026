package com.example.dropby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dropby.data.local.AppDatabase;

public class BookmarkFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvBookmark = view.findViewById(R.id.rvBookmark);
        View layoutEmptyState = view.findViewById(R.id.layoutEmptyState);

        rvBookmark.setLayoutManager(new LinearLayoutManager(getContext()));
        PostAdapter adapter = new PostAdapter();
        adapter.setListener(new PostAdapter.OnPostInteractionListener() {
            @Override
            public void onLikeClick(com.example.dropby.data.local.PostEntity post) {
                post.isLiked = !post.isLiked;
                java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> 
                    AppDatabase.getInstance(requireContext()).postDao().updatePost(post)
                );
            }

            @Override
            public void onBookmarkClick(com.example.dropby.data.local.PostEntity post) {
                post.isBookmarked = !post.isBookmarked;
                java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> 
                    AppDatabase.getInstance(requireContext()).postDao().updatePost(post)
                );
            }

            @Override
            public void onAuthorClick(String authorName) {
                String currentUsername = new com.example.dropby.utils.TokenManager(requireContext()).getUsername();
                if (currentUsername == null) currentUsername = "";
                
                if ("Anda".equals(authorName) || currentUsername.equals(authorName)) {
                    com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
                    if (bottomNav != null) {
                        bottomNav.setSelectedItemId(R.id.nav_profile);
                    }
                } else {
                    android.content.Intent intent = new android.content.Intent(getContext(), DummyProfileActivity.class);
                    intent.putExtra("authorName", authorName);
                    startActivity(intent);
                }
            }

            @Override
            public void onPostLongClick(com.example.dropby.data.local.PostEntity post) {
                // Ignore for bookmarks feed
            }
        });
        rvBookmark.setAdapter(adapter);

        AppDatabase.getInstance(requireContext()).postDao().getBookmarkedPosts().observe(getViewLifecycleOwner(), posts -> {
            if (posts != null && !posts.isEmpty()) {
                adapter.setPosts(posts);
                rvBookmark.setVisibility(View.VISIBLE);
                layoutEmptyState.setVisibility(View.GONE);
            } else {
                rvBookmark.setVisibility(View.GONE);
                layoutEmptyState.setVisibility(View.VISIBLE);
            }
        });
    }
}