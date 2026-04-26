package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.temmahadi.healthcare.Data.DoctorDetailsData;
import com.temmahadi.healthcare.Data.LabTestData;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NotificationHelper.createNotificationChannel(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        
        // Subscription Check
        if (!sharedPreferences.getBoolean("isSubscribed", false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        String username = sharedPreferences.getString("username", "");

        // Set greeting
        TextView greetingText = findViewById(R.id.greetingText);
        greetingText.setText("Hello, " + username + "! 👋");

        // Initialize database and seed data
        database = DatabaseHelper.getInstance(this);
        DoctorDetailsData doctorDetailsData = new DoctorDetailsData(database);
        LabTestData labTestData = new LabTestData(database);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!sharedPreferences.getBoolean("isInserted_v4", false)) {
            doctorDetailsData.doctor1();
            doctorDetailsData.doctor2();
            doctorDetailsData.doctor3();
            doctorDetailsData.doctor4();
            doctorDetailsData.doctor5();
            doctorDetailsData.doctor6();
            labTestData.LBData();
            labTestData.package_details();
            editor.putBoolean("isInserted_v4", true);
            editor.apply();
        }

        resetLegacyDoctorRowsIfNeeded(sharedPreferences);

        // Profile icon
        ImageView profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        // Cart icon
        ImageView cartIcon = findViewById(R.id.cartIcon);
        cartIcon.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        });

        // Find Doctor
        CardView findDoc = findViewById(R.id.FindDoc);
        findDoc.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, FindDoctorActivity.class));
        });

        // Lab Tests
        CardView labTest = findViewById(R.id.LabTest);
        labTest.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, LabTestActivity.class));
        });

        // BMI Calculator
        CardView bmiCalc = findViewById(R.id.BMICalc);
        bmiCalc.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, BMICalculatorActivity.class));
        });

        // Health Tips
        CardView healthTips = findViewById(R.id.HealthTips);
        healthTips.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, HealthTipsActivity.class));
        });

        // My Appointments
        CardView myAppointments = findViewById(R.id.MyAppointments);
        myAppointments.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, MyAppointmentsActivity.class));
        });

        // Emergency
        CardView emergency = findViewById(R.id.Emergency);
        emergency.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, EmergencyActivity.class));
        });

        // Profile
        CardView profile = findViewById(R.id.Profile);
        profile.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        // Logout
        CardView exit = findViewById(R.id.Exit);
        exit.setOnClickListener(view -> {
            editor.clear();
            editor.apply();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void resetLegacyDoctorRowsIfNeeded(SharedPreferences sharedPreferences) {
        if (sharedPreferences.getBoolean("doctor_data_cleanup_v1", false)) {
            return;
        }

        new Thread(() -> {
            List<String> doctorCategories = Arrays.asList(
                    "Family Physician",
                    "Dietitian",
                    "Dentist",
                    "Surgeon",
                    "Cardiologist",
                    "Other"
            );

            database.mainDAO().deleteByCategories(doctorCategories);

            DoctorDetailsData doctorDetailsData = new DoctorDetailsData(database);
            doctorDetailsData.doctor1();
            doctorDetailsData.doctor2();
            doctorDetailsData.doctor3();
            doctorDetailsData.doctor4();
            doctorDetailsData.doctor5();
            doctorDetailsData.doctor6();

            sharedPreferences.edit()
                    .putBoolean("doctor_data_cleanup_v1", true)
                    .putBoolean("isInserted_v4", true)
                    .apply();
        }).start();
    }
}