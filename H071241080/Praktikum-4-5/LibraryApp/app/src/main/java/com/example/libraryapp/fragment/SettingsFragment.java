package com.example.libraryapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.libraryapp.LoginActivity;
import com.example.libraryapp.R;
import com.example.libraryapp.SharedPrefManager;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPrefManager prefManager = new SharedPrefManager(requireContext());

        TextView tvUsername = view.findViewById(R.id.tvUsername);
        Switch switchDarkMode = view.findViewById(R.id.switchDarkMode);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        tvUsername.setText("👤  " + prefManager.getUsername());
        switchDarkMode.setChecked(prefManager.isDarkMode());

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefManager.setDarkMode(isChecked);
            android.util.Log.d("DARKMODE", "isChecked: " + isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        btnLogout.setOnClickListener(v -> {
            prefManager.logout();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}