package com.example.tp5_mobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class TambahKampusActivity extends AppCompatActivity {

    private EditText etNama;
    private ImageView ivLogo;
    private Uri selectedLogoUri;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedLogoUri = result.getData().getData();
                    ivLogo.setImageURI(selectedLogoUri);
                    
                    // Persist permission agar URI bisa dibaca nanti di Home
                    getContentResolver().takePersistableUriPermission(selectedLogoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kampus);

        etNama = findViewById(R.id.et_nama_kampus);
        ivLogo = findViewById(R.id.iv_logo_preview);

        findViewById(R.id.btn_pilih_logo).setOnClickListener(v -> openGallery());
        findViewById(R.id.btn_simpan_kampus).setOnClickListener(v -> simpanKampus());

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }

    private void simpanKampus() {
        String nama = etNama.getText().toString();
        if (nama.isEmpty()) {
            Toast.makeText(this, "Nama Kampus wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        String uriString = (selectedLogoUri != null) ? selectedLogoUri.toString() : null;
        DataRepository.getInstance().addKampus(new Kampus(nama, 0, uriString));

        Toast.makeText(this, "Kampus Berhasil Ditambahkan!", Toast.LENGTH_SHORT).show();
        finish();
    }
}