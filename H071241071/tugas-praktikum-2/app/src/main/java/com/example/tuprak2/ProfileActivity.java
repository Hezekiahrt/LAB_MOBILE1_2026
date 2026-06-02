package com.example.tuprak2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.tuprak2.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (DataRepository.userPosts.isEmpty()) {
            for (int i = 1; i <= 5; i++) {
                int resId = getResources().getIdentifier("img" + i, "drawable", getPackageName());
                DataRepository.userPosts.add(new Post("Me", R.drawable.pfp_sample, resId != 0 ? resId : R.drawable.pfp_sample, "Post " + i));
            }
        }

        if (DataRepository.highlights.isEmpty()) {
            for (int i = 1; i <= 7; i++) {
                int imgNum = 6 + (i % 5);
                int resId = getResources().getIdentifier("img" + imgNum, "drawable", getPackageName());
                DataRepository.highlights.add(resId != 0 ? resId : R.drawable.pfp_sample);
            }
        }

        binding.rvHighlights.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvHighlights.setAdapter(new HighlightAdapter(DataRepository.highlights));

        binding.rvProfileGrid.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvProfileGrid.setAdapter(new ProfileGridAdapter(DataRepository.userPosts));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (binding.rvProfileGrid.getAdapter() != null) {
            binding.rvProfileGrid.getAdapter().notifyDataSetChanged();
        }
    }
}