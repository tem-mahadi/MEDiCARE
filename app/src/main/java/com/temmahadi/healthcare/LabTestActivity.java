package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.temmahadi.healthcare.Adapter.DoctorDetailsAdapter;
import com.temmahadi.healthcare.Adapter.LabTestAdapter;
import com.temmahadi.healthcare.Data.LabTestData;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;
import com.temmahadi.healthcare.RoomDB.Items;

import java.util.List;

public class LabTestActivity extends AppCompatActivity {
    Button backbtn,gobtn;
    RecyclerView recyclerView;
    LabTestAdapter labTestAdapter;
    List<Items> testList,packageList;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);
        backbtn = findViewById(R.id.ltbackbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this,HomeActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recycleViewLT);
        databaseHelper = DatabaseHelper.getInstance(this);
        testList = databaseHelper.mainDAO().getAll("Lab Test");
        packageList = databaseHelper.mainDAO().getAll("Package Details");
        updateRecycler(testList);


    }
    private void updateRecycler(List < Items > list) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        labTestAdapter = new LabTestAdapter(LabTestActivity.this, list,packageList);
        recyclerView.setAdapter(labTestAdapter);
    }
}