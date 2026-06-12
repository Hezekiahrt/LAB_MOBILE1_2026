package com.example.dropby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.chip.ChipGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.dropby.data.local.PostEntity;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private PostAdapter postAdapter;
    private List<PostEntity> currentPosts = new ArrayList<>(); // Menyimpan data asli untuk proses filter

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi ViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Inisialisasi View
        RecyclerView rvPosts = view.findViewById(R.id.rvPosts);
        SwipeRefreshLayout swipeRefresh = view.findViewById(R.id.swipeRefresh);
        
        View btnSemua = view.findViewById(R.id.btnSemua);
        View btnTeman = view.findViewById(R.id.btnTeman);
        android.widget.TextView tvSemua = view.findViewById(R.id.tvSemua);
        android.widget.TextView tvTeman = view.findViewById(R.id.tvTeman);
        View indicatorSemua = view.findViewById(R.id.indicatorSemua);
        View indicatorTeman = view.findViewById(R.id.indicatorTeman);

        // Setup RecyclerView
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter();
        rvPosts.setAdapter(postAdapter);

        // 1. Observasi Data dari Room/API (Local Data Persistent)
        homeViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            if (posts != null) {
                currentPosts = posts;
                // Selalu filter ulang sesuai pilihan tab saat data baru masuk
                applyFilter(indicatorSemua.getVisibility() == View.VISIBLE ? 1 : 2);
            }
        });

        // 2. Observasi Status Refresh (Animasi Loading)
        homeViewModel.getIsRefreshing().observe(getViewLifecycleOwner(), isRefreshing -> {
            swipeRefresh.setRefreshing(isRefreshing);
        });

        // 3. Observasi Error Message (Jaringan Gagal)
        homeViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        // 4. Aksi Tombol Refresh (Saat layar ditarik ke bawah)
        swipeRefresh.setOnRefreshListener(() -> {
            homeViewModel.fetchPostsFromApi();
        });

        // 5. Aksi Toggle Feed (FYP / Teman)
        btnSemua.setOnClickListener(v -> {
            tvSemua.setTypeface(null, android.graphics.Typeface.BOLD);
            tvSemua.setTextColor(getResources().getColor(R.color.textColorPrimary));
            indicatorSemua.setVisibility(View.VISIBLE);

            tvTeman.setTypeface(null, android.graphics.Typeface.NORMAL);
            tvTeman.setTextColor(android.graphics.Color.parseColor("#888888"));
            indicatorTeman.setVisibility(View.INVISIBLE);

            applyFilter(1); // 1 = Semua
        });

        btnTeman.setOnClickListener(v -> {
            tvTeman.setTypeface(null, android.graphics.Typeface.BOLD);
            tvTeman.setTextColor(getResources().getColor(R.color.textColorPrimary));
            indicatorTeman.setVisibility(View.VISIBLE);

            tvSemua.setTypeface(null, android.graphics.Typeface.NORMAL);
            tvSemua.setTextColor(android.graphics.Color.parseColor("#888888"));
            indicatorSemua.setVisibility(View.INVISIBLE);

            applyFilter(2); // 2 = Teman
        });

        // Ambil data dari API untuk pertama kali saat layar dibuka
        if (savedInstanceState == null) {
            homeViewModel.fetchPostsFromApi();
        }
    }

    // Logika Filter (FYP vs Teman)
    private void applyFilter(int type) {
        if (type == 2) {
            // TODO: Logika khusus API untuk teman.
            // Sebagai contoh simulasi lokal: kita filter postingan yang ratingnya 8 ke atas.
            List<PostEntity> friendPosts = new ArrayList<>();
            for (PostEntity post : currentPosts) {
                if (post.rating >= 8) {
                    friendPosts.add(post);
                }
            }
            postAdapter.setPosts(friendPosts);
        } else {
            // Tampilkan semua data (FYP)
            postAdapter.setPosts(currentPosts);
        }
    }
}