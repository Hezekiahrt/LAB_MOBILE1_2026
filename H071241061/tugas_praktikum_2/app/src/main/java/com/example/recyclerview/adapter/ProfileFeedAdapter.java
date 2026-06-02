package com.example.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;
import com.example.recyclerview.activity.DetailFeedActivity;
import com.example.recyclerview.model.Post;

import java.util.ArrayList;

public class ProfileFeedAdapter extends RecyclerView.Adapter<ProfileFeedAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Post> postList;

    public ProfileFeedAdapter(Context context, ArrayList<Post> postList) {
        this.context  = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.imgFeed.setImageResource(post.getImageResId());

        holder.imgFeed.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailFeedActivity.class);
            intent.putExtra("imageResId",   post.getImageResId());
            intent.putExtra("profileResId", post.getProfileImageResId());
            intent.putExtra("caption",      post.getCaption());
            intent.putExtra("username",     post.getUsername());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFeed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFeed = itemView.findViewById(R.id.imgFeed);
        }
    }
}