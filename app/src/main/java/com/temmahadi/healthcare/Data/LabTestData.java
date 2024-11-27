package com.temmahadi.healthcare.Data;

import android.app.Application;
import android.content.Context;

import com.temmahadi.healthcare.RoomDB.DatabaseHelper;
import com.temmahadi.healthcare.RoomDB.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LabTestData extends Application {
    DatabaseHelper roomDB;
    Context context;

    public LabTestData(DatabaseHelper roomDB) {
        this.roomDB = roomDB;
    }

    public LabTestData(DatabaseHelper roomDB, Context context) {
        this.roomDB = roomDB;
        this.context = context;
    }
    public void package_details(){
        String[][] package_details = {
                {
                        "Blood Glucose Fasting\n" +
                                "Complete Hemogram\n" +
                                "HbAlc\n" +
                                "Iron Studies\n" +
                                "Kidney Function Test\n" +
                                "LDH Lactate Dehydrogenase, Serum\n" +
                                "Lipid Profile\n" +
                                "Liver Function Test",
                        "Blood Glucose Fasting",
                        "COVID-19 Antibody - Ig6",
                        "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)",
                        "Complete Hemogram\n" +
                                "CRP (C Reactive Protein) Quantitative, Serum\n" +
                                "Iron Studies\n" +
                                "Kidney Function Test\n" +
                                "Vitamin D Total-25 Hydroxy\n" +
                                "Liver Function Test\n" +
                                "Lipid Profile"
                }
        };
        prepareItems("Package Details",package_details);
    }
    public void LBData(){
        String[][] lbdata = {
                {"Package 1 : Full Body Checkup", "","","","999"},
                {"Package 2 : Blood Glucose Fasting", "","","","299"},
                {"Package 3 : COVID-19 Antibody - Ig6", "","","","899"},
                {"Package 4 : Thyroid Check", "","","","499"},
                {"Package 5 : Immunity Check", "","","","699"},

        };
        prepareItems("Lab Test",lbdata);
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
