package com.example.praktikum6.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.praktikum6.EditProfileActivity;
import com.example.praktikum6.SettingActivity;
import com.example.praktikum6.databinding.FragmentProfileBinding;
import com.example.praktikum6.helper.UserPreference;
import com.example.praktikum6.model.UserModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private UserPreference userPreference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPreference = new UserPreference(requireContext());
        loadProfile();
        setupClickListeners();
    }

    private void loadProfile() {
        UserModel user = userPreference.getUser();

        if (!user.getName().isEmpty()) {
            String[] parts = user.getName().split(" ");
            String inisial = parts.length >= 2
                    ? String.valueOf(parts[0].charAt(0)) + String.valueOf(parts[1].charAt(0))
                    : String.valueOf(parts[0].charAt(0));
            binding.tvAvatar.setText(inisial.toUpperCase());
        }

        binding.tvName.setText(user.getName());
        binding.tvNim.setText(user.getNim());
        binding.tvEmail.setText(user.getEmail().isEmpty() ? "-" : user.getEmail());
        binding.tvPhone.setText(user.getPhone().isEmpty() ? "-" : user.getPhone());
        binding.tvJurusan.setText(user.getJurusan().isEmpty() ? "-" : user.getJurusan());
        binding.tvSemester.setText(user.getSemester().isEmpty() ? "-" : "Semester " + user.getSemester());
        binding.tvIpk.setText(user.getIpk().isEmpty() ? "0.00" : user.getIpk());
    }

    private void setupClickListeners() {
        binding.btnEditProfile.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), EditProfileActivity.class));
        });

        binding.btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), SettingActivity.class));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProfile();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
