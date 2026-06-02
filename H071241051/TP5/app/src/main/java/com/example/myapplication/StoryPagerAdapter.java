package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoryPagerAdapter extends RecyclerView.Adapter<StoryPagerAdapter.ViewHolder> {

    private List<Story> storyList;

    public StoryPagerAdapter(List<Story> storyList) {
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = storyList.get(position);
        holder.ivStoryDetail.setImageResource(story.getImageRes());
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStoryDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStoryDetail = itemView.findViewById(R.id.ivStoryDetail);
        }
    }
}