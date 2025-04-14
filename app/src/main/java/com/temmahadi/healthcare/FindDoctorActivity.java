package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        CardView exit= findViewById(R.id.Back);
        exit.setOnClickListener(view -> {
            startActivity(new Intent(FindDoctorActivity.this, HomeActivity.class));
            finish();
        });

        CardView familyPhysician= findViewById(R.id.FDFamilyPhysician);
        familyPhysician.setOnClickListener(view -> {
            Intent it= new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            it.putExtra("title","Family Physician");
            startActivity(it);
            finish();
        });

        CardView dietitian= findViewById(R.id.FDDietitian);
        dietitian.setOnClickListener(view -> {
            Intent it= new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            it.putExtra("title","Dietitian");
            startActivity(it);
            finish();
        });

        CardView dentist= findViewById(R.id.FDDentist);
        dentist.setOnClickListener(view -> {
            Intent it= new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            it.putExtra("title","Dentist");
            startActivity(it);
            finish();
        });

        CardView surgeon= findViewById(R.id.FDSurgeon);
        surgeon.setOnClickListener(view -> {
            Intent it= new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            it.putExtra("title","Surgeon");
            startActivity(it);
            finish();
        });

        CardView cardiologist= findViewById(R.id.FDCardiologist);
        cardiologist.setOnClickListener(view -> {
            Intent it= new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            it.putExtra("title","Cardiologist");
            startActivity(it);
            finish();
        });

    }
}