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

        // Set visual tombol bookmark/like jika sudah pernah ditekan
        if (post.isBookmarked) {
            holder.btnBookmark.setColorFilter(0xFFE07A5F); // Warna Terakota aktif
        } else {
            holder.btnBookmark.clearColorFilter();
        }

        // TODO: Anda bisa menambahkan aksi klik untuk tombol Like dan Bookmark di sini nanti
    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

    // Class ViewHolder
    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthorName, tvPlaceName, tvRating, tvCaption;
        ImageView ivPostImage;
        ImageButton btnLike, btnBookmark;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthorName = itemView.findViewById(R.id.tvAuthorName);
            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnBookmark = itemView.findViewById(R.id.btnBookmark);
        }
    }
}