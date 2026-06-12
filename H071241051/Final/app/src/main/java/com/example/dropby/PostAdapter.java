package com.example.dropby;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.dropby.data.local.PostEntity;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<PostEntity> postList = new ArrayList<>();
    private OnPostInteractionListener listener;

    public interface OnPostInteractionListener {
        void onLikeClick(PostEntity post);
        void onBookmarkClick(PostEntity post);
        void onAuthorClick(String authorName);
        void onPostLongClick(PostEntity post);
    }

    public void setListener(OnPostInteractionListener listener) {
        this.listener = listener;
    }

    // Fungsi untuk memperbarui data saat ada perubahan dari Room/API
    public void setPosts(List<PostEntity> posts) {
        this.postList = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostEntity post = postList.get(position);

        holder.tvAuthorName.setText(post.authorName);
        holder.tvPlaceName.setText(post.placeName);
        holder.tvRating.setText("Rating: " + post.rating + "/10");
        holder.tvCaption.setText(post.caption);

        // Memuat gambar menggunakan Glide (Otomatis handle cache & memory)
        Glide.with(holder.itemView.getContext())
                .load(post.imageUrl)
                .centerCrop()
                .into(holder.ivPostImage);

        int currentNightMode = holder.itemView.getContext().getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        boolean isDarkMode = currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES;

        // Set visual tombol bookmark/like jika sudah pernah ditekan
        if (post.isBookmarked) {
            holder.btnBookmark.setColorFilter(0xFFE07A5F); // Warna Terakota aktif
        } else {
            if (isDarkMode) holder.btnBookmark.setColorFilter(android.graphics.Color.WHITE);
            else holder.btnBookmark.clearColorFilter();
        }

        if (post.isLiked) {
            holder.btnLike.setColorFilter(0xFFE07A5F);
        } else {
            if (isDarkMode) holder.btnLike.setColorFilter(android.graphics.Color.WHITE);
            else holder.btnLike.clearColorFilter();
        }

        if (post.location != null && !post.location.isEmpty()) {
            holder.tvLocation.setText("📍 " + post.location);
            holder.tvLocation.setVisibility(android.view.View.VISIBLE);
        } else {
            holder.tvLocation.setVisibility(android.view.View.GONE);
        }

        holder.tvLocation.setOnClickListener(v -> {
            if (post.location != null && !post.location.isEmpty()) {
                android.net.Uri gmmIntentUri = android.net.Uri.parse("geo:0,0?q=" + android.net.Uri.encode(post.location));
                android.content.Intent mapIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW, gmmIntentUri);
                try {
                    v.getContext().startActivity(mapIntent);
                } catch (android.content.ActivityNotFoundException e) {
                    android.widget.Toast.makeText(v.getContext(), "Aplikasi peta tidak ditemukan di perangkat", android.widget.Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnLike.setOnClickListener(v -> {
            if (listener != null) listener.onLikeClick(post);
        });

        holder.btnBookmark.setOnClickListener(v -> {
            if (listener != null) listener.onBookmarkClick(post);
        });

        holder.tvAuthorName.setOnClickListener(v -> {
            if (listener != null) listener.onAuthorClick(post.authorName);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onPostLongClick(post);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

    // Class ViewHolder
    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthorName, tvPlaceName, tvLocation, tvRating, tvCaption;
        ImageView ivPostImage;
        ImageButton btnLike, btnBookmark;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthorName = itemView.findViewById(R.id.tvAuthorName);
            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnBookmark = itemView.findViewById(R.id.btnBookmark);
        }
    }
}