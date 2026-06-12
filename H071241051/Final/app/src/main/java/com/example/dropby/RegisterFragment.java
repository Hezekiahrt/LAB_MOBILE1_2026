package com.example.dropby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText etUsername = view.findViewById(R.id.etUsername);
        TextInputEditText etEmail = view.findViewById(R.id.etEmail);
        TextInputEditText etPassword = view.findViewById(R.id.etPassword);
        Button btnRegister = view.findViewById(R.id.btnRegister);
        TextView tvGoToLogin = view.findViewById(R.id.tvGoToLogin);

        tvGoToLogin.setOnClickListener(v -> 
            Navigation.findNavController(v).popBackStack()
        );

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                // TODO: Implement registration logic in AuthViewModel
                Toast.makeText(getContext(), "Pendaftaran berhasil (Simulasi)", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).popBackStack();
            } else {
                Toast.makeText(getContext(), "Isi semua kolom", Toast.LENGTH_SHORT).show();
            }
        });
    }
}