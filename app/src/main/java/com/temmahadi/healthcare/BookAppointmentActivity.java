package com.temmahadi.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.temmahadi.healthcare.RoomDB.Appointment;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;

import java.util.Calendar;
import java.util.Locale;

public class BookAppointmentActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Button datebtn, timebtn, btnbook, btnback;
    String selectedDate = "";
    String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        ed1 = findViewById(R.id.editTextAppName);
        ed2 = findViewById(R.id.editTextAppAddress);
        ed3 = findViewById(R.id.editTextAppCont);
        ed4 = findViewById(R.id.editTextAppFee);
        datebtn = findViewById(R.id.btnAppDate);
        timebtn = findViewById(R.id.btnAppTime);
        btnback = findViewById(R.id.buttonAppBack);
        btnbook = findViewById(R.id.buttonAppointment);

        // Remove setKeyListener(null) so users can manually type their own appointment details
        // ed1.setKeyListener(null);
        // ed2.setKeyListener(null);
        // ed3.setKeyListener(null);
        // ed4.setKeyListener(null);

        Intent it = getIntent();
        int appointmentId = it.getIntExtra("appointmentId", -1);
        String fullname = it.getStringExtra("text1");
        String address = it.getStringExtra("text2");
        String contact = it.getStringExtra("text3");
        String fees = it.getStringExtra("text4");
        String prevDate = it.getStringExtra("date");
        String prevTime = it.getStringExtra("time");

        if (fullname != null) ed1.setText(fullname);
        if (address != null) ed2.setText(address);
        if (contact != null) ed3.setText(contact);
        if (fees != null) ed4.setText(fees);
        
        if (prevDate != null) {
            selectedDate = prevDate;
            datebtn.setText("📅  " + selectedDate);
        }
        if (prevTime != null) {
            selectedTime = prevTime;
            timebtn.setText("🕐  " + selectedTime);
        }

        if (appointmentId != -1) {
            btnbook.setText("UPDATE APPOINTMENT");
        }

        initDatePicker();
        datebtn.setOnClickListener(view -> datePickerDialog.show());

        initTimePicker();
        timebtn.setOnClickListener(view -> timePickerDialog.show());

        btnback.setOnClickListener(view -> finish());

        btnbook.setOnClickListener(view -> {
            if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please select both date and time", Toast.LENGTH_SHORT).show();
                return;
            }

            String finalName = ed1.getText().toString();
            String finalAddress = ed2.getText().toString();
            String finalContact = ed3.getText().toString();
            String finalFees = ed4.getText().toString();

            if (finalName.isEmpty() || finalFees.isEmpty()) {
                Toast.makeText(this, "Please enter at least Doctor Name and Fee", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");

            DatabaseHelper db = DatabaseHelper.getInstance(this);
            Appointment appointment = new Appointment(
                    finalName,
                    finalAddress,
                    finalContact,
                    finalFees,
                    selectedDate,
                    selectedTime,
                    username
            );

            new Thread(() -> {
                if (appointmentId != -1) {
                    appointment.id = appointmentId;
                    db.appointmentDao().update(appointment);
                } else {
                    db.appointmentDao().insert(appointment);
                }

                // Schedule reminder 1 hour before
                int notificationId = (appointment.doctorName + appointment.date + appointment.time).hashCode();
                NotificationHelper.scheduleReminder(
                        BookAppointmentActivity.this,
                        notificationId,
                        "Appointment Reminder",
                        "You have an appointment with " + appointment.doctorName + " at " + appointment.time,
                        appointment.date,
                        appointment.time
                );

                runOnUiThread(() -> {
                    String title = appointmentId != -1 ? "✅ Appointment Updated!" : "✅ Appointment Booked!";
                    new AlertDialog.Builder(BookAppointmentActivity.this)
                            .setTitle(title)
                            .setMessage("Your appointment with " + finalName + " has been saved.\n\n" +
                                    "📅 Date: " + selectedDate + "\n" +
                                    "🕐 Time: " + selectedTime)
                            .setPositiveButton("OK", (dialog, which) -> finish())
                            .setCancelable(false)
                            .show();
                });
            }).start();
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            selectedDate = day + "/" + month + "/" + year;
            datebtn.setText("📅  " + selectedDate);
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> {
            int displayHour = hour % 12;
            if (displayHour == 0) {
                displayHour = 12;
            }
            String amPm = hour < 12 ? "AM" : "PM";
            selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", displayHour, minute, amPm);
            timebtn.setText("🕐  " + selectedTime);
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this, timeSetListener, hrs, mins, false);
    }
}