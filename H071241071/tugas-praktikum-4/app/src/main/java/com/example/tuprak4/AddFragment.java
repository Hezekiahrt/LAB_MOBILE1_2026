package com.example.tuprak4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddFragment extends Fragment {

    private ImageView ivCoverPreview;
    private EditText etTitle, etAuthor, etYear, etGenre, etRating, etBlurb;
    private Button btnPickImage, btnSave;
    private Uri selectedImageUri = null;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivCoverPreview.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivCoverPreview = view.findViewById(R.id.iv_add_cover);
        btnPickImage = view.findViewById(R.id.btn_pick_image);
        etTitle = view.findViewById(R.id.et_add_title);
        etAuthor = view.findViewById(R.id.et_add_author);
        etYear = view.findViewById(R.id.et_add_year);
        etGenre = view.findViewById(R.id.et_add_genre);
        etRating = view.findViewById(R.id.et_add_rating);
        etBlurb = view.findViewById(R.id.et_add_blurb);
        btnSave = view.findViewById(R.id.btn_save_book);

        btnPickImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();
            String year = etYear.getText().toString().trim();
            String genre = etGenre.getText().toString().trim();
            String ratingStr = etRating.getText().toString().trim();
            String blurb = etBlurb.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || year.isEmpty() || selectedImageUri == null) {
                Toast.makeText(getContext(), "Judul, Penulis, Tahun, dan Gambar wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            float rating = ratingStr.isEmpty() ? 0.0f : Float.parseFloat(ratingStr);
            String newId = String.valueOf(System.currentTimeMillis());

            Book newBook = new Book(newId, title, author, year, blurb, selectedImageUri, rating, genre);

            DataSource.books.add(newBook);

            Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
            clearForm();
        });
    }

    private void clearForm() {
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etGenre.setText("");
        etRating.setText("");
        etBlurb.setText("");
        ivCoverPreview.setImageURI(null);
        ivCoverPreview.setBackgroundColor(0xFFE0E0E0);
        selectedImageUri = null;
    }
}