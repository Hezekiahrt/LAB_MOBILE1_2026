package com.example.tuprak4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivCover;
    private TextView tvTitle, tvAuthor, tvYear, tvGenre, tvRating, tvBlurb;
    private Button btnLike;
    private Book currentBook;

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
                currentBook.setLiked(!currentBook.isLiked());
                updateLikeButton();
            });
        }
    }

    private void updateLikeButton() {
        if (currentBook.isLiked()) {
            btnLike.setText("Hapus dari Favorit");
        } else {
            btnLike.setText("Tambah ke Favorit");
        }
    }
}