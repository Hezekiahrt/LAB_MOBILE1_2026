package com.example.praktikum6;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.praktikum6.databinding.ActivityEditProfileBinding;
import com.example.praktikum6.helper.UserPreference;
import com.example.praktikum6.model.UserModel;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userPreference = new UserPreference(this);

        loadUserData();
        setupClickListeners();
    }

    private void loadUserData() {
        UserModel user = userPreference.getUser();
        binding.etName.setText(user.getName());
        binding.etNim.setText(user.getNim());
        binding.etEmail.setText(user.getEmail());
        binding.etPhone.setText(user.getPhone());
        binding.etJurusan.setText(user.getJurusan());
        binding.etSemester.setText(user.getSemester());
        binding.etIpk.setText(user.getIpk());
    }

    private void setupClickListeners() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnSave.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String name = binding.etName.getText().toString().trim();
        String nim = binding.etNim.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();
        String jurusan = binding.etJurusan.getText().toString().trim();
        String semester = binding.etSemester.getText().toString().trim();
        String ipk = binding.etIpk.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            binding.tilName.setError("Nama tidak boleh kosong");
            return;
        }
        if (TextUtils.isEmpty(nim)) {
            binding.tilNim.setError("NIM tidak boleh kosong");
            return;
        }

        // Validate IPK
        if (!TextUtils.isEmpty(ipk)) {
            try {
                double ipkVal = Double.parseDouble(ipk);
                if (ipkVal < 0.0 || ipkVal > 4.0) {
                    binding.tilIpk.setError("IPK harus antara 0.00 - 4.00");
                    return;
                }
            } catch (NumberFormatException e) {
                binding.tilIpk.setError("Format IPK tidak valid");
                return;
            }
        }

        UserModel currentUser = userPreference.getUser();
        currentUser.setName(name);
        currentUser.setNim(nim);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setJurusan(jurusan);
        currentUser.setSemester(semester);
        currentUser.setIpk(TextUtils.isEmpty(ipk) ? "0.00" : ipk);
        userPreference.saveUser(currentUser);

        Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
