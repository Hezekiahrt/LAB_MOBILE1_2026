package com.example.library;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BookAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.rvFavorites);
        progressBar = view.findViewById(R.id.progressBarFav);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Menerapkan Background Thread saat memuat data Favorites
        loadFavoritesInBackground();
    }

    private void loadFavoritesInBackground() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // -- PROSES BACKGROUND THREAD DIMULAI --
            List<Book> likedBooks = new ArrayList<>();
            for (Book book : DataStore.getInstance().getBooks()) {
                if (book.isLiked()) {
                    likedBooks.add(book);
                }
            }

            // Simulasi proses berat memuat data (0.8 detik)
            try { Thread.sleep(800); } catch (InterruptedException e) { e.printStackTrace(); }

            // -- KEMBALI KE UI THREAD --
            handler.post(() -> {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                adapter = new BookAdapter(likedBooks, book -> {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("BOOK_ID", book.getId());
                    startActivity(intent);
                });
                recyclerView.setAdapter(adapter);
            });
        });
    }
}
