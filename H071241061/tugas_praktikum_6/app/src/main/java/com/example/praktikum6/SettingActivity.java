package com.example.praktikum6;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.praktikum6.databinding.ActivitySettingBinding;
import com.example.praktikum6.helper.SettingPreference;
import com.example.praktikum6.helper.UserPreference;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;
    private SettingPreference settingPref;
    private UserPreference userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settingPref = new SettingPreference(this);
        userPref = new UserPreference(this);

        setupUI();
        setupClickListeners();
    }

    private void setupUI() {
        binding.switchDarkMode.setChecked(settingPref.isDarkMode());
        binding.switchNotification.setChecked(settingPref.isNotificationEnabled());

        String lang = settingPref.getLanguage();
        binding.tvCurrentLanguage.setText(lang);
    }

    private void setupClickListeners() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.switchDarkMode.setOnCheckedChangeListener((btn, isChecked) -> {
            settingPref.setDarkMode(isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            recreate();
        });

        binding.switchNotification.setOnCheckedChangeListener((btn, isChecked) -> {
            settingPref.setNotification(isChecked);
        });

        binding.layoutLanguage.setOnClickListener(v -> showLanguageDialog());

        binding.btnLogout.setOnClickListener(v -> showLogoutDialog());

        binding.layoutAbout.setOnClickListener(v -> showAboutDialog());
    }

    private void showLanguageDialog() {
        String[] languages = {"Indonesia", "English"};
        int selected = settingPref.getLanguage().equals("Indonesia") ? 0 : 1;

        new AlertDialog.Builder(this)
                .setTitle("Pilih Bahasa")
                .setSingleChoiceItems(languages, selected, (dialog, which) -> {
                    settingPref.setLanguage(languages[which]);
                    binding.tvCurrentLanguage.setText(languages[which]);
                    dialog.dismiss();
                })
                .show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setPositiveButton("Ya, Keluar", (dialog, which) -> {
                    userPref.logout();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Tentang Aplikasi")
                .setMessage("📱 Mahasiswa Dashboard\n\nVersi: 1.0.0\nPraktikum 6 - SharedPreferences\n\nDibuat dengan ❤️ menggunakan Android Java")
                .setPositiveButton("OK", null)
                .show();
    }
}
