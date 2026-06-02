package com.example.libraryapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.libraryapp.R;
import com.example.libraryapp.data.BookRepository;
import com.example.libraryapp.model.Book;

public class AddBookFragment extends Fragment {

    private EditText etTitle, etAuthor, etYear, etBlurb, etGenre;
    private RatingBar ratingBarAdd;
    private ImageView ivPreview;
    private Uri selectedImageUri = null;

    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ActivityResultLauncher<String[]> permissionLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK
                            && result.getData() != null) {
                        selectedImageUri = result.getData().getData();

                        if (selectedImageUri != null) {
                            try {
                                requireContext().getContentResolver()
                                        .takePersistableUriPermission(
                                                selectedImageUri,
                                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                                        );
                            } catch (SecurityException e) {
                            }
                        }

                        Glide.with(this)
                                .load(selectedImageUri)
                                .centerCrop()
                                .placeholder(R.drawable.cover_placeholder)
                                .into(ivPreview);
                    }
                }
        );

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    boolean anyGranted = false;
                    for (Boolean val : result.values()) {
                        if (Boolean.TRUE.equals(val)) {
                            anyGranted = true;
                            break;
                        }
                    }
                    if (anyGranted) {
                        launchPicker();
                    } else {
                        Toast.makeText(getContext(),
                                "Izin akses foto diperlukan untuk memilih cover.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        etTitle      = view.findViewById(R.id.etTitle);
        etAuthor     = view.findViewById(R.id.etAuthor);
        etYear       = view.findViewById(R.id.etYear);
        etBlurb      = view.findViewById(R.id.etBlurb);
        etGenre      = view.findViewById(R.id.etGenre);
        ratingBarAdd = view.findViewById(R.id.ratingBarAdd);
        ivPreview    = view.findViewById(R.id.ivPreview);

        Button btnPickImage = view.findViewById(R.id.btnPickImage);
        Button btnSave      = view.findViewById(R.id.btnSave);

        btnPickImage.setOnClickListener(v -> checkPermissionAndOpenGallery());
        btnSave.setOnClickListener(v -> saveBook());

        return view;
    }

    private void checkPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            boolean hasImages = ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
            boolean hasVisual = ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) == PackageManager.PERMISSION_GRANTED;

            if (hasImages || hasVisual) {
                launchPicker();
            } else {
                permissionLauncher.launch(new String[]{
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
                });
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            boolean granted = ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;

            if (granted) {
                launchPicker();
            } else {
                permissionLauncher.launch(new String[]{
                        Manifest.permission.READ_MEDIA_IMAGES
                });
            }

        } else {
            boolean granted = ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

            if (granted) {
                launchPicker();
            } else {
                permissionLauncher.launch(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                });
            }
        }
    }

    private void launchPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        imagePickerLauncher.launch(intent);
    }

    private void saveBook() {
        String title   = etTitle.getText().toString().trim();
        String author  = etAuthor.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String blurb   = etBlurb.getText().toString().trim();
        String genre   = etGenre.getText().toString().trim();
        float rating   = ratingBarAdd.getRating();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty()) {
            Toast.makeText(getContext(),
                    "Judul, Penulis, dan Tahun wajib diisi!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(),
                    "Tahun tidak valid!", Toast.LENGTH_SHORT).show();
            return;
        }

        String uriString = selectedImageUri != null
                ? selectedImageUri.toString() : null;

        Book newBook = new Book(
                title,
                author,
                year,
                blurb.isEmpty() ? "Tidak ada deskripsi." : blurb,
                genre.isEmpty() ? "Umum" : genre,
                rating,
                uriString
        );

        BookRepository.getInstance().addBook(newBook);
        Toast.makeText(getContext(),
                "Buku \"" + title + "\" berhasil ditambahkan! 📚",
                Toast.LENGTH_SHORT).show();
        clearForm();
    }

    private void clearForm() {
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etBlurb.setText("");
        etGenre.setText("");
        ratingBarAdd.setRating(0);
        ivPreview.setImageResource(R.drawable.cover_placeholder);
        selectedImageUri = null;
    }
}