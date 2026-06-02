package com.example.tp5_mobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;
import java.util.stream.Collectors;

public class TambahAlumniActivity extends AppCompatActivity {

    private EditText etNama, etAngkatan, etIpk, etPhone, etEmail, etInstansi;
    private Spinner spinnerKampus;
    private ImageView ivFoto;
    private Uri selectedImageUri;

    // Launcher untuk memilih foto dari galeri
    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivFoto.setImageURI(selectedImageUri);
                    
                    // Persist permission agar URI bisa dibaca nanti di Home
                    try {
                        getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_alumni);

        initViews();
        setupKampusSpinner();

        // Tombol Pilih Foto Berfungsi
        findViewById(R.id.iv_tambah_foto).setOnClickListener(v -> openGallery());
        findViewById(R.id.btn_pilih_foto).setOnClickListener(v -> openGallery());

        // Simpan Data dan Update Home
        findViewById(R.id.btn_simpan).setOnClickListener(v -> simpanData());

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initViews() {
        etNama = findViewById(R.id.et_nama);
        etAngkatan = findViewById(R.id.et_angkatan);
        etIpk = findViewById(R.id.et_ipk);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etInstansi = findViewById(R.id.et_instansi);
        spinnerKampus = findViewById(R.id.spinner_kampus_form);
        ivFoto = findViewById(R.id.iv_tambah_foto);
    }

    private void setupKampusSpinner() {
        List<String> listKampus = DataRepository.getInstance().getKampusList()
                .stream().map(k -> k.nama).collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listKampus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKampus.setAdapter(adapter);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }

    private void simpanData() {
        String nama = etNama.getText().toString();
        String angkatan = etAngkatan.getText().toString();
        String ipk = etIpk.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String instansi = etInstansi.getText().toString();
        String kampus = (spinnerKampus.getSelectedItem() != null) ? spinnerKampus.getSelectedItem().toString() : "";

        if (nama.isEmpty() || angkatan.isEmpty()) {
            Toast.makeText(this, "Nama dan Angkatan wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simpan dengan fotoUri jika ada, bidang diisi string kosong
        Alumni alumniBaru;
        String uriString = (selectedImageUri != null) ? selectedImageUri.toString() : null;
        
        if (uriString != null) {
            alumniBaru = new Alumni(nama, kampus, "Umum", angkatan, "Bekerja", instansi, "-", ipk, phone, email, uriString);
        } else {
            alumniBaru = new Alumni(nama, kampus, "Umum", angkatan, "Bekerja", instansi, "-", ipk, phone, email, 0);
        }

        DataRepository.getInstance().addAlumni(alumniBaru);

        Toast.makeText(this, "Data Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
        finish();
    }
}