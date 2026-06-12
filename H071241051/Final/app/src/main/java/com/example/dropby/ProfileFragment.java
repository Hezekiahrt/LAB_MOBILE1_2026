package com.example.dropby;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.dropby.data.remote.ApiClient;
import com.example.dropby.utils.ThemeManager;
import com.example.dropby.utils.TokenManager;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ProfileFragment extends Fragment {

    private TokenManager tokenManager;
    private ImageView ivProfilePicture;
    private TextView tvUsername;

    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    Uri localUri = copyUriToLocalFile(uri);
                    if (localUri != null) {
                        tokenManager.saveProfilePicture(localUri.toString());
                        ivProfilePicture.setImageURI(localUri);
                        syncProfileToServer(tvUsername.getText().toString(), localUri.toString());
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tokenManager = new TokenManager(requireContext());
        ThemeManager themeManager = new ThemeManager(requireContext());

        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        tvUsername = view.findViewById(R.id.tvUsername);

        // ── Tampilkan Username & Foto Profil ──────────────────────────────
        String savedUsername = tokenManager.getUsername();
        if (savedUsername != null && !savedUsername.isEmpty()) {
            tvUsername.setText(savedUsername);
        }

        String savedPic = tokenManager.getProfilePicture();
        if (savedPic != null && !savedPic.isEmpty()) {
            ivProfilePicture.setImageURI(Uri.parse(savedPic));
        } else {
            ivProfilePicture.setImageResource(R.mipmap.ic_launcher_round);
        }

        // ── Edit Profile Actions ────────────────────────────
        MaterialCardView cardProfilePicture = view.findViewById(R.id.cardProfilePicture);
        cardProfilePicture.setOnClickListener(v -> mGetContent.launch("image/*"));

        LinearLayout btnEditUsername = view.findViewById(R.id.btnEditUsername);
        btnEditUsername.setOnClickListener(v -> {
            EditText input = new EditText(requireContext());
            input.setText(tvUsername.getText().toString());
            
            // Tambahkan padding agar lebih rapi
            int padding = (int) (16 * getResources().getDisplayMetrics().density);
            input.setPadding(padding, padding, padding, padding);

            new AlertDialog.Builder(requireContext())
                    .setTitle("Ubah Username")
                    .setView(input)
                    .setPositiveButton("Simpan", (dialog, which) -> {
                        String newName = input.getText().toString().trim();
                        if (!newName.isEmpty()) {
                            tvUsername.setText(newName);
                            tokenManager.updateUsername(newName);
                            syncProfileToServer(newName, tokenManager.getProfilePicture());
                        }
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

        // ── Checkmark indicators ────────────────────────────
        ImageView checkSystem = view.findViewById(R.id.checkSystem);
        ImageView checkLight  = view.findViewById(R.id.checkLight);
        ImageView checkDark   = view.findViewById(R.id.checkDark);

        updateCheckmarks(themeManager.getMode(), checkSystem, checkLight, checkDark);

        // ── Theme Option Listeners ──────────────────────────
        LinearLayout optionSystem = view.findViewById(R.id.optionSystem);
        LinearLayout optionLight  = view.findViewById(R.id.optionLight);
        LinearLayout optionDark   = view.findViewById(R.id.optionDark);

        optionSystem.setOnClickListener(v -> {
            themeManager.saveMode(ThemeManager.MODE_SYSTEM);
            themeManager.applyTheme();
            updateCheckmarks(ThemeManager.MODE_SYSTEM, checkSystem, checkLight, checkDark);
        });

        optionLight.setOnClickListener(v -> {
            themeManager.saveMode(ThemeManager.MODE_LIGHT);
            themeManager.applyTheme();
            updateCheckmarks(ThemeManager.MODE_LIGHT, checkSystem, checkLight, checkDark);
        });

        optionDark.setOnClickListener(v -> {
            themeManager.saveMode(ThemeManager.MODE_DARK);
            themeManager.applyTheme();
            updateCheckmarks(ThemeManager.MODE_DARK, checkSystem, checkLight, checkDark);
        });

        // ── Logout ──────────────────────────────────────────
        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            tokenManager.logout();
            ApiClient.reset(); // Hapus cache Retrofit agar token lama tidak tersimpan
            Intent intent = new Intent(getActivity(), AuthActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    private Uri copyUriToLocalFile(Uri uri) {
        try {
            InputStream in = requireContext().getContentResolver().openInputStream(uri);
            File file = new File(requireContext().getFilesDir(), "profile_pic_" + System.currentTimeMillis() + ".jpg");
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Gagal memproses gambar", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void syncProfileToServer(String username, String profilePicUri) {
        // Karena endpoint backend belum tersedia sepenuhnya (masih pakai api.dropby.com dummy),
        // kita hanya tampilkan pesan berhasil secara lokal.
        // Jika API sudah siap, panggil ApiClient.getService(requireContext()).updateProfile(...)
        Toast.makeText(requireContext(), "Profil berhasil diperbarui secara lokal!", Toast.LENGTH_SHORT).show();
    }

    private void updateCheckmarks(int activeMode, ImageView checkSystem, ImageView checkLight, ImageView checkDark) {
        checkSystem.setVisibility(activeMode == ThemeManager.MODE_SYSTEM ? View.VISIBLE : View.GONE);
        checkLight.setVisibility(activeMode  == ThemeManager.MODE_LIGHT  ? View.VISIBLE : View.GONE);
        checkDark.setVisibility(activeMode   == ThemeManager.MODE_DARK   ? View.VISIBLE : View.GONE);
    }
}