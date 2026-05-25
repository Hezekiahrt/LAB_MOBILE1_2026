package com.example.tuprak2;

import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProfileGridAdapter extends RecyclerView.Adapter<ProfileGridAdapter.ViewHolder> {
    private List<Post> list;

    public ProfileGridAdapter(List<Post> list) { this.list = list; }

    @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int v) {
        ImageView iv = new ImageView(p.getContext());
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 350));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setPadding(2, 2, 2, 2);
        return new ViewHolder(iv);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder h, int p) {
        Post post = list.get(p);
        ImageView iv = (ImageView) h.itemView;

        if (post.getPostUri() != null) {
            iv.setImageURI(post.getPostUri());
        } else {
            iv.setImageResource(post.getPostImage());
        }
    }

    @Override public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(android.view.View v) { super(v); }
    }
}