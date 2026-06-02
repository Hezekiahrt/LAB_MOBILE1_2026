package com.example.libraryapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapp.DetailActivity;
import com.example.libraryapp.R;
import com.example.libraryapp.adapter.BookAdapter;
import com.example.libraryapp.data.BookRepository;
import com.example.libraryapp.model.Book;
import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private TextView tvEmpty;
    private BookRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        repository   = BookRepository.getInstance();

        recyclerView = view.findViewById(R.id.recyclerFavorites);
        tvEmpty      = view.findViewById(R.id.tvEmpty);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookAdapter(getContext(), repository.getFavoriteBooks(),
                new BookAdapter.OnBookClickListener() {
                    @Override
                    public void onBookClick(Book book, int position) {
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_BOOK_ID, book.getId());
                        startActivity(intent);
                    }
                    @Override
                    public void onLikeClick(Book book, int position) {
                        refreshFavorites();
                    }
                });
        recyclerView.setAdapter(adapter);
        toggleEmptyState(repository.getFavoriteBooks().isEmpty());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFavorites();
    }

    private void refreshFavorites() {
        ArrayList<Book> favorites = repository.getFavoriteBooks();
        adapter.updateList(favorites);
        toggleEmptyState(favorites.isEmpty());
    }

    private void toggleEmptyState(boolean isEmpty) {
        tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }
}