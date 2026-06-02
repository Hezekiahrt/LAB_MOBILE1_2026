package com.example.tuprak5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivCover;
    private TextView tvTitle, tvAuthor, tvYear, tvGenre, tvRating, tvBlurb;
    private Button btnLike;
    private Book currentBook;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivCover = findViewById(R.id.iv_detail_cover);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvAuthor = findViewById(R.id.tv_detail_author);
        tvYear = findViewById(R.id.tv_detail_year);
        tvGenre = findViewById(R.id.tv_detail_genre);
        tvRating = findViewById(R.id.tv_detail_rating);
        tvBlurb = findViewById(R.id.tv_detail_blurb);
        btnLike = findViewById(R.id.btn_like);

        SharedPreferences pref = getSharedPreferences("user_pref", MODE_PRIVATE);
        currentUsername = pref.getString("saved_username", "");

        String bookId = getIntent().getStringExtra("BOOK_ID");

        for (Book book : DataSource.books) {
            if (book.getId().equals(bookId)) {
                currentBook = book;
                break;
            }
        }

        if (currentBook != null) {
            tvTitle.setText(currentBook.getTitle());
            tvAuthor.setText("Penulis: " + currentBook.getAuthor());
            tvYear.setText("Tahun: " + currentBook.getYear());
            tvGenre.setText("Genre: " + currentBook.getGenre());
            tvRating.setText("Rating: " + currentBook.getRating() + "/5.0");
            tvBlurb.setText(currentBook.getBlurb());

            if (currentBook.getCoverImageUri() != null) {
                ivCover.setImageURI(currentBook.getCoverImageUri());
            } else {
                ivCover.setImageResource(currentBook.getCoverImageResId());
            }

            updateLikeButton();

            btnLike.setOnClickListener(v -> {
                boolean newStatus = !currentBook.isLiked();
                currentBook.setLiked(newStatus);
                saveFavoriteStatus(currentBook.getId(), newStatus);
                updateLikeButton();
            });
        }
    }

    private void saveFavoriteStatus(String bookId, boolean isLiked) {
        SharedPreferences pref = getSharedPreferences("user_pref", MODE_PRIVATE);
        Set<String> favorites = pref.getStringSet("favorites_" + currentUsername, new HashSet<>());
        
        // Buat copy set karena getStringSet mengembalikan set yang tidak boleh dimodifikasi langsung
        Set<String> updatedFavorites = new HashSet<>(favorites);
        
        if (isLiked) {
            updatedFavorites.add(bookId);
        } else {
            updatedFavorites.remove(bookId);
        }
        
        pref.edit().putStringSet("favorites_" + currentUsername, updatedFavorites).apply();
    }

    private void updateLikeButton() {
        if (currentBook.isLiked()) {
            btnLike.setText("Hapus dari Favorit");
        } else {
            btnLike.setText("Tambah ke Favorit");
        }
    }
}