package com.example.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;
import com.example.recyclerview.activity.ProfileActivity;
import com.example.recyclerview.model.Post;

import java.util.ArrayList;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Post> list;

    public HomeFeedAdapter(Context context, ArrayList<Post> list) {
        this.context = context;
        this.list    = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post data = list.get(position);

        holder.imgProf.setImageResource(data.getProfileImageResId());
        holder.imgPost.setImageResource(data.getImageResId());
        holder.tvUser.setText(data.getUsername());

        String full = data.getUsername() + "  " + data.getCaption();
        SpannableString spannable = new SpannableString(full);
        spannable.setSpan(
                new StyleSpan(android.graphics.Typeface.BOLD),
                0, data.getUsername().length(),
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        holder.tvCap.setText(spannable);
        holder.tvLikes.setText(data.getLikeCount() + " suka");
        holder.tvTime.setText(data.getTimeAgo().toUpperCase());

        View.OnClickListener toProfile = v -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("username", data.getUsername());
            intent.putExtra("profileImageResId", data.getProfileImageResId());
            context.startActivity(intent);
        };
        holder.imgProf.setOnClickListener(toProfile);
        holder.tvUser.setOnClickListener(toProfile);
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProf, imgPost;
        TextView tvUser, tvCap, tvLikes, tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProf = itemView.findViewById(R.id.imgProfile);
            imgPost = itemView.findViewById(R.id.imgPost);
            tvUser  = itemView.findViewById(R.id.tvUsername);
            tvCap   = itemView.findViewById(R.id.tvCaption);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvTime  = itemView.findViewById(R.id.tvTime);
        }
    }
}