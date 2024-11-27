package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.temmahadi.healthcare.RoomDB.DatabaseHelper;
import com.temmahadi.healthcare.RoomDB.Items;

public class LabTestDetailsActivity extends AppCompatActivity {
    TextView tv1,tv2;
    EditText ed;
    Button back,add;
    Items item; SharedPreferences sharedPreferences;
    DatabaseHelper roomDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);
        tv1 = findViewById(R.id.LDTitleName);
        tv2 = findViewById(R.id.totalcost);
        ed = findViewById(R.id.textmultiline);
        back = findViewById(R.id.ldbackbtn);
        add = findViewById(R.id.addtocart);

        ed.setKeyListener(null);
        Intent intent = getIntent();
        tv1.setText(intent.getStringExtra("text1"));
        ed.setText(intent.getStringExtra("text2"));
        tv2.setText("Total Cost: "+intent.getStringExtra("text3")+"/-");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestDetailsActivity.this,LabTestActivity.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
//                String username= sharedPreferences.getString("username","").toString();
//                String product= tv1.getText().toString();
//                String cost= tv2.getText().toString();
//                String[] details= {username,product,cost};
//                boolean check = roomDB.mainDAO().check("LabCart",details);
//                if(check) {
//                    Toast.makeText(getApplicationContext(), "Product Already Added", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    item = new Items(details,"LabCart");
//                    roomDB.mainDAO().saveItem(item);
//                    Toast.makeText(getApplicationContext(), "Record Inserted to Cart", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(LabTestDetailsActivity.this,LabTestActivity.class));
//                }
            }
        });

    }
}