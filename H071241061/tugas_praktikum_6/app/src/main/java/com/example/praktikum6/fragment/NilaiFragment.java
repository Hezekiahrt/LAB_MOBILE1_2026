package com.example.praktikum6.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.praktikum6.databinding.FragmentNilaiBinding;
import com.example.praktikum6.helper.UserPreference;
import com.example.praktikum6.model.UserModel;

public class NilaiFragment extends Fragment {

    private FragmentNilaiBinding binding;
    private UserPreference userPreference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNilaiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPreference = new UserPreference(requireContext());
        loadNilai();
    }

    private void loadNilai() {
        UserModel user = userPreference.getUser();
        String ipk = user.getIpk().isEmpty() ? "0.00" : user.getIpk();
        binding.tvIpkBesar.setText(ipk);

        try {
            double ipkVal = Double.parseDouble(ipk);
            String predikat;
            if (ipkVal >= 3.51) predikat = "Dengan Pujian (Cumlaude) 🏆";
            else if (ipkVal >= 3.01) predikat = "Sangat Memuaskan ⭐";
            else if (ipkVal >= 2.76) predikat = "Memuaskan ✅";
            else if (ipkVal >= 2.00) predikat = "Cukup 📚";
            else predikat = "Perlu Peningkatan 💪";
            binding.tvPredikat.setText(predikat);

            // Progress bar IPK (max 4.0)
            int progress = (int) ((ipkVal / 4.0) * 100);
            binding.progressIpk.setProgress(progress);
        } catch (Exception e) {
            binding.tvPredikat.setText("Belum ada data IPK");
        }

        // Dummy nilai mata kuliah
        binding.tvNilaiContent.setText(
                "Semester ini:\n\n" +
                        "📘 Algoritma & Pemrograman\n   Nilai: A  (4.0)\n\n" +
                        "📗 Basis Data\n   Nilai: A-  (3.7)\n\n" +
                        "📙 Struktur Data\n   Nilai: B+  (3.3)\n\n" +
                        "📕 Kalkulus\n   Nilai: B   (3.0)\n\n" +
                        "📓 Jaringan Komputer\n   Nilai: A  (4.0)\n\n" +
                        "📒 Pemrograman Web\n   Nilai: A- (3.7)\n\n" +
                        "─────────────────────\n" +
                        "Total SKS: 18  |  IP Semester: " + ipk
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
