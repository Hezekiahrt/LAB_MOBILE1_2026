package com.example.praktikum4.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikum4.DetailActivity;
import com.example.praktikum4.R;
import com.example.praktikum4.adapter.BookAdapter;
import com.example.praktikum4.data.BookRepository;
import com.example.praktikum4.model.Book;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private TextView tvEmpty;
    private ProgressBar progressBarFavorites;
    private BookRepository repository;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        repository           = BookRepository.getInstance();
        recyclerView         = view.findViewById(R.id.recyclerFavorites);
        tvEmpty              = view.findViewById(R.id.tvEmpty);
        progressBarFavorites = view.findViewById(R.id.progressBarFavorites);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookAdapter(getContext(), new ArrayList<>(),
                new BookAdapter.OnBookClickListener() {
                    @Override
                    public void onBookClick(Book book, int position) {
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_BOOK_ID, book.getId());
                        startActivity(intent);
                    }
                    @Override
                    public void onLikeClick(Book book, int position) {
                        refreshFavoritesAsync();
                    }
                });
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFavoritesAsync();
    }

    private void refreshFavoritesAsync() {
        progressBarFavorites.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);

        executor.execute(() -> {
            ArrayList<Book> favorites = repository.getFavoriteBooks();

            mainHandler.post(() -> {
                if (getContext() == null) return;
                adapter.updateList(favorites);
                progressBarFavorites.setVisibility(View.GONE);
                toggleEmptyState(favorites.isEmpty());
            });
        });
    }

    private void toggleEmptyState(boolean isEmpty) {
        tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}