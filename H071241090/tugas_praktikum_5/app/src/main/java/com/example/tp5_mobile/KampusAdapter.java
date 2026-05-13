package com.example.tp5_mobile;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class KampusAdapter extends RecyclerView.Adapter<KampusAdapter.ViewHolder> {
    private List<Kampus> kampusList;
    private OnKampusClickListener listener;

    public interface OnKampusClickListener {
        void onKampusClick(Kampus kampus);
    }

    public KampusAdapter(List<Kampus> kampusList, OnKampusClickListener listener) {
        this.kampusList = kampusList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kampus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kampus kampus = kampusList.get(position);
        holder.tvNama.setText(kampus.nama);
        holder.tvJumlah.setText(kampus.jumlahAlumni + " alumni");
        
        // Cek apakah menggunakan Logo dari Galeri (URI) atau Drawable (Resource)
        if (kampus.logoUri != null && !kampus.logoUri.isEmpty()) {
            holder.ivLogo.setImageURI(Uri.parse(kampus.logoUri));
        } else if (kampus.logoRes != 0) {
            holder.ivLogo.setImageResource(kampus.logoRes);
        } else {
            holder.ivLogo.setImageResource(android.R.drawable.ic_menu_gallery);
        }
        
        if (kampus.isSelected) {
            holder.cardView.setStrokeWidth(4);
            holder.cardView.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.status_studi_bg));
        } else {
            holder.cardView.setStrokeWidth(0);
            holder.cardView.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(v -> {
            for (Kampus k : kampusList) k.isSelected = false;
            kampus.isSelected = true;
            notifyDataSetChanged();
            listener.onKampusClick(kampus);
        });
    }

    @Override
    public int getItemCount() {
        return kampusList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvJumlah;
        ImageView ivLogo;
        MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama_kampus);
            tvJumlah = itemView.findViewById(R.id.tv_jumlah_alumni);
            ivLogo = itemView.findViewById(R.id.iv_logo_kampus);
            cardView = (MaterialCardView) itemView;
        }
    }
}