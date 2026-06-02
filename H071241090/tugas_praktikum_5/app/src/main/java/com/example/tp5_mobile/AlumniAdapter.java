package com.example.tp5_mobile;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.List;

public class AlumniAdapter extends RecyclerView.Adapter<AlumniAdapter.ViewHolder> {
    private List<Alumni> alumniList;
    private OnAlumniDeletedListener deleteListener;

    public interface OnAlumniDeletedListener {
        void onAlumniDeleted();
    }

    public AlumniAdapter(List<Alumni> alumniList, OnAlumniDeletedListener deleteListener) {
        this.alumniList = alumniList;
        this.deleteListener = deleteListener;
    }

    public void updateList(List<Alumni> newList) {
        this.alumniList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumni, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alumni alumni = alumniList.get(position);
        holder.tvNama.setText(alumni.nama);
        holder.tvKampusAngkatan.setText(alumni.kampus + " • " + alumni.angkatan);
        holder.tvInstansi.setText(alumni.instansi);
        holder.tvBadgeStatus.setText(alumni.status);
        holder.tvBidang.setText(alumni.bidang);
        
        // Set Photo (Handle URI or Resource)
        if (alumni.fotoUri != null && !alumni.fotoUri.isEmpty()) {
            holder.ivFoto.setImageURI(Uri.parse(alumni.fotoUri));
        } else if (alumni.fotoRes != 0) {
            holder.ivFoto.setImageResource(alumni.fotoRes);
        } else {
            holder.ivFoto.setImageResource(android.R.drawable.ic_menu_gallery);
        }
        
        // Badge Colors
        if (alumni.status.contains("Bekerja")) {
            holder.tvBadgeStatus.setBackgroundResource(R.drawable.bg_badge_bekerja);
            holder.tvBadgeStatus.setTextColor(holder.itemView.getContext().getColor(R.color.status_bekerja_text));
        } else if (alumni.status.contains("Wirausaha")) {
            holder.tvBadgeStatus.setBackgroundResource(R.drawable.bg_badge_wirausaha);
            holder.tvBadgeStatus.setTextColor(holder.itemView.getContext().getColor(R.color.status_wirausaha_text));
        } else if (alumni.status.contains("Studi")) {
            holder.tvBadgeStatus.setBackgroundResource(R.drawable.bg_badge_studi);
            holder.tvBadgeStatus.setTextColor(holder.itemView.getContext().getColor(R.color.status_studi_text));
        } else {
            holder.tvBadgeStatus.setBackgroundResource(R.drawable.bg_badge_belum);
            holder.tvBadgeStatus.setTextColor(holder.itemView.getContext().getColor(R.color.status_belum_text));
        }

        holder.btnCall.setOnClickListener(v -> {
            if (alumni.phone != null && !alumni.phone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + alumni.phone));
                v.getContext().startActivity(intent);
            }
        });

        holder.btnEmail.setOnClickListener(v -> {
            if (alumni.email != null && !alumni.email.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + alumni.email));
                v.getContext().startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            DataRepository.getInstance().removeAlumni(alumni);
            if (deleteListener != null) {
                deleteListener.onAlumniDeleted();
            }
        });
    }

    @Override
    public int getItemCount() {
        return alumniList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvKampusAngkatan, tvInstansi, tvBadgeStatus, tvBidang;
        ShapeableImageView ivFoto;
        MaterialButton btnCall, btnEmail;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvKampusAngkatan = itemView.findViewById(R.id.tv_kampus_angkatan);
            tvInstansi = itemView.findViewById(R.id.tv_instansi);
            tvBadgeStatus = itemView.findViewById(R.id.tv_badge_status);
            tvBidang = itemView.findViewById(R.id.tv_bidang);
            ivFoto = itemView.findViewById(R.id.iv_foto_profil);
            btnCall = itemView.findViewById(R.id.btn_call);
            btnEmail = itemView.findViewById(R.id.btn_email);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}