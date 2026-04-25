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

import com.temmahadi.healthcare.BookAppointmentActivity;
import com.temmahadi.healthcare.R;
import com.temmahadi.healthcare.RoomDB.Items;

import java.util.List;

public class DoctorDetailsAdapter extends RecyclerView.Adapter<DoctorDetailsAdapter.myViewholder> {
    Context context; List<Items> list;

    public DoctorDetailsAdapter(Context context, List<Items> list) {
        this.context = context;
        this.list = list;
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
        DoctorDisplayData displayData = buildDisplayData(currentItem);

        holder.line_1.setText(displayData.name);
        holder.line_2.setText(displayData.address);
        holder.line_3.setText(displayData.experience);
        holder.line_4.setText(displayData.mobile);
        holder.line_5.setText(displayData.fee);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(context, BookAppointmentActivity.class);
                it.putExtra("text1", displayData.name);
                it.putExtra("text2", displayData.address);
                it.putExtra("text3", displayData.mobile);
                it.putExtra("text4", displayData.fee);
                context.startActivity(it);
            }
        });

    }

    private DoctorDisplayData buildDisplayData(Items item) {
        String[] details = item != null ? item.getDetails() : null;
        if (details == null || details.length == 0) {
            return new DoctorDisplayData("", "", "", "", "");
        }

        String name = getSafeDetail(details, 0);
        int expIndex = indexOfKeyword(details, "exp");
        int mobileIndex = indexOfAnyKeyword(details, new String[]{"mobile", "contact", "phone"});
        int feeIndex = findLikelyFeeIndex(details);

        int stopIndex = firstNonNegative(expIndex, mobileIndex, feeIndex);
        String address;
        if (stopIndex > 1) {
            address = joinRange(details, 1, stopIndex - 1);
        } else {
            address = getSafeDetail(details, 1);
        }

        String experience = expIndex >= 0 ? details[expIndex] : getSafeDetail(details, 2);
        String mobile = mobileIndex >= 0 ? details[mobileIndex] : getSafeDetail(details, 3);
        String fee = feeIndex >= 0 ? details[feeIndex] : getSafeDetail(details, details.length - 1);

        return new DoctorDisplayData(name, address, experience, mobile, fee);
    }

    private int indexOfKeyword(String[] details, String keyword) {
        if (details == null || keyword == null) {
            return -1;
        }
        for (int i = 0; i < details.length; i++) {
            String value = details[i];
            if (value != null && value.toLowerCase().contains(keyword.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    private int indexOfAnyKeyword(String[] details, String[] keywords) {
        if (details == null || keywords == null) {
            return -1;
        }
        for (int i = 0; i < details.length; i++) {
            String value = details[i];
            if (value == null) {
                continue;
            }
            String lower = value.toLowerCase();
            for (String keyword : keywords) {
                if (keyword != null && lower.contains(keyword.toLowerCase())) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int findLikelyFeeIndex(String[] details) {
        if (details == null || details.length == 0) {
            return -1;
        }

        for (int i = details.length - 1; i >= 0; i--) {
            String value = details[i];
            if (value == null) {
                continue;
            }

            String lower = value.toLowerCase();
            if (lower.contains("mobile") || lower.contains("contact") || lower.contains("phone") || lower.contains("exp")) {
                continue;
            }

            String digitsOnly = value.replaceAll("[^0-9]", "");
            if (!digitsOnly.isEmpty() && digitsOnly.length() <= 6) {
                return i;
            }
        }

        return details.length - 1;
    }

    private int firstNonNegative(int... values) {
        int result = Integer.MAX_VALUE;
        for (int value : values) {
            if (value >= 0 && value < result) {
                result = value;
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    private String joinRange(String[] details, int start, int end) {
        if (details == null || start < 0 || end < start || start >= details.length) {
            return "";
        }

        int safeEnd = Math.min(end, details.length - 1);
        StringBuilder builder = new StringBuilder();
        for (int i = start; i <= safeEnd; i++) {
            String part = details[i];
            if (part == null || part.trim().isEmpty()) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(part.trim());
        }
        return builder.toString();
    }

    private String getSafeDetail(String[] details, int index) {
        if (details == null || index < 0 || index >= details.length || details[index] == null) {
            return "";
        }
        return details[index].trim();
    }

    private static class DoctorDisplayData {
        final String name;
        final String address;
        final String experience;
        final String mobile;
        final String fee;

        DoctorDisplayData(String name, String address, String experience, String mobile, String fee) {
            this.name = name;
            this.address = address;
            this.experience = experience;
            this.mobile = mobile;
            this.fee = fee;
        }
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

