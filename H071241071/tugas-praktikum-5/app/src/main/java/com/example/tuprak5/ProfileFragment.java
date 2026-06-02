package com.example.tuprak5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {

    private TextView tvUsername, tvBio;
    private ImageView ivProfilePhoto;
    private RecyclerView rvFavorites;
    private BookAdapter bookAdapter;
    private ProgressBar progressBar;

    private DrawerLayout drawerLayout;
    private ImageButton btnMenu;
    private Switch switchTheme;
    private LinearLayout layoutLogout, layoutEditProfile;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawerLayout = view.findViewById(R.id.drawer_layout);
        btnMenu = view.findViewById(R.id.btn_menu_profile);
        switchTheme = view.findViewById(R.id.switch_theme_profile);
        layoutLogout = view.findViewById(R.id.layout_logout);
        layoutEditProfile = view.findViewById(R.id.layout_edit_profile);
        tvUsername = view.findViewById(R.id.tv_profile_username);
        tvBio = view.findViewById(R.id.tv_profile_bio);
        ivProfilePhoto = view.findViewById(R.id.iv_profile_photo);
        rvFavorites = view.findViewById(R.id.rv_profile_favorites);
        progressBar = view.findViewById(R.id.progressBarProfile);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);

        rvFavorites.setLayoutManager(new GridLayoutManager(getContext(), 2));
        bookAdapter = new BookAdapter(new ArrayList<>(), true);
        rvFavorites.setAdapter(bookAdapter);

        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));

        boolean forceOpen = sharedPreferences.getBoolean("force_open_drawer", false);
        if (forceOpen) {
            drawerLayout.openDrawer(GravityCompat.END);
            sharedPreferences.edit().putBoolean("force_open_drawer", false).apply();
        }

        boolean isDarkMode = sharedPreferences.getBoolean("dark mode", false);
        switchTheme.setChecked(isDarkMode);

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!buttonView.isPressed()) return;

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("dark mode", isChecked);
            editor.putBoolean("force_open_drawer", true);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        layoutEditProfile.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.END);
            startActivity(new Intent(requireActivity(), EditProfileActivity.class));
        });

        layoutLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProfileData();
        loadFavoriteBooks();
    }

    private void loadProfileData() {
        SharedPreferences pref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String username = pref.getString("saved_username", "Pengguna");

        tvUsername.setText(username);

        String savedBio = pref.getString("bio_" + username, "Mahasiswa Sistem Informasi Universitas Hasanuddin");
        String savedPhotoPath = pref.getString("photo_" + username, null);

        tvBio.setText(savedBio);

        if (savedPhotoPath != null) {
            File file = new File(savedPhotoPath);
            if (file.exists()) {
                ivProfilePhoto.setImageURI(Uri.fromFile(file));
            } else {
                ivProfilePhoto.setImageResource(R.mipmap.ic_launcher_round);
            }
        } else {
            ivProfilePhoto.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    private void loadFavoriteBooks() {
        progressBar.setVisibility(View.VISIBLE);
        rvFavorites.setVisibility(View.GONE);

        executorService.execute(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Book> favoriteBooks = new ArrayList<>();
            for (Book book : DataSource.books) {
                if (book.isLiked()) {
                    favoriteBooks.add(book);
                }
            }

            handler.post(() -> {
                if (isAdded()) {
                    progressBar.setVisibility(View.GONE);
                    rvFavorites.setVisibility(View.VISIBLE);
                    bookAdapter.setFilteredList(favoriteBooks);
                }
            });
        });
    }
}