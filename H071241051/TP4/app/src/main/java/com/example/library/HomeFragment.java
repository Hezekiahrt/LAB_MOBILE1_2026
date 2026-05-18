package com.example.library;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private BookAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBooks);
        SearchView searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBarHome);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi adapter dengan semua data buku
        adapter = new BookAdapter(DataStore.getInstance().getBooks(), book -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("BOOK_ID", book.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Menerapkan Background Thread pada SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearchInBackground(newText);
                return true;
            }
        });

        return view;
    }

    private void performSearchInBackground(String query) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // -- PROSES BACKGROUND THREAD DIMULAI --
            List<Book> filteredList = new ArrayList<>();
            List<Book> allBooks = DataStore.getInstance().getBooks();

            if (query == null || query.trim().isEmpty()) {
                filteredList.addAll(allBooks);
            } else {
                String filterPattern = query.toLowerCase().trim();
                for (Book book : allBooks) {
                    if (book.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(book);
                    }
                }
            }

            // Simulasi proses berat agar animasi loading terlihat (1 detik)
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

            // -- KEMBALI KE UI THREAD --
            handler.post(() -> {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.updateData(filteredList); // Memperbarui RecyclerView
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            // Refresh data (jika ada perubahan Like/Unlike dari DetailActivity)
            adapter.notifyDataSetChanged();
        }
    }
}
