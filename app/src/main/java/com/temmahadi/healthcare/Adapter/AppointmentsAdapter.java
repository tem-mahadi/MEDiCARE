package com.temmahadi.healthcare.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temmahadi.healthcare.R;
import com.temmahadi.healthcare.RoomDB.Appointment;

import java.util.List;

import android.widget.ImageView;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {

    private final List<Appointment> appointments;
    private final OnAppointmentClickListener listener;

    public interface OnAppointmentClickListener {
        void onEditClick(Appointment appointment);
        void onDeleteClick(Appointment appointment);
    }

    public AppointmentsAdapter(List<Appointment> appointments, OnAppointmentClickListener listener) {
        this.appointments = appointments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appt = appointments.get(position);
        holder.doctor.setText(appt.doctorName);
        holder.hospital.setText(appt.hospital);
        holder.date.setText("📅 " + appt.date);
        holder.time.setText("🕐 " + appt.time);
        holder.fee.setText("৳" + appt.fee);

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(appt);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(appt);
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView doctor, hospital, date, time, fee;
        ImageView btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor = itemView.findViewById(R.id.appointmentDoctor);
            hospital = itemView.findViewById(R.id.appointmentHospital);
            date = itemView.findViewById(R.id.appointmentDate);
            time = itemView.findViewById(R.id.appointmentTime);
            fee = itemView.findViewById(R.id.appointmentFee);
            btnEdit = itemView.findViewById(R.id.btnEditAppointment);
            btnDelete = itemView.findViewById(R.id.btnDeleteAppointment);
        }
    }
}
