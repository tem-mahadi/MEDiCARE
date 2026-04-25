package com.temmahadi.healthcare.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temmahadi.healthcare.R;

import java.util.List;

public class HealthTipsAdapter extends RecyclerView.Adapter<HealthTipsAdapter.TipViewHolder> {

    private final List<String[]> tips; // [emoji, title, description]

    public HealthTipsAdapter(List<String[]> tips) {
        this.tips = tips;
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_health_tip, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        String[] tip = tips.get(position);
        holder.emoji.setText(tip[0]);
        holder.title.setText(tip[1]);
        holder.description.setText(tip[2]);
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    public static class TipViewHolder extends RecyclerView.ViewHolder {
        TextView emoji, title, description;

        public TipViewHolder(@NonNull View itemView) {
            super(itemView);
            emoji = itemView.findViewById(R.id.tipEmoji);
            title = itemView.findViewById(R.id.tipTitle);
            description = itemView.findViewById(R.id.tipDescription);
        }
    }
}
