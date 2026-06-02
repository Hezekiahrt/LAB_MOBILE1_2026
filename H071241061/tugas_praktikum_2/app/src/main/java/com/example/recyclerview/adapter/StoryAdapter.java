package com.example.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;
import com.example.recyclerview.activity.DetailStoryActivity;
import com.example.recyclerview.activity.PostActivity;
import com.example.recyclerview.model.Story;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private Context          context;
    private ArrayList<Story> storyList;

    public StoryAdapter(Context context, ArrayList<Story> storyList) {
        this.context   = context;
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = storyList.get(position);

        holder.imgStory.setImageResource(story.getImageResId());
        holder.tvLabel.setText(story.getLabel());

        if (story.isMyStory()) {
            holder.ringView.setVisibility(View.INVISIBLE);
            holder.imgStory.setAlpha(1.0f);

            if (holder.btnAddStory != null) {
                holder.btnAddStory.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(v -> {
                v.setScaleX(0.9f);
                v.setScaleY(0.9f);
                v.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
                context.startActivity(new Intent(context, PostActivity.class));
            });

        } else {
            holder.ringView.setVisibility(View.VISIBLE);

            if (holder.btnAddStory != null) {
                holder.btnAddStory.setVisibility(View.GONE);
            }

            if (story.isViewed()) {
                holder.ringView.setBackgroundResource(R.drawable.ring_viewed);
                holder.imgStory.setAlpha(0.6f);
            } else {
                holder.ringView.setBackgroundResource(R.drawable.circle_border_story);
                holder.imgStory.setAlpha(1.0f);
            }

            holder.itemView.setOnClickListener(v -> {
                v.setScaleX(0.9f);
                v.setScaleY(0.9f);
                v.animate().scaleX(1f).scaleY(1f).setDuration(150).start();

                story.setViewed(true);
                notifyItemChanged(position);

                Intent intent = new Intent(context, DetailStoryActivity.class);
                intent.putExtra("imageResId",   story.getImageResId());
                intent.putExtra("profileResId", story.getImageResId());
                intent.putExtra("label",        story.getLabel());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() { return storyList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgStory;
        TextView  tvLabel;
        View      ringView;
        View      btnAddStory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStory    = itemView.findViewById(R.id.imgStory);
            tvLabel     = itemView.findViewById(R.id.tvLabel);
            ringView    = itemView.findViewById(R.id.viewRing);
            btnAddStory = itemView.findViewById(R.id.btnAddStory);
        }
    }
}