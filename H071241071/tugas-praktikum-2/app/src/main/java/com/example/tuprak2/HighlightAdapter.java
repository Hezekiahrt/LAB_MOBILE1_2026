package com.example.tuprak2;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {
    private List<Integer> list;

    public HighlightAdapter(List<Integer> list) {
        this.list = list;
    }

    @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int v) {
        ShapeableImageView iv = new ShapeableImageView(p.getContext());

        int size = (int) (70 * p.getContext().getResources().getDisplayMetrics().density);
        iv.setLayoutParams(new ViewGroup.LayoutParams(size, size));
        iv.setPadding(10, 10, 10, 10);
        iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);

        iv.setShapeAppearanceModel(
                ShapeAppearanceModel.builder()
                        .setAllCornerSizes(ShapeAppearanceModel.PILL)
                        .build()
        );

        iv.setStrokeWidth(3f);
        iv.setStrokeColor(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#333333")));

        return new ViewHolder(iv);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder h, int p) {
        ((ShapeableImageView) h.itemView).setImageResource(list.get(p));
    }

    @Override public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(android.view.View v) { super(v); }
    }
}