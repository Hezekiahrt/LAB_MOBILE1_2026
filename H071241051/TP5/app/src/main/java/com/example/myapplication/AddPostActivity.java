package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AddPostActivity extends AppCompatActivity {

    private ImageView ivNewPostPreview, btnBack;
    private EditText etCaption;
    private TextView btnShare;
    private Uri selectedImageUri;
    private SessionManager session;

    private final ActivityResultLauncher<String> getImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    ivNewPostPreview.setImageURI(uri);
                    try {
                        getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (SecurityException e) { }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionManager(this);
        // Terapkan Tema
        if (session.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        ivNewPostPreview = findViewById(R.id.ivNewPostPreview);
        btnBack = findViewById(R.id.btnAddPostBack);
        etCaption = findViewById(R.id.etCaption);
        btnShare = findViewById(R.id.btnShare);

        ivNewPostPreview.setOnClickListener(v -> getImage.launch("image/*"));
        btnBack.setOnClickListener(v -> finish());

        btnShare.setOnClickListener(v -> {
            if (selectedImageUri == null) {
                Toast.makeText(this, "Silakan pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            String caption = etCaption.getText().toString();
            User user = DataRepository.getInstance().getCurrentUser();
            
            Post newPost;
            if (user.getProfileImageRes() != -1) {
                newPost = new Post(user.getUsername(), user.getProfileImageRes(), selectedImageUri.toString(), caption);
            } else {
                newPost = new Post(user.getUsername(), user.getProfileImageUri(), selectedImageUri.toString(), caption);
            }
            
            DataRepository.getInstance().addPost(newPost);
            Toast.makeText(this, "Berhasil diupload!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });
    }
}