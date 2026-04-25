package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.temmahadi.healthcare.Adapter.LabTestAdapter;
import com.temmahadi.healthcare.RoomDB.CustomLabTest;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;
import com.temmahadi.healthcare.RoomDB.Items;

import java.util.ArrayList;
import java.util.List;

public class LabTestActivity extends AppCompatActivity {
    Button backbtn;
    FloatingActionButton fabAdd;
    RecyclerView recyclerView;
    LabTestAdapter labTestAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        backbtn = findViewById(R.id.ltbackbtn);
        fabAdd = findViewById(R.id.fabAddCustomLabTest);
        recyclerView = findViewById(R.id.recycleViewLT);
        databaseHelper = DatabaseHelper.getInstance(this);

        backbtn.setOnClickListener(view -> finish());

        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(LabTestActivity.this, AddCustomLabTestActivity.class));
        });

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        new Thread(() -> {
            List<Items> testList = databaseHelper.mainDAO().getAll("Lab Test");
            if (testList == null) testList = new ArrayList<>();

            List<Items> packageList = databaseHelper.mainDAO().getAll("Package Details");
            if (packageList == null) packageList = new ArrayList<>();

            // Fetch custom user lab tests
            List<CustomLabTest> customTests = databaseHelper.customLabTestDao().getAll();
            if (customTests == null) {
                customTests = new ArrayList<>();
            }

            for (CustomLabTest clt : customTests) {
                // Map to "Lab Test"
                String[] testDetails = {clt.packageName, "", "", "", clt.cost};
                Items testItem = new Items(testDetails, "Lab Test");
                testList.add(0, testItem);

                // Map to "Package Details"
                String[] pkgDetails = {clt.details, "", "", "", ""};
                Items pkgItem = new Items(pkgDetails, "Package Details");
                packageList.add(0, pkgItem);
            }

            List<Items> finalTestList = testList;
            List<Items> finalPkgList = packageList;
            runOnUiThread(() -> updateRecycler(finalTestList, finalPkgList));
        }).start();
    }

    private void updateRecycler(List<Items> testList, List<Items> packageList) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        labTestAdapter = new LabTestAdapter(LabTestActivity.this, testList, packageList);
        recyclerView.setAdapter(labTestAdapter);
    }
}