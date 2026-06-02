package com.example.libraryapp;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryapp.model.Book;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int bookId = getIntent().getIntExtra("book_id", -1);
        Book book = findBook(bookId);

        if (book == null) { finish(); return; }

        ImageView imgCover = findViewById(R.id.imgCover);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvAuthor = findViewById(R.id.tvAuthor);
        TextView tvYear = findViewById(R.id.tvYear);
        TextView tvGenre = findViewById(R.id.tvGenre);
        TextView tvBlurb = findViewById(R.id.tvBlurb);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnLike = findViewById(R.id.btnLike);
        TextView btnBack = findViewById(R.id.btnBack);

        if (book.getCoverUri() != null) {
            imgCover.setImageURI(book.getCoverUri());
        } else if (book.getDrawableResId() != 0) {
            imgCover.setImageResource(book.getDrawableResId());
        } else {
            imgCover.setImageResource(R.drawable.ic_book_placeholder);
        }

        tvTitle.setText(book.getTitle());
        tvAuthor.setText("✍  " + book.getAuthor());
        tvYear.setText("📅  " + book.getYear());
        tvGenre.setText(book.getGenre());
        tvBlurb.setText(book.getBlurb());
        ratingBar.setRating(book.getRating());

        updateLikeButton(btnLike, book);

        btnLike.setOnClickListener(v -> {
            book.setLiked(!book.isLiked());
            updateLikeButton(btnLike, book);
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void updateLikeButton(Button btn, Book book) {
        if (book.isLiked()) {
            btn.setText("♥  Liked");
            btn.setBackgroundTintList(ColorStateList.valueOf(
                    getResources().getColor(R.color.like_active, getTheme())
            ));
        } else {
            btn.setText("♡  Like");
            btn.setBackgroundTintList(ColorStateList.valueOf(
                    getResources().getColor(R.color.like_inactive, getTheme())
            ));
        }
    }

    private Book findBook(int id) {
        List<Book> books = DataHelper.getBooks();
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }
}