package com.example.praktikum6;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.praktikum6.databinding.ActivityMainBinding;
import com.example.praktikum6.fragment.HomeFragment;
import com.example.praktikum6.fragment.JadwalFragment;
import com.example.praktikum6.fragment.NilaiFragment;
import com.example.praktikum6.fragment.ProfileFragment;
import com.example.praktikum6.helper.UserPreference;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserPreference userPref = new UserPreference(this);
        if (!userPref.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        loadFragment(new HomeFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                loadFragment(new HomeFragment());
            } else if (id == R.id.nav_jadwal) {
                loadFragment(new JadwalFragment());
            } else if (id == R.id.nav_nilai) {
                loadFragment(new NilaiFragment());
            } else if (id == R.id.nav_profile) {
                loadFragment(new ProfileFragment());
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
