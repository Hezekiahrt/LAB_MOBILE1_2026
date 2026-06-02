package com.example.libraryapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.libraryapp.fragment.AddBookFragment;
import com.example.libraryapp.fragment.FavoritesFragment;
import com.example.libraryapp.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private FavoritesFragment favoritesFragment;
    private AddBookFragment addBookFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment      = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        addBookFragment   = new AddBookFragment();

        loadFragment(homeFragment);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                loadFragment(homeFragment);
            } else if (id == R.id.nav_favorites) {
                loadFragment(favoritesFragment);
            } else if (id == R.id.nav_add) {
                loadFragment(addBookFragment);
            } else {
                return false;
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}