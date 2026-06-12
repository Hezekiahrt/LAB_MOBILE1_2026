package com.example.dropby;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

public class LoginFragment extends Fragment {
    private AuthViewModel authViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Scope ViewModel ke Activity agar aman saat Fragment di-recreate
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        EditText etUsername = view.findViewById(R.id.etUsername);
        EditText etPassword = view.findViewById(R.id.etPassword);
        Button btnLogin = view.findViewById(R.id.btnLogin);
        TextView tvGoToRegister = view.findViewById(R.id.tvGoToRegister);

        // Navigasi ke Register
        tvGoToRegister.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment)
        );

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (!username.isEmpty() && !password.isEmpty()) {
                // Panggil tanpa Context — sekarang aman karena ViewModel memakai Application
                authViewModel.login(username, password);
            } else {
                Toast.makeText(getContext(), "Isi semua kolom", Toast.LENGTH_SHORT).show();
            }
        });

        // Observasi hasil dari ViewModel
        authViewModel.getLoginSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {
                // Pindah ke HomeActivity jika berhasil
                Intent intent = new Intent(requireActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        authViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}