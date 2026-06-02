package com.example.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    public interface OnImageSelectedListener {
        void onSelected(int imageResId);
    }

    private Context context;
    private ArrayList<Integer> imageList;
    private OnImageSelectedListener listener;
    private int selectedPosition = 0;

    public GalleryAdapter(Context context, ArrayList<Integer> imageList, OnImageSelectedListener listener) {
        this.context   = context;
        this.imageList = imageList;
        this.listener  = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resId = imageList.get(position);
        holder.imgGallery.setImageResource(resId);

        holder.overlay.setVisibility(
                position == selectedPosition ? View.VISIBLE : View.GONE
        );

        holder.itemView.setOnClickListener(v -> {
            int prev = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(prev);
            notifyItemChanged(selectedPosition);
            listener.onSelected(resId);
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGallery;
        View overlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGallery = itemView.findViewById(R.id.imgGallery);
            overlay    = itemView.findViewById(R.id.viewOverlay);
        }
    }
}