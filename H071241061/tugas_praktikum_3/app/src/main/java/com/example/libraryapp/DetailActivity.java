package com.example.libraryapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.example.libraryapp.data.BookRepository;
import com.example.libraryapp.model.Book;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_ID = "extra_book_id"; 

    private Book book;
    private FloatingActionButton fabLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int bookId = getIntent().getIntExtra(EXTRA_BOOK_ID, -1);
        book = BookRepository.getInstance().getBookById(bookId);
        if (book == null) { finish(); return; }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        
        toolbar.setNavigationOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed());

        ImageView ivCover   = findViewById(R.id.ivCoverDetail);
        TextView tvTitle    = findViewById(R.id.tvTitleDetail);
        TextView tvAuthor   = findViewById(R.id.tvAuthorDetail);
        TextView tvYear     = findViewById(R.id.tvYearDetail);
        TextView tvBlurb    = findViewById(R.id.tvBlurbDetail);
        TextView tvRating   = findViewById(R.id.tvRatingDetail);
        RatingBar ratingBar = findViewById(R.id.ratingBarDetail);
        Chip chipGenre      = findViewById(R.id.chipGenre);
        fabLike             = findViewById(R.id.fabLike);

        tvTitle.setText(book.getTitle());
        tvAuthor.setText("✍ " + book.getAuthor());
        tvYear.setText("📅 " + book.getYear());
        tvBlurb.setText(book.getBlurb());
        tvRating.setText(String.format("%.1f / 5.0", book.getRating()));
        ratingBar.setRating(book.getRating());
        chipGenre.setText(book.getGenre());

        if (book.getCoverUriString() != null) {
            Glide.with(this)
                    .load(Uri.parse(book.getCoverUriString()))
                    .placeholder(R.drawable.cover_placeholder)
                    .centerCrop()
                    .into(ivCover);
        } else if (book.getCoverResId() != 0) {
            ivCover.setImageResource(book.getCoverResId());
        } else {
            ivCover.setImageResource(R.drawable.cover_placeholder);
        }

        updateFabIcon();

        fabLike.setOnClickListener(v -> {
            book.setLiked(!book.isLiked()); 
            updateFabIcon();
            String msg = book.isLiked() ? "Ditambahkan ke Favorit ❤️" : "Dihapus dari Favorit";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateFabIcon() {
        fabLike.setImageResource(
                book.isLiked() ? R.drawable.favorite_filled : R.drawable.favorite_border
        );
    }
}
