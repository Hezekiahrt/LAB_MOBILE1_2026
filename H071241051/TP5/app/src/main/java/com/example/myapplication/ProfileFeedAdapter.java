package com.example.myapplication;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProfileFeedAdapter extends RecyclerView.Adapter<ProfileFeedAdapter.ViewHolder> {

    private List<Post> postList;
    private OnPostClickListener postClickListener;

    public interface OnPostClickListener {
        void onPostClick(Post post);
    }

    public ProfileFeedAdapter(List<Post> postList, OnPostClickListener postClickListener) {
        this.postList = postList;
        this.postClickListener = postClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);
        
        if (post.getPostImageRes() != -1) {
            holder.ivProfilePost.setImageResource(post.getPostImageRes());
        } else if (post.getPostImageUri() != null) {
            try {
                holder.ivProfilePost.setImageURI(Uri.parse(post.getPostImageUri()));
            } catch (Exception e) {
                holder.ivProfilePost.setImageResource(android.R.drawable.ic_menu_report_image);
            }
        }

        // Show reel icon for some posts to match the image provided
        if (position % 2 == 0) {
            holder.ivPostTypeIcon.setVisibility(View.VISIBLE);
        } else {
            holder.ivPostTypeIcon.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> postClickListener.onPostClick(post));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfilePost, ivPostTypeIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePost = itemView.findViewById(R.id.ivProfilePost);
            ivPostTypeIcon = itemView.findViewById(R.id.ivPostTypeIcon);
        }
    }
}