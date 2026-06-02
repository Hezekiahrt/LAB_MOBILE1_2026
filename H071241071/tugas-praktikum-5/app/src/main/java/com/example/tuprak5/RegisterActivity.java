package com.example.tuprak5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnRegister;
    private TextView tvGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.et_reg_username);
        etPassword = findViewById(R.id.et_reg_password);
        btnRegister = findViewById(R.id.btn_register);
        tvGoToLogin = findViewById(R.id.tv_go_to_login);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE);


                if (sharedPreferences.contains("user_" + username)) {
                    Toast.makeText(this, "Username sudah digunakan, cari yang lain!", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("user_" + username, password);
                    editor.apply();

                    Toast.makeText(this, "Registrasi Berhasil! Silakan Login.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });

        tvGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }
}