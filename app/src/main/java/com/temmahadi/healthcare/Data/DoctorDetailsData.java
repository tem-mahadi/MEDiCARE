package com.temmahadi.healthcare.Data;

import android.app.Application;
import android.content.Context;

import com.temmahadi.healthcare.RoomDB.DatabaseHelper;
import com.temmahadi.healthcare.RoomDB.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoctorDetailsData extends Application {
    DatabaseHelper roomDB;
    String category;
    Context context;

    public DoctorDetailsData(DatabaseHelper roomDB) {
        this.roomDB = roomDB;
    }

    public DoctorDetailsData(DatabaseHelper roomDB, Context context) {
        this.roomDB = roomDB;
        this.context = context;
    }
    public void doctor1(){
        String[][] doctor_details1 = {
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
        };
        prepareItems("Family Physician",doctor_details1);
    }
    public void doctor2(){
        String[][] doctor_details2 = {
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
        };
        prepareItems("Dietitian",doctor_details2);
    }
    public void doctor3(){
        String[][] doctor_details3 = {
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
        };
        prepareItems("Dentist",doctor_details3);
    }
    public void doctor4(){
        String[][] doctor_details1 = {
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
        };
        prepareItems("Surgeon",doctor_details1);
    }
    public void doctor5(){
        String[][] doctor_details1 = {
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
                {"Doctor Name : Hasan Al Mahadi", "Hospital Address: Rangpur Medical College", "Exp : 5yrs", "Mobile No: 01988263710","1000"},
        };
        prepareItems("Cardiologist",doctor_details1);
    }
    public void prepareItems(String category, String[][] data) {
        List<Items> datalist = new ArrayList<>();
        for (String[] details : data) {
            datalist.add(new Items(details, category));
        }
        // Insert all items in a background thread
        new Thread(() -> {
            for (Items item : datalist) {
                roomDB.mainDAO().saveItem(item);
            }
        }).start();
    }
}
