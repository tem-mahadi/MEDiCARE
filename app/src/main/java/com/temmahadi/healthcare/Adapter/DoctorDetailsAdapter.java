package com.temmahadi.healthcare.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        holder.line_1.setText(list.get(position).getDetails()[0]);
        holder.line_2.setText(list.get(position).getDetails()[1]);
        holder.line_3.setText(list.get(position).getDetails()[2]);
        holder.line_4.setText(list.get(position).getDetails()[3]);
        holder.line_5.setText(list.get(position).getDetails()[4]);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(context, BookAppointmentActivity.class);
                it.putExtra("text1",list.get(position).getDetails()[0]);
                it.putExtra("text2",list.get(position).getDetails()[1]);
                it.putExtra("text3",list.get(position).getDetails()[3]);
                it.putExtra("text4",list.get(position).getDetails()[4]);
                context.startActivity(it);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class myViewholder extends RecyclerView.ViewHolder{
        TextView line_1,line_2,line_3,line_4,line_5;
        LinearLayout linearLayout;
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

