package com.example.praktikum6;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.praktikum6.databinding.ActivityLoginBinding;
import com.example.praktikum6.helper.UserPreference;
import com.example.praktikum6.model.UserModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userPreference = new UserPreference(this);

        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.btnLogin.setOnClickListener(v -> validateAndLogin());

        binding.tvRegisterHere.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void validateAndLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        binding.tilEmail.setError(null);
        binding.tilPassword.setError(null);

        boolean isValid = true;

        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.setError("Email tidak boleh kosong");
            isValid = false;
        }
        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError("Password tidak boleh kosong");
            isValid = false;
        }

        if (!isValid) return;

        UserModel savedUser = userPreference.getUser();

        if (savedUser.getEmail().isEmpty()) {
            binding.tilEmail.setError("Akun tidak ditemukan, silakan register");
            return;
        }

        if (!savedUser.getEmail().equals(email)) {
            binding.tilEmail.setError("Email tidak terdaftar");
            return;
        }

        if (!savedUser.getPassword().equals(password)) {
            binding.tilPassword.setError("Password salah");
            return;
        }

        userPreference.setLogin(true);

        binding.btnLogin.setText("Masuk...");
        binding.btnLogin.setEnabled(false);

        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }, 800);
    }
}
