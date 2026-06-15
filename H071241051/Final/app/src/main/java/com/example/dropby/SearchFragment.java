package com.example.dropby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dropby.data.local.AppDatabase;
import com.example.dropby.data.local.PostEntity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.Executors;

public class SearchFragment extends Fragment {
    private PostAdapter adapter;
    private RecyclerView rvSearch;
    private View layoutEmptyState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSearch = view.findViewById(R.id.rvSearch);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        
        TextInputEditText searchInput = view.findViewById(R.id.etSearch);

        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostAdapter();
        adapter.setListener(new PostAdapter.OnPostInteractionListener() {
            @Override
            public void onLikeClick(PostEntity post) {
                post.isLiked = !post.isLiked;
                Executors.newSingleThreadExecutor().execute(() -> 
                    AppDatabase.getInstance(requireContext()).postDao().updatePost(post)
                );
            }

            @Override
            public void onBookmarkClick(PostEntity post) {
                post.isBookmarked = !post.isBookmarked;
                Executors.newSingleThreadExecutor().execute(() -> 
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
            public void onPostLongClick(PostEntity post) {
                // Ignore for search feed
            }
        });
        rvSearch.setAdapter(adapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (query.isEmpty()) {
                    rvSearch.setVisibility(View.GONE);
                    layoutEmptyState.setVisibility(View.VISIBLE);
                } else {
                    AppDatabase.getInstance(requireContext()).postDao().searchPosts(query).observe(getViewLifecycleOwner(), posts -> {
                        if (posts != null && !posts.isEmpty()) {
                            adapter.setPosts(posts);
                            rvSearch.setVisibility(View.VISIBLE);
                            layoutEmptyState.setVisibility(View.GONE);
                        } else {
                            rvSearch.setVisibility(View.GONE);
                            layoutEmptyState.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
//new merge
