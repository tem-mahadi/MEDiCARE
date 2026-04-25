package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.temmahadi.healthcare.RoomDB.CustomLabTest;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;

public class AddCustomLabTestActivity extends AppCompatActivity {

    EditText etName, etCost, etDetails;
    Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_lab_test);

        etName = findViewById(R.id.etCustomLabName);
        etCost = findViewById(R.id.etCustomLabCost);
        etDetails = findViewById(R.id.etCustomLabDetails);
        btnSave = findViewById(R.id.btnSaveCustomLab);
        btnBack = findViewById(R.id.addCustomLabBackBtn);

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String cost = etCost.getText().toString().trim();
            String details = etDetails.getText().toString().trim();

            if (name.isEmpty() || cost.isEmpty()) {
                Toast.makeText(this, "Please enter at least Name and Cost", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");

            CustomLabTest labTest = new CustomLabTest(username, name, cost, details);
            DatabaseHelper db = DatabaseHelper.getInstance(this);

            new Thread(() -> {
                db.customLabTestDao().insert(labTest);
                runOnUiThread(() -> {
                    Toast.makeText(AddCustomLabTestActivity.this, "Lab Test Added Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
