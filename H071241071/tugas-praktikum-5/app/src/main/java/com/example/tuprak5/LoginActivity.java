package com.example.tuprak5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_login_username);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvGoToRegister = findViewById(R.id.tv_go_to_register);

        btnLogin.setOnClickListener(v -> {
            String inputUsername = etUsername.getText().toString().trim();
            String inputPassword = etPassword.getText().toString().trim();

            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Username dan Password harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE);

            String savedPasswordForUser = sharedPreferences.getString("user_" + inputUsername, null);

            if (savedPasswordForUser != null && savedPasswordForUser.equals(inputPassword)) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);

                editor.putString("saved_username", inputUsername);
                editor.apply();

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show();
            }
        });

        tvGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }
}