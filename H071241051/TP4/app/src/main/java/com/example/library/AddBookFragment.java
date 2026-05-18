package com.example.library;

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
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class AddBookFragment extends Fragment {

    private ImageView imgCoverPreview;
    private Uri selectedImageUri;

    // Launcher untuk mengambil gambar cover dari galeri
    private final ActivityResultLauncher<Intent> photoPickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    imgCoverPreview.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // R is automatically found because it is in the same package: com.example.library
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        EditText etTitle = view.findViewById(R.id.etTitle); // Form input data buku
        EditText etAuthor = view.findViewById(R.id.etAuthor);
        Button btnSelectImage = view.findViewById(R.id.btnSelectImage);
        Button btnSave = view.findViewById(R.id.btnSave);
        imgCoverPreview = view.findViewById(R.id.imgCoverPreview);

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            photoPickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String author = etAuthor.getText().toString();
            String imagePath = selectedImageUri != null ? selectedImageUri.toString() : "";

            // Book and DataStore are also in the com.example.library package
            Book newBook = new Book(UUID.randomUUID().toString(), title, author, 2026, "Blurb", imagePath, "Umum", 0.0);
            DataStore.getInstance().addBook(newBook);

            Toast.makeText(getContext(), "Buku ditambahkan!", Toast.LENGTH_SHORT).show();
            // Reset form input
            etTitle.setText("");
            etAuthor.setText("");
            imgCoverPreview.setImageURI(null);
        });

        return view;
    }
}
