package com.example.library;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String bookId = getIntent().getStringExtra("BOOK_ID");

        // Cari buku di DataStore
        for (Book b : DataStore.getInstance().getBooks()) {
            if (b.getId().equals(bookId)) {
                currentBook = b;
                break;
            }
        }

        TextView tvTitle = findViewById(R.id.tvDetailTitle); // Menampilkan info lengkap
        Button btnLike = findViewById(R.id.btnLike); // Tombol Like

        if (currentBook != null) {
            tvTitle.setText(currentBook.getTitle());
            updateLikeButtonStatus(btnLike);

            btnLike.setOnClickListener(v -> {
                currentBook.setLiked(!currentBook.isLiked());
                updateLikeButtonStatus(btnLike);
            });
        }
    }

    private void updateLikeButtonStatus(Button btnLike) {
        if (currentBook.isLiked()) {
            btnLike.setText("Unlike");
        } else {
            btnLike.setText("Like");
        }
    }
}
