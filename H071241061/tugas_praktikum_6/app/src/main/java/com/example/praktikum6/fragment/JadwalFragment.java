package com.example.praktikum6.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.praktikum6.databinding.FragmentJadwalBinding;

public class JadwalFragment extends Fragment {

    private FragmentJadwalBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentJadwalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTabJadwal();
    }

    private void setupTabJadwal() {
        // Default tampilkan Senin
        showJadwalSenin();

        binding.chipSenin.setOnClickListener(v -> showJadwalSenin());
        binding.chipSelasa.setOnClickListener(v -> showJadwalSelasa());
        binding.chipRabu.setOnClickListener(v -> showJadwalRabu());
        binding.chipKamis.setOnClickListener(v -> showJadwalKamis());
        binding.chipJumat.setOnClickListener(v -> showJadwalJumat());
        binding.chipSabtu.setOnClickListener(v -> showJadwalSabtu());
    }

    private void showJadwalSenin() {
        binding.tvJadwalContent.setText(
                "🕖 07:30 - 09:10\n" +
                        "Algoritma & Pemrograman\n" +
                        "📍 Lab Komputer A | 👨‍🏫 Dr. Budi Santoso\n\n" +
                        "🕙 10:00 - 11:40\n" +
                        "Kalkulus\n" +
                        "📍 Ruang 301 | 👨‍🏫 Prof. Ahmad Fauzi\n\n" +
                        "🕐 13:00 - 14:40\n" +
                        "Bahasa Indonesia\n" +
                        "📍 Ruang 204 | 👩‍🏫 Ibu Sari Dewi"
        );
    }

    private void showJadwalSelasa() {
        binding.tvJadwalContent.setText(
                "🕗 08:00 - 09:40\n" +
                        "Basis Data\n" +
                        "📍 Lab Komputer B | 👨‍🏫 Dr. Hendra\n\n" +
                        "🕙 11:00 - 12:40\n" +
                        "Jaringan Komputer\n" +
                        "📍 Ruang 305 | 👨‍🏫 Ir. Surya\n\n" +
                        "🕑 14:00 - 15:40\n" +
                        "Pemrograman Web\n" +
                        "📍 Lab Web | 👩‍🏫 Ibu Maya"
        );
    }

    private void showJadwalRabu() {
        binding.tvJadwalContent.setText(
                "🕖 07:30 - 09:10\n" +
                        "Struktur Data\n" +
                        "📍 Ruang 201 | 👨‍🏫 Dr. Rizki\n\n" +
                        "🕙 10:30 - 12:10\n" +
                        "Sistem Operasi\n" +
                        "📍 Ruang 302 | 👨‍🏫 Prof. Wahyu"
        );
    }

    private void showJadwalKamis() {
        binding.tvJadwalContent.setText(
                "🕘 09:00 - 10:40\n" +
                        "Rekayasa Perangkat Lunak\n" +
                        "📍 Ruang 401 | 👨‍🏫 Dr. Andi\n\n" +
                        "🕐 13:00 - 14:40\n" +
                        "Kecerdasan Buatan\n" +
                        "📍 Lab AI | 👨‍🏫 Prof. Dian"
        );
    }

    private void showJadwalJumat() {
        binding.tvJadwalContent.setText(
                "🕖 07:30 - 09:10\n" +
                        "Pemrograman Mobile\n" +
                        "📍 Lab Mobile | 👨‍🏫 Dr. Kevin\n\n" +
                        "🕙 10:00 - 11:40\n" +
                        "Etika Profesi\n" +
                        "📍 Ruang 101 | 👩‍🏫 Ibu Lina"
        );
    }

    private void showJadwalSabtu() {
        binding.tvJadwalContent.setText(
                "🕗 08:00 - 11:00\n" +
                        "Praktikum Basis Data\n" +
                        "📍 Lab Komputer C | 👨‍🏫 Asisten Lab\n\n" +
                        "🎉 Tidak ada kuliah lagi!\nSelamat beristirahat 😄"
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
