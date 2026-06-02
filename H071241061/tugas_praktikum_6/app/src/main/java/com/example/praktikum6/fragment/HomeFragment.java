package com.example.praktikum6.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.praktikum6.SettingActivity;
import com.example.praktikum6.databinding.FragmentHomeBinding;
import com.example.praktikum6.helper.UserPreference;
import com.example.praktikum6.model.UserModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private UserPreference userPreference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPreference = new UserPreference(requireContext());

        loadDashboard();
        setupClickListeners();
    }

    private void loadDashboard() {
        UserModel user = userPreference.getUser();

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String greeting;
        String emoji;
        if (hour >= 5 && hour < 12) {
            greeting = "Selamat Pagi";
            emoji = "☀️";
        } else if (hour >= 12 && hour < 15) {
            greeting = "Selamat Siang";
            emoji = "🌤️";
        } else if (hour >= 15 && hour < 18) {
            greeting = "Selamat Sore";
            emoji = "🌅";
        } else {
            greeting = "Selamat Malam";
            emoji = "🌙";
        }

        String firstName = user.getName().split(" ")[0];
        binding.tvGreeting.setText(greeting + ", " + firstName + "! " + emoji);
        binding.tvDate.setText(getCurrentDate());
        binding.tvName.setText(user.getName());
        binding.tvNim.setText(user.getNim());
        binding.tvJurusan.setText(user.getJurusan());
        binding.tvSemester.setText("Semester " + user.getSemester());

        // IPK with color
        String ipk = user.getIpk();
        binding.tvIpk.setText(ipk);
        try {
            double ipkVal = Double.parseDouble(ipk);
            if (ipkVal >= 3.5) {
                binding.tvIpkLabel.setText("IPK 🏆");
            } else if (ipkVal >= 3.0) {
                binding.tvIpkLabel.setText("IPK ✅");
            } else {
                binding.tvIpkLabel.setText("IPK 📚");
            }
        } catch (Exception ignored) {}

        // Avatar inisial dari nama
        if (!user.getName().isEmpty()) {
            String[] parts = user.getName().split(" ");
            String inisial = parts.length >= 2
                    ? String.valueOf(parts[0].charAt(0)) + String.valueOf(parts[1].charAt(0))
                    : String.valueOf(parts[0].charAt(0));
            binding.tvAvatar.setText(inisial.toUpperCase());
        }

        // Hari kuliah
        String hariIni = new SimpleDateFormat("EEEE", new Locale("id", "ID")).format(new Date());
        binding.tvHariIni.setText("Jadwal " + hariIni);

        loadJadwalHariIni(hariIni);
    }

    private void loadJadwalHariIni(String hari) {
        // Dummy jadwal berdasarkan hari
        String jadwal = "";
        switch (hari) {
            case "Senin":
                jadwal = "• 07:30 - Algoritma & Pemrograman\n• 10:00 - Kalkulus\n• 13:00 - Bahasa Indonesia";
                break;
            case "Selasa":
                jadwal = "• 08:00 - Basis Data\n• 11:00 - Jaringan Komputer\n• 14:00 - Pemrograman Web";
                break;
            case "Rabu":
                jadwal = "• 07:30 - Struktur Data\n• 10:30 - Sistem Operasi";
                break;
            case "Kamis":
                jadwal = "• 09:00 - Rekayasa Perangkat Lunak\n• 13:00 - Kecerdasan Buatan";
                break;
            case "Jumat":
                jadwal = "• 07:30 - Pemrograman Mobile\n• 10:00 - Etika Profesi";
                break;
            case "Sabtu":
                jadwal = "• 08:00 - Praktikum Basis Data";
                break;
            default:
                jadwal = "🎉 Tidak ada kuliah hari ini!\nSelamat beristirahat.";
                break;
        }
        binding.tvJadwalHariIni.setText(jadwal);
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
        return sdf.format(new Date());
    }

    private void setupClickListeners() {
        binding.btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), SettingActivity.class));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDashboard(); // Reload saat kembali dari edit profil
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
