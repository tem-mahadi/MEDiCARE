package com.temmahadi.healthcare.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temmahadi.healthcare.LabTestDetailsActivity;
import com.temmahadi.healthcare.R;
import com.temmahadi.healthcare.RoomDB.Items;

import java.util.List;

public class LabTestAdapter extends RecyclerView.Adapter<LabTestAdapter.myViewholder> {
    Context context; List<Items> list; List<Items> packageList;
    public LabTestAdapter(Context context, List<Items> list, List<Items> packageList) {
        this.context = context;
        this.list = list;
        this.packageList = packageList;
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewholder(LayoutInflater.from(context).inflate(R.layout.multi_lines,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull myViewholder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("ADAPT", "Retrieved items: ");
        Items currentItem = list.get(position);
        holder.line_1.setText(getSafeDetail(currentItem, 0));
        holder.line_2.setText(getSafeDetail(currentItem, 1));
        holder.line_3.setText(getSafeDetail(currentItem, 2));
        holder.line_4.setText(getSafeDetail(currentItem, 3));
        holder.line_5.setText("Total Cost: " + getSafeDetail(currentItem, 4) + "/-");

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(context, LabTestDetailsActivity.class);
                it.putExtra("text1", getSafeDetail(currentItem, 0));
                it.putExtra("text2", resolvePackageDetails(position));
                it.putExtra("text3", getSafeDetail(currentItem, 4));
                context.startActivity(it);
            }
        });

    }

    private String getSafeDetail(Items item, int index) {
        if (item == null || item.getDetails() == null) {
            return "";
        }
        String[] details = item.getDetails();
        if (index < 0 || index >= details.length || details[index] == null) {
            return "";
        }
        return details[index];
    }

    private String resolvePackageDetails(int position) {
        if (packageList == null || packageList.isEmpty()) {
            return "Details unavailable";
        }

        // Preferred format: one package-detail row per test row.
        if (position >= 0 && position < packageList.size()) {
            String perItemDetails = getSafeDetail(packageList.get(position), 0);
            if (!perItemDetails.isEmpty()) {
                return perItemDetails;
            }
        }

        // Legacy format: one row with details spread across indexes [0..n].
        String legacyDetails = getSafeDetail(packageList.get(0), position);
        if (!legacyDetails.isEmpty()) {
            return legacyDetails;
        }

        String fallback = getSafeDetail(packageList.get(0), 0);
        return fallback.isEmpty() ? "Details unavailable" : fallback;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class myViewholder extends RecyclerView.ViewHolder{
        TextView line_1,line_2,line_3,line_4,line_5;
        CardView linearLayout;
        public myViewholder(@NonNull View itemView) {
            super(itemView);
            line_1 = itemView.findViewById(R.id.line_a);
            line_2 = itemView.findViewById(R.id.line_b);
            line_3 = itemView.findViewById(R.id.line_c);
            line_4 = itemView.findViewById(R.id.line_d);
            line_5 = itemView.findViewById(R.id.line_e);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}

