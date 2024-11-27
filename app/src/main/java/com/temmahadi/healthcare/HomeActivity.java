package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.temmahadi.healthcare.Data.DoctorDetailsData;
import com.temmahadi.healthcare.Data.LabTestData;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;

public class HomeActivity extends AppCompatActivity {

    DatabaseHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences= getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        Toast.makeText(getApplicationContext(),"Welcome "+username,Toast.LENGTH_SHORT).show();

        database = DatabaseHelper.getInstance(this);
//        database.mainDAO().clearAll();
        DoctorDetailsData doctorDetailsData = new DoctorDetailsData(database);
        LabTestData labTestData = new LabTestData(database);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        boolean data_inserted = sharedPreferences.getBoolean("isInserted",true);
        if(!data_inserted) {
            editor.putBoolean("isInserted",false);
            editor.apply();
            doctorDetailsData.doctor1();
            doctorDetailsData.doctor2();
            doctorDetailsData.doctor3();
            doctorDetailsData.doctor4();
            doctorDetailsData.doctor5();
            labTestData.LBData();
            labTestData.package_details();
        }
        CardView exit= findViewById(R.id.Exit);
        exit.setOnClickListener(view -> {
            editor.clear();
            editor.apply();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        });

        CardView findDoc= findViewById(R.id.FindDoc);
        findDoc.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, FindDoctorActivity.class));
        });
        CardView LabTest= findViewById(R.id.LabTest);
        LabTest.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, LabTestActivity.class));
        });
    }
}