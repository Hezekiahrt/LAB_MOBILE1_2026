package com.example.dropby;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dropby.data.local.AppDatabase;

public class FriendListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        ListView lvFriends = findViewById(R.id.lvFriends);

        AppDatabase.getInstance(this).postDao().getAllAuthors().observe(this, authors -> {
            if (authors != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, authors);
                lvFriends.setAdapter(adapter);

                lvFriends.setOnItemClickListener((parent, view, position, id) -> {
                    String authorName = authors.get(position);
                    Intent intent = new Intent(FriendListActivity.this, DummyProfileActivity.class);
                    intent.putExtra("authorName", authorName);
                    startActivity(intent);
                });
            }
        });
    }
}
