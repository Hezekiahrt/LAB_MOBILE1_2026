package com.example.tugas_praktikum_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText etName = findViewById(R.id.etEditName);
        EditText etUser = findViewById(R.id.etEditUsername);
        Button btnSave = findViewById(R.id.btnSave);

        etName.setText(getIntent().getStringExtra("old_name"));
        etUser.setText(getIntent().getStringExtra("old_user"));

        btnSave.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("res_name", etName.getText().toString());
            result.putExtra("res_user", etUser.getText().toString());

            setResult(Activity.RESULT_OK, result);
            finish();
        });
    }
}