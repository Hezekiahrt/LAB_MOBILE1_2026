package com.example.libraryapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.DetailActivity;
import com.example.libraryapp.R;
import com.example.libraryapp.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context context;
    private List<Book> books;

    public BookAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    public void updateList(List<Book> newList) {
        this.books = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvYear.setText(book.getYear());
        holder.tvGenre.setText(book.getGenre());

        if (book.getCoverUri() != null) {
            holder.imgCover.setImageURI(book.getCoverUri());
        } else if (book.getDrawableResId() != 0) {
            holder.imgCover.setImageResource(book.getDrawableResId());
        } else {
            holder.imgCover.setImageResource(R.drawable.ic_book_placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("book_id", book.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return books.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView tvTitle, tvAuthor, tvYear, tvGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imgCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvGenre = itemView.findViewById(R.id.tvGenre);
        }
    }
}