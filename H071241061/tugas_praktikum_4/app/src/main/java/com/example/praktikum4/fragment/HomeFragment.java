package com.example.praktikum4.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikum4.DetailActivity;
import com.example.praktikum4.R;
import com.example.praktikum4.adapter.BookAdapter;
import com.example.praktikum4.data.BookRepository;
import com.example.praktikum4.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private BookRepository repository;
    private SearchView searchView;
    private Spinner spinnerGenre;
    private ProgressBar progressBarSearch;
    private String currentGenre = "All";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        repository       = BookRepository.getInstance();
        recyclerView     = view.findViewById(R.id.recyclerView);
        searchView       = view.findViewById(R.id.etSearch);
        spinnerGenre     = view.findViewById(R.id.spinnerGenre);
        progressBarSearch = view.findViewById(R.id.progressBarSearch);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookAdapter(getContext(), repository.getAllBooks(),
                new BookAdapter.OnBookClickListener() {
                    @Override
                    public void onBookClick(Book book, int position) {
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_BOOK_ID, book.getId());
                        startActivity(intent);
                    }
                    @Override
                    public void onLikeClick(Book book, int position) {
                        adapter.notifyItemChanged(position);
                    }
                });
        recyclerView.setAdapter(adapter);

        setupSearch();
        setupGenreFilter();

        return view;
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterBooksAsync(query, currentGenre);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterBooksAsync(newText, currentGenre);
                return false;
            }
        });
    }

    private void setupGenreFilter() {
        List<String> genres = repository.getAllGenres();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, genres) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (v instanceof TextView) {
                    ((TextView) v).setTextColor(Color.BLACK);
                    ((TextView) v).setTextSize(14);
                }
                return v;
            }
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                if (v instanceof TextView) ((TextView) v).setTextColor(Color.BLACK);
                v.setBackgroundColor(Color.WHITE);
                return v;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(spinnerAdapter);

        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                currentGenre = genres.get(pos);
                filterBooksAsync(searchView.getQuery().toString(), currentGenre);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void filterBooksAsync(String query, String genre) {
        progressBarSearch.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        executor.execute(() -> {
            ArrayList<Book> allBooks = repository.getAllBooks();
            ArrayList<Book> result = new ArrayList<>();

            for (Book book : allBooks) {
                boolean matchQuery = query.isEmpty() ||
                        book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(query.toLowerCase());
                boolean matchGenre = genre.equals("All") ||
                        book.getGenre().equalsIgnoreCase(genre);
                if (matchQuery && matchGenre) result.add(book);
            }

            mainHandler.post(() -> {
                if (getContext() == null) return;
                adapter.updateList(result);
                progressBarSearch.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        filterBooksAsync(searchView.getQuery().toString(), currentGenre);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
