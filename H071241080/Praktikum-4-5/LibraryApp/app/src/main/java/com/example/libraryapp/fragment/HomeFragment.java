package com.example.libraryapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.DataHelper;
import com.example.libraryapp.R;
import com.example.libraryapp.adapter.BookAdapter;
import com.example.libraryapp.model.Book;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private BookAdapter adapter;
    private final List<Book> filteredList = new ArrayList<>();
    private String currentQuery = "";
    private String currentGenre = "Semua";

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        TextInputEditText searchView = view.findViewById(R.id.searchView);
        Spinner spinnerGenre = view.findViewById(R.id.spinnerGenre);

        adapter = new BookAdapter(getContext(), filteredList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        List<String> genres = new ArrayList<>();
        genres.add("Semua");
        for (Book b : DataHelper.getBooks()) {
            if (!genres.contains(b.getGenre())) genres.add(b.getGenre());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, genres);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(spinnerAdapter);

        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                currentGenre = genres.get(position);
                filterAsync();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s.toString().toLowerCase();
                filterAsync();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        filterAsync();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        filterAsync();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }

    private void filterAsync() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        String querySnapshot = currentQuery;
        String genreSnapshot = currentGenre;

        executor.execute(() -> {
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}

            List<Book> result = new ArrayList<>();
            for (Book b : DataHelper.getBooks()) {
                boolean matchQuery = b.getTitle().toLowerCase().contains(querySnapshot);
                boolean matchGenre = genreSnapshot.equals("Semua") || b.getGenre().equals(genreSnapshot);
                if (matchQuery && matchGenre) result.add(b);
            }

            mainHandler.post(() -> {
                if (getContext() == null) return;
                filteredList.clear();
                filteredList.addAll(result);
                adapter.updateList(filteredList);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            });
        });
    }
}