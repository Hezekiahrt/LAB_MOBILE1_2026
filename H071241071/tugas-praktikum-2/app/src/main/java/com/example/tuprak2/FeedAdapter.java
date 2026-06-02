package com.example.tuprak2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tuprak2.databinding.ItemFeedBinding;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private List<Post> list;
    private OnClick listener;
    public interface OnClick { void onProfileClick(); }
    public FeedAdapter(List<Post> list, OnClick listener) { this.list = list; this.listener = listener; }
    @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new ViewHolder(ItemFeedBinding.inflate(LayoutInflater.from(p.getContext()), p, false));
    }
    @Override public void onBindViewHolder(@NonNull ViewHolder h, int p) {
        Post post = list.get(p);
        h.b.tvUsername.setText(post.getUsername());
        h.b.tvCaption.setText(post.getCaption());
        h.b.ivProfile.setImageResource(post.getProfileImage());
        if (post.getPostUri() != null) h.b.ivPost.setImageURI(post.getPostUri());
        else h.b.ivPost.setImageResource(post.getPostImage());
        h.b.layoutHeader.setOnClickListener(v -> listener.onProfileClick());
    }
    @Override public int getItemCount() { return list.size(); }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemFeedBinding b;
        public ViewHolder(ItemFeedBinding b) { super(b.getRoot()); this.b = b; }
    }
}