package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.temmahadi.healthcare.Adapter.DoctorDetailsAdapter;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;
import com.temmahadi.healthcare.RoomDB.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView tv;
    Button btn;
    RecyclerView recyclerView;
    DoctorDetailsAdapter doctorDetailsAdapter;
    List<Items> itemsList;
    DatabaseHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        btn = findViewById(R.id.backbtn);
        tv = findViewById(R.id.FDTitleName);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        recyclerView = findViewById(R.id.recycleView);

        database = DatabaseHelper.getInstance(this);
        itemsList = database.mainDAO().getAll(title);
        updateRecycler(itemsList);

        btn.setOnClickListener(view -> {
            startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
        });
    }
        private void updateRecycler(List < Items > list) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
            doctorDetailsAdapter = new DoctorDetailsAdapter(DoctorDetailsActivity.this, list);
            recyclerView.setAdapter(doctorDetailsAdapter);
        }

}