package com.example.myapplication;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<Post> postList;
    private OnUserClickListener userClickListener;

    public interface OnUserClickListener {
        void onUserClick(Post post);
    }

    public FeedAdapter(List<Post> postList, OnUserClickListener userClickListener) {
        this.postList = postList;
        this.userClickListener = userClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.tvUsername.setText(post.getUsername());
        holder.tvCaption.setText(post.getCaption());
        
        // Post Image
        if (post.getPostImageRes() != -1) {
            holder.ivPost.setImageResource(post.getPostImageRes());
        } else if (post.getPostImageUri() != null) {
            try {
                holder.ivPost.setImageURI(Uri.parse(post.getPostImageUri()));
            } catch (Exception e) {
                holder.ivPost.setImageResource(android.R.drawable.ic_menu_report_image);
            }
        } else {
            holder.ivPost.setImageDrawable(null);
        }

        // Profile Image
        if (post.getProfileImageRes() != -1) {
            holder.ivProfile.setImageResource(post.getProfileImageRes());
        } else if (post.getProfileImageUri() != null) {
            try {
                holder.ivProfile.setImageURI(Uri.parse(post.getProfileImageUri()));
            } catch (Exception e) {
                holder.ivProfile.setImageResource(android.R.drawable.presence_online);
            }
        } else {
            holder.ivProfile.setImageResource(android.R.drawable.presence_online);
        }

        View.OnClickListener listener = v -> userClickListener.onUserClick(post);
        holder.ivProfile.setOnClickListener(listener);
        holder.tvUsername.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile, ivPost;
        TextView tvUsername, tvCaption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivItemProfile);
            ivPost = itemView.findViewById(R.id.ivItemPost);
            tvUsername = itemView.findViewById(R.id.tvItemUsername);
            tvCaption = itemView.findViewById(R.id.tvItemCaption);
        }
    }
}