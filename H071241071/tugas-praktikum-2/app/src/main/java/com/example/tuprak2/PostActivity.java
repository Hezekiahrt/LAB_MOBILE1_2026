package com.example.tuprak2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tuprak2.databinding.ActivityPostBinding;

public class PostActivity extends AppCompatActivity {
    private ActivityPostBinding binding;
    private Uri selectedUri;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityResultLauncher<String[]> pick = registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
            if (uri != null) {
                selectedUri = uri;
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                binding.ivPreview.setImageURI(uri);
            }
        });

        binding.btnPick.setOnClickListener(v -> pick.launch(new String[]{"image/*"}));

        binding.btnUpload.setOnClickListener(v -> {
            String caption = binding.etCaption.getText().toString();
            if (selectedUri != null && !caption.isEmpty()) {
                DataRepository.userPosts.add(0, new Post("fadeeru", R.drawable.pfp_sample, selectedUri, caption));
                DataRepository.homeFeeds.add(0, new Post("fadeeru", R.drawable.pfp_sample, selectedUri, caption));
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Data belum lengkap!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}