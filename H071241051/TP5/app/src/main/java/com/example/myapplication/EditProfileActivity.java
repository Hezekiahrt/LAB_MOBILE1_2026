package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.imageview.ShapeableImageView;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etUsername, etBio;
    private ImageView btnBack, btnSave;
    private ShapeableImageView ivEditProfileImage;
    private SessionManager session;

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
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etBio = findViewById(R.id.etBio);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        ivEditProfileImage = findViewById(R.id.ivEditProfileImage);

        // Load current data
        Intent intent = getIntent();
        etName.setText(intent.getStringExtra("name"));
        etUsername.setText(intent.getStringExtra("username"));
        etBio.setText(intent.getStringExtra("bio"));
        String imageUri = intent.getStringExtra("imageUri");

        User user = DataRepository.getInstance().getCurrentUser();
        if (user.getProfileImageRes() != -1) {
            ivEditProfileImage.setImageResource(user.getProfileImageRes());
        } else if (imageUri != null) {
            ivEditProfileImage.setImageURI(Uri.parse(imageUri));
        }

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString();
            String newUsername = etUsername.getText().toString();
            String newBio = etBio.getText().toString();

            DataRepository.getInstance().updateCurrentUser(newName, newUsername, newBio);
            
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(this, "Profil diperbarui", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}