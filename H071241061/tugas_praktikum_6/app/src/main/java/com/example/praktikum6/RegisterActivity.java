package com.example.praktikum6;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.praktikum6.databinding.ActivityRegisterBinding;
import com.example.praktikum6.helper.UserPreference;
import com.example.praktikum6.model.UserModel;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private UserPreference userPreference;

    private final String[] jurusanList = {
            "Teknik Informatika", "Sistem Informasi", "Teknik Komputer",
            "Ilmu Komputer", "Teknik Elektro", "Manajemen Informatika"
    };

    private final String[] semesterList = {
            "1", "2", "3", "4", "5", "6", "7", "8"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userPreference = new UserPreference(this);

        setupDropdowns();
        setupClickListeners();
    }

    private void setupDropdowns() {
        ArrayAdapter<String> jurusanAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, jurusanList);
        binding.autoCompleteJurusan.setAdapter(jurusanAdapter);

        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, semesterList);
        binding.autoCompleteSemester.setAdapter(semesterAdapter);
    }

    private void setupClickListeners() {
        binding.btnRegister.setOnClickListener(v -> validateAndRegister());

        binding.tvLoginHere.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void validateAndRegister() {
        String name = binding.etName.getText().toString().trim();
        String nim = binding.etNim.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String confirmPass = binding.etConfirmPassword.getText().toString().trim();
        String jurusan = binding.autoCompleteJurusan.getText().toString().trim();
        String semester = binding.autoCompleteSemester.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();

        binding.tilName.setError(null);
        binding.tilNim.setError(null);
        binding.tilEmail.setError(null);
        binding.tilPassword.setError(null);
        binding.tilConfirmPassword.setError(null);
        binding.tilJurusan.setError(null);
        binding.tilSemester.setError(null);

        boolean isValid = true;

        if (TextUtils.isEmpty(name)) {
            binding.tilName.setError("Nama tidak boleh kosong");
            isValid = false;
        }
        if (TextUtils.isEmpty(nim) || nim.length() < 8) {
            binding.tilNim.setError("NIM minimal 8 karakter");
            isValid = false;
        }
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError("Email tidak valid");
            isValid = false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            binding.tilPassword.setError("Password minimal 6 karakter");
            isValid = false;
        }
        if (!password.equals(confirmPass)) {
            binding.tilConfirmPassword.setError("Password tidak cocok");
            isValid = false;
        }
        if (TextUtils.isEmpty(jurusan)) {
            binding.tilJurusan.setError("Pilih jurusan");
            isValid = false;
        }
        if (TextUtils.isEmpty(semester)) {
            binding.tilSemester.setError("Pilih semester");
            isValid = false;
        }

        if (!isValid) return;

        UserModel user = new UserModel(name, nim, email, password, jurusan, semester, "0.00", phone);
        user.setLoggedIn(false);
        userPreference.saveUser(user);

        Toast.makeText(this, "Registrasi berhasil! Silakan login.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
