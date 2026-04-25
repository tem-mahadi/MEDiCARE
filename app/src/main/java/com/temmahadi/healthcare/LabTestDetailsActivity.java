package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.temmahadi.healthcare.RoomDB.Converters;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;
import com.temmahadi.healthcare.RoomDB.Items;

import com.temmahadi.healthcare.RoomDB.CartItem;
import java.util.Arrays;

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
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                String cartOwner = username.isEmpty() ? "guest" : username;
                String packageName = tv1.getText().toString();
                String cost = intent.getStringExtra("text3"); // get original cost string

                roomDB = DatabaseHelper.getInstance(LabTestDetailsActivity.this);

                new Thread(() -> {
                    int count = roomDB.cartDao().checkExists(cartOwner, packageName);
                    if (count > 0) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Package Already in Cart", Toast.LENGTH_SHORT).show());
                    } else {
                        CartItem cartItem = new CartItem(cartOwner, packageName, cost);
                        roomDB.cartDao().insert(cartItem);
                        runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(), "Added to Cart successfully", Toast.LENGTH_SHORT).show();
                            // In the future, we could navigate to CartActivity here
                        });
                    }
                }).start();
            }
        });

    }
}