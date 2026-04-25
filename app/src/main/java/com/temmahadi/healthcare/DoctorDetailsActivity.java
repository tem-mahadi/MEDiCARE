package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.temmahadi.healthcare.Adapter.DoctorDetailsAdapter;
import com.temmahadi.healthcare.RoomDB.CustomDoctor;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;
import com.temmahadi.healthcare.RoomDB.Items;

import java.util.ArrayList;
import java.util.List;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView tv;
    Button btn;
    FloatingActionButton fabAdd;
    RecyclerView recyclerView;
    DoctorDetailsAdapter doctorDetailsAdapter;
    DatabaseHelper database;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        btn = findViewById(R.id.backbtn);
        tv = findViewById(R.id.FDTitleName);
        fabAdd = findViewById(R.id.fabAddCustomDoctor);
        recyclerView = findViewById(R.id.recycleView);

        Intent it = getIntent();
        title = it.getStringExtra("title");
        if (title == null || title.trim().isEmpty()) {
            Toast.makeText(this, "Doctor category not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        tv.setText(title);

        database = DatabaseHelper.getInstance(this);

        btn.setOnClickListener(view -> finish());

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorDetailsActivity.this, AddCustomDoctorActivity.class);
            intent.putExtra("title", title);
            startActivity(intent);
        });

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // reload in case a custom doctor was added
    }

    private void loadData() {
        new Thread(() -> {
            // Fetch hardcoded dummy doctors
            List<Items> itemsList = database.mainDAO().getAll(title);
            if (itemsList == null) {
                itemsList = new ArrayList<>();
            }

            // Fetch custom user doctors
            List<CustomDoctor> customDoctors = database.customDoctorDao().getByCategory(title);
            if (customDoctors == null) {
                customDoctors = new ArrayList<>();
            }
            
            // Convert custom doctors to Items so we can reuse the adapter
            for (CustomDoctor cd : customDoctors) {
                String[] details = {cd.name, cd.address, cd.experience, cd.contact, cd.fee};
                Items mappedItem = new Items(details, cd.category);
                // Put them at the top of the list!
                itemsList.add(0, mappedItem);
            }

            List<Items> finalItemsList = itemsList;
            runOnUiThread(() -> updateRecycler(finalItemsList));
        }).start();
    }

    private void updateRecycler(List<Items> list) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        doctorDetailsAdapter = new DoctorDetailsAdapter(DoctorDetailsActivity.this, list);
        recyclerView.setAdapter(doctorDetailsAdapter);
    }
}