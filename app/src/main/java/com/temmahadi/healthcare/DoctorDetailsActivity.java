package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView tv; Button btn;
    private String[][] doctor_details1 = {
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
    };
    private String[][] doctor_details2 = {
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
    };
    private String[][] doctor_details3 = {
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
    };
    private String[][] doctor_details4 = {
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
    };
    private String[][] doctor_details5 = {
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
            {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No:01988263710","1000"},
    };
    private String[][] doctor_details = {};
    HashMap<String, String> item;
    SimpleAdapter sa;
    ArrayList List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv= findViewById(R.id.FDTitleName);
        Intent it= getIntent();
        String title= it.getStringExtra("title");
        tv.setText(title);

        assert title != null;
        if(title.compareTo("Family Physician")==0)
            doctor_details = doctor_details1;
        else
        if(title.compareTo("Dietitian")==0)
            doctor_details = doctor_details2;
        else
        if(title.compareTo("Dentist")==0)
            doctor_details = doctor_details3;
        else
        if(title.compareTo("Surgeon")==0)
            doctor_details = doctor_details4;
        else
            doctor_details = doctor_details5;


        btn.setOnClickListener(view -> {
            startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
        });

        List = new ArrayList<>();
        for(int i=0; i<doctor_details.length; i++){
            item = new HashMap<String, String>();
            item.put("Line1",doctor_details[i][0]);
            item.put("Line2",doctor_details[i][1]);
            item.put("Line3",doctor_details[i][2]);
            item.put("Line4",doctor_details[i][3]);
            item.put("Line5","Cons Fees:"+doctor_details[i][4]+"/-");
            List.add(item);
        }
        sa= new SimpleAdapter(this,List,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});

        ListView lst = findViewById(R.id.listViewDD);
        lst.setAdapter(sa);
    }
}