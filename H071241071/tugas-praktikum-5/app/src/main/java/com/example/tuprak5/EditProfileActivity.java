package com.example.tuprak5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView ivEditPhoto;
    private EditText etEditBio;
    private Button btnChangePhoto, btnSave;
    private Uri selectedImageUri;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ivEditPhoto = findViewById(R.id.iv_edit_photo);
        etEditBio = findViewById(R.id.et_edit_bio);
        btnChangePhoto = findViewById(R.id.btn_change_photo);
        btnSave = findViewById(R.id.btn_save_profile);

        SharedPreferences pref = getSharedPreferences("user_pref", MODE_PRIVATE);
        currentUsername = pref.getString("saved_username", "");

        // Load data lama
        String savedBio = pref.getString("bio_" + currentUsername, "Mahasiswa Sistem Informasi Universitas Hasanuddin");
        String savedPhotoPath = pref.getString("photo_" + currentUsername, null);

        etEditBio.setText(savedBio);
        if (savedPhotoPath != null) {
            File file = new File(savedPhotoPath);
            if (file.exists()) {
                ivEditPhoto.setImageURI(Uri.fromFile(file));
            }
        }

        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        ivEditPhoto.setImageURI(selectedImageUri);
                    }
                }
        );

        btnChangePhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("bio_" + currentUsername, etEditBio.getText().toString());

            if (selectedImageUri != null) {
                // Simpan ke Internal Storage agar akses tidak hilang
                String internalPath = saveToInternalStorage(selectedImageUri);
                if (internalPath != null) {
                    editor.putString("photo_" + currentUsername, internalPath);
                }
            }

            editor.apply();
            Toast.makeText(this, "Profil diperbarui!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private String saveToInternalStorage(Uri uri) {
        try {
            String fileName = "profile_" + currentUsername + ".jpg";
            File file = new File(getFilesDir(), fileName);

            InputStream inputStream = getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            inputStream.close();
            outputStream.flush();
            outputStream.close();

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}