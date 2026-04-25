package com.temmahadi.healthcare.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temmahadi.healthcare.R;
import com.temmahadi.healthcare.RoomDB.BookedLabTest;

import java.util.List;

public class BookedLabTestsAdapter extends RecyclerView.Adapter<BookedLabTestsAdapter.ViewHolder> {

    private final List<BookedLabTest> tests;
    private final OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDelete(BookedLabTest test);
    }

    public BookedLabTestsAdapter(List<BookedLabTest> tests, OnDeleteClickListener deleteClickListener) {
        this.tests = tests;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booked_lab_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookedLabTest test = tests.get(position);
        holder.packageName.setText(test.packageName);
        holder.cost.setText("\u09f3" + test.cost);
        holder.date.setText("\ud83d\udcc5 " + test.date);
        holder.time.setText("\ud83d\udd50 " + test.time);
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDelete(test);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView packageName;
        TextView cost;
        TextView date;
        TextView time;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            packageName = itemView.findViewById(R.id.bookedLabPackageName);
            cost = itemView.findViewById(R.id.bookedLabCost);
            date = itemView.findViewById(R.id.bookedLabDate);
            time = itemView.findViewById(R.id.bookedLabTime);
            btnDelete = itemView.findViewById(R.id.btnDeleteBookedLab);
        }
    }
}
