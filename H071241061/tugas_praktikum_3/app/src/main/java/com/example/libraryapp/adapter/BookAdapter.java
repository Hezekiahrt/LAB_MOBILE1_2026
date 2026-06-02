package com.example.libraryapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.libraryapp.R;
import com.example.libraryapp.model.Book;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final Context context;
    private ArrayList<Book> bookList;
    private final OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book, int position);
        void onLikeClick(Book book, int position);
    }

    public BookAdapter(Context context, ArrayList<Book> bookList, OnBookClickListener listener) {
        this.context = context;
        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvYear.setText(String.valueOf(book.getYear()));
        holder.tvGenre.setText(book.getGenre());
        holder.tvBlurb.setText(book.getBlurb());
        holder.ratingBar.setRating(book.getRating());
        holder.tvRating.setText(String.format("%.1f", book.getRating()));

        if (book.getCoverUriString() != null) {
            Glide.with(context)
                    .load(Uri.parse(book.getCoverUriString()))
                    .placeholder(R.drawable.cover_placeholder)
                    .centerCrop()
                    .into(holder.ivCover);
        }
        else if (book.getCoverResId() != 0) {
            holder.ivCover.setImageResource(book.getCoverResId());
        }
        else {
            holder.ivCover.setImageResource(R.drawable.cover_placeholder);
        }

        holder.ivLike.setImageResource(
                book.isLiked() ? R.drawable.favorite_filled : R.drawable.favorite_border
        );

        holder.cardView.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onBookClick(book, pos);
            }
        });

        holder.ivLike.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                book.setLiked(!book.isLiked());
                holder.ivLike.setImageResource(
                        book.isLiked() ? R.drawable.favorite_filled : R.drawable.favorite_border
                );
                listener.onLikeClick(book, pos);
            }
        });
    }

    @Override
    public int getItemCount() { return bookList.size(); }

    public void updateList(ArrayList<Book> newList) {
        this.bookList = newList;
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivCover, ivLike;
        TextView tvTitle, tvAuthor, tvYear, tvGenre, tvBlurb, tvRating;
        RatingBar ratingBar;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView  = itemView.findViewById(R.id.cardView);
            ivCover   = itemView.findViewById(R.id.ivCover);
            ivLike    = itemView.findViewById(R.id.ivLike);
            tvTitle   = itemView.findViewById(R.id.tvTitle);
            tvAuthor  = itemView.findViewById(R.id.tvAuthor);
            tvYear    = itemView.findViewById(R.id.tvYear);
            tvGenre   = itemView.findViewById(R.id.tvGenre);
            tvBlurb   = itemView.findViewById(R.id.tvBlurb);
            tvRating  = itemView.findViewById(R.id.tvRating);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
