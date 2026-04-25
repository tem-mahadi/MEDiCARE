package com.temmahadi.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.temmahadi.healthcare.Adapter.AppointmentsAdapter;
import com.temmahadi.healthcare.RoomDB.Appointment;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;

import java.util.List;

public class MyAppointmentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout emptyState;
    DatabaseHelper db;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        Button backBtn = findViewById(R.id.apptBackBtn);
        backBtn.setOnClickListener(view -> finish());

        Button labTestsBtn = findViewById(R.id.btnUpcomingLabTests);
        labTestsBtn.setOnClickListener(view -> {
            startActivity(new Intent(MyAppointmentsActivity.this, UpcomingLabTestsActivity.class));
        });

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        db = DatabaseHelper.getInstance(this);

        recyclerView = findViewById(R.id.recyclerAppointments);
        emptyState = findViewById(R.id.emptyState);

        FloatingActionButton fab = findViewById(R.id.fabAddAppointment);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MyAppointmentsActivity.this, BookAppointmentActivity.class);
            startActivity(intent);
        });

        loadAppointments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAppointments();
    }

    private void loadAppointments() {
        new Thread(() -> {
            List<Appointment> appointments = db.appointmentDao().getByUser(username);
            runOnUiThread(() -> {
                if (appointments.isEmpty()) {
                    emptyState.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyState.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyAppointmentsActivity.this));
                    AppointmentsAdapter adapter = new AppointmentsAdapter(appointments, new AppointmentsAdapter.OnAppointmentClickListener() {
                        @Override
                        public void onEditClick(Appointment appointment) {
                            Intent it = new Intent(MyAppointmentsActivity.this, BookAppointmentActivity.class);
                            it.putExtra("appointmentId", appointment.id);
                            it.putExtra("text1", appointment.doctorName);
                            it.putExtra("text2", appointment.hospital);
                            it.putExtra("text3", appointment.contact);
                            it.putExtra("text4", appointment.fee);
                            it.putExtra("date", appointment.date);
                            it.putExtra("time", appointment.time);
                            startActivity(it);
                        }

                        @Override
                        public void onDeleteClick(Appointment appointment) {
                            new AlertDialog.Builder(MyAppointmentsActivity.this)
                                    .setTitle("Delete Appointment")
                                    .setMessage("Are you sure you want to delete this appointment?")
                                    .setPositiveButton("Delete", (dialog, which) -> {
                                        new Thread(() -> {
                                            db.appointmentDao().deleteById(appointment.id);
                                            runOnUiThread(() -> {
                                                Toast.makeText(MyAppointmentsActivity.this, "Appointment Deleted", Toast.LENGTH_SHORT).show();
                                                loadAppointments();
                                            });
                                        }).start();
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            });
        }).start();
    }
}
