package com.example.tuprak5;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private ArrayList<Book> books;
    private boolean isGrid;

    public BookAdapter(ArrayList<Book> books) {
        this.books = books;
        this.isGrid = false;
    }

    public BookAdapter(ArrayList<Book> books, boolean isGrid) {
        this.books = books;
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = isGrid ? R.layout.item_book_grid : R.layout.item_book;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvTitle.setText(book.getTitle());
        
        if (holder.tvAuthor != null) {
            holder.tvAuthor.setText(book.getAuthor());
        }
        if (holder.tvYear != null) {
            holder.tvYear.setText(book.getYear());
        }

        if (book.getCoverImageUri() != null) {
            holder.ivCover.setImageURI(book.getCoverImageUri());
        } else {
            holder.ivCover.setImageResource(book.getCoverImageResId());
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("BOOK_ID", book.getId());
            v.getContext().startActivity(intent);
        });
    }

    public void setFilteredList(ArrayList<Book> filteredList) {
        this.books = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvAuthor, tvYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvYear = itemView.findViewById(R.id.tv_year);
        }
    }
}