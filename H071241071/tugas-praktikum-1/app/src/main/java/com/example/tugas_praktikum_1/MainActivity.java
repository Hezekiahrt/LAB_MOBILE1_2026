package com.example.tugas_praktikum_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvName, tvUser;

    private final ActivityResultLauncher<Intent> editLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    // Update tampilan setelah edit
                    tvName.setText(result.getData().getStringExtra("res_name"));
                    tvUser.setText(result.getData().getStringExtra("res_user"));
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvFullName);
        tvUser = findViewById(R.id.tvUsername);
        Button btnEdit = findViewById(R.id.btnEditProfile);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            intent.putExtra("old_name", tvName.getText().toString());
            intent.putExtra("old_user", tvUser.getText().toString());
            editLauncher.launch(intent);
        });
    }
}