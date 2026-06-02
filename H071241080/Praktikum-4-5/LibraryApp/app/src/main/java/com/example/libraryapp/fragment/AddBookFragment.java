package com.example.libraryapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.libraryapp.DataHelper;
import com.example.libraryapp.R;
import com.example.libraryapp.model.Book;

public class AddBookFragment extends Fragment {

    private Uri selectedImageUri = null;
    private ImageView imgPreview;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    imgPreview.setImageURI(selectedImageUri);
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etAuthor = view.findViewById(R.id.etAuthor);
        EditText etYear = view.findViewById(R.id.etYear);
        EditText etGenre = view.findViewById(R.id.etGenre);
        EditText etBlurb = view.findViewById(R.id.etBlurb);
        EditText etRating = view.findViewById(R.id.etRating);
        imgPreview = view.findViewById(R.id.imgPreview);
        Button btnPickImage = view.findViewById(R.id.btnPickImage);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnPickImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();
            String year = etYear.getText().toString().trim();
            String genre = etGenre.getText().toString().trim();
            String blurb = etBlurb.getText().toString().trim();
            String ratingStr = etRating.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || year.isEmpty()) {
                Toast.makeText(getContext(), "Judul, Penulis, dan Tahun wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            float rating = 0f;
            try { rating = Float.parseFloat(ratingStr); } catch (Exception ignored) {}

            Book book = new Book(title, author, year, blurb, genre.isEmpty() ? "Lainnya" : genre, rating);
            if (selectedImageUri != null) book.setCoverUri(selectedImageUri);
            DataHelper.addBook(book);

            Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
            // Reset form
            etTitle.setText(""); etAuthor.setText(""); etYear.setText("");
            etGenre.setText(""); etBlurb.setText(""); etRating.setText("");
            imgPreview.setImageResource(R.drawable.ic_book_placeholder);
            selectedImageUri = null;
        });

        return view;
    }
}