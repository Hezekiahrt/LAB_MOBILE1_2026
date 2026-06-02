package com.example.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;
import com.example.recyclerview.adapter.GalleryAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class PostActivity extends AppCompatActivity {

    private ImageView imgPreview, imgThumb;
    private int selectedImageResId;

    private ArrayList<Integer> imageList = new ArrayList<>(Arrays.asList(
            R.drawable.post1,
            R.drawable.post2,
            R.drawable.post3,
            R.drawable.post4,
            R.drawable.post5,
            R.drawable.post1,
            R.drawable.post3,
            R.drawable.post5,
            R.drawable.post2,
            R.drawable.post4
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imgPreview       = findViewById(R.id.imgPreview);
        imgThumb         = findViewById(R.id.imgThumb);
        EditText etCaption = findViewById(R.id.etCaption);
        TextView btnShare  = findViewById(R.id.btnShare);
        ImageView btnBack  = findViewById(R.id.btnBackPost);

        selectedImageResId = imageList.get(0);
        imgPreview.setImageResource(selectedImageResId);
        imgThumb.setImageResource(selectedImageResId);

        RecyclerView rvGallery = findViewById(R.id.rvGallery);
        rvGallery.setLayoutManager(new GridLayoutManager(this, 3));
        rvGallery.setAdapter(new GalleryAdapter(this, imageList, resId -> {
            selectedImageResId = resId;
            imgPreview.setImageResource(resId);
            imgThumb.setImageResource(resId);
        }));

        btnShare.setOnClickListener(v -> {
            String caption = etCaption.getText().toString().trim();
            if (caption.isEmpty()) {
                Toast.makeText(this, "Tulis caption dulu!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent result = new Intent();
            result.putExtra("imageResId", selectedImageResId);
            result.putExtra("caption", caption);
            setResult(RESULT_OK, result);
            finish();
        });

        btnBack.setOnClickListener(v -> finish());
    }
}