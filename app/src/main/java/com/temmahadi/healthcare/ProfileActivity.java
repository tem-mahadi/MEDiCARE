package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.temmahadi.healthcare.RoomDB.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        String lastBMI = sharedPreferences.getString("lastBMI", "--");
        String lastBMICategory = sharedPreferences.getString("lastBMICategory", "");

        // Set profile data
        TextView profileInitial = findViewById(R.id.profileInitial);
        TextView profileUsername = findViewById(R.id.profileUsername);
        TextView profileBMI = findViewById(R.id.profileBMI);
        TextView profileBMICategory = findViewById(R.id.profileBMICategory);
        TextView profileAppointments = findViewById(R.id.profileAppointments);

        profileInitial.setText(username.isEmpty() ? "U" : String.valueOf(username.charAt(0)).toUpperCase());
        profileUsername.setText(username);
        profileBMI.setText(lastBMI);
        profileBMICategory.setText(lastBMICategory);

        // Get appointment count
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        int appointmentCount = db.appointmentDao().getCount(username);
        profileAppointments.setText(String.valueOf(appointmentCount));

        // Logout
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });

        // Back to Home
        Button btnBackHome = findViewById(R.id.btnBackHome);
        btnBackHome.setOnClickListener(view -> {
            finish();
        });
    }
}
