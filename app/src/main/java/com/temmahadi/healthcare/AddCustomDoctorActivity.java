package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.temmahadi.healthcare.RoomDB.CustomDoctor;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;

public class AddCustomDoctorActivity extends AppCompatActivity {

    EditText etName, etAddress, etContact, etExp, etFee;
    Button btnSave, btnBack;
    TextView tvTitle;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_doctor);

        category = getIntent().getStringExtra("title"); // The doctor category (e.g., Physician)

        etName = findViewById(R.id.etCustomDocName);
        etAddress = findViewById(R.id.etCustomDocAddress);
        etContact = findViewById(R.id.etCustomDocContact);
        etExp = findViewById(R.id.etCustomDocExp);
        etFee = findViewById(R.id.etCustomDocFee);
        btnSave = findViewById(R.id.btnSaveCustomDoc);
        btnBack = findViewById(R.id.addCustomDocBackBtn);
        tvTitle = findViewById(R.id.addCustomDocTitle);

        if (category != null) {
            tvTitle.setText("Add " + category);
        }

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String contact = etContact.getText().toString().trim();
            String exp = etExp.getText().toString().trim();
            String fee = etFee.getText().toString().trim();

            if (name.isEmpty() || fee.isEmpty()) {
                Toast.makeText(this, "Please enter at least Name and Fee", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");

            CustomDoctor doctor = new CustomDoctor(username, category, name, address, contact, exp, fee);
            DatabaseHelper db = DatabaseHelper.getInstance(this);

            new Thread(() -> {
                db.customDoctorDao().insert(doctor);
                runOnUiThread(() -> {
                    Toast.makeText(AddCustomDoctorActivity.this, "Doctor Added Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
