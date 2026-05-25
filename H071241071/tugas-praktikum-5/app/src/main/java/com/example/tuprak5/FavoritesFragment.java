package com.example.tuprak5;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private BookAdapter bookAdapter;
    private ProgressBar progressBar;


    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavorites = view.findViewById(R.id.rv_favorites);
        progressBar = view.findViewById(R.id.progressBar);

        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        bookAdapter = new BookAdapter(new ArrayList<>());
        rvFavorites.setAdapter(bookAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        progressBar.setVisibility(View.VISIBLE);
        rvFavorites.setVisibility(View.GONE);


        executorService.execute(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Book> favoriteList = new ArrayList<>();

            for (Book book : DataSource.books) {
                if (book.isLiked()) {
                    favoriteList.add(book);
                }
            }


            handler.post(() -> {
                progressBar.setVisibility(View.GONE);
                rvFavorites.setVisibility(View.VISIBLE);

                bookAdapter.setFilteredList(favoriteList);
            });
        });
    }
}