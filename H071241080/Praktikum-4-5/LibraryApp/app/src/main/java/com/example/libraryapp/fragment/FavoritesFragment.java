package com.example.libraryapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.DataHelper;
import com.example.libraryapp.R;
import com.example.libraryapp.adapter.BookAdapter;
import com.example.libraryapp.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayout layoutEmpty;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerFavorites);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);

        loadFavoritesAsync();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }

    private void loadFavoritesAsync() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);

        executor.execute(() -> {
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}

            List<Book> favorites = new ArrayList<>();
            for (Book b : DataHelper.getBooks()) {
                if (b.isLiked()) favorites.add(b);
            }

            mainHandler.post(() -> {
                if (getContext() == null) return;
                progressBar.setVisibility(View.GONE);

                if (favorites.isEmpty()) {
                    layoutEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    layoutEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    BookAdapter adapter = new BookAdapter(getContext(), favorites);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                }
            });
        });
    }
}