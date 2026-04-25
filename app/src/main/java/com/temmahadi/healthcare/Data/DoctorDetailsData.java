package com.temmahadi.healthcare.Data;

import android.app.Application;
import android.content.Context;

import com.temmahadi.healthcare.RoomDB.DatabaseHelper;
import com.temmahadi.healthcare.RoomDB.Items;

import java.util.ArrayList;
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

    public void doctor1() {
        String[][] doctor_details = {
                {"Dr. Kamal Hossain", "Rangpur Medical College Hospital", "Exp : 15 yrs", "Mobile No: 01712345678", "800"},
                {"Dr. Fatima Akter", "Dhaka Medical College Hospital", "Exp : 10 yrs", "Mobile No: 01812345679", "1000"},
                {"Dr. Rafiq Islam", "Square Hospital, Dhaka", "Exp : 20 yrs", "Mobile No: 01912345680", "1500"},
                {"Dr. Nasrin Jahan", "Popular Medical College", "Exp : 8 yrs", "Mobile No: 01612345681", "700"},
                {"Dr. Aminul Haque", "Ibn Sina Hospital, Dhaka", "Exp : 12 yrs", "Mobile No: 01512345682", "1200"},
        };
        prepareItems("Family Physician", doctor_details);
    }

    public void doctor2() {
        String[][] doctor_details = {
                {"Dr. Sharmin Sultana", "BIRDEM Hospital, Dhaka", "Exp : 9 yrs", "Mobile No: 01713456789", "600"},
                {"Dr. Tanvir Ahmed", "United Hospital, Dhaka", "Exp : 7 yrs", "Mobile No: 01813456790", "800"},
                {"Dr. Rumana Parveen", "Lab Aid Hospital, Dhaka", "Exp : 14 yrs", "Mobile No: 01913456791", "1000"},
                {"Dr. Zahid Hasan", "Rangpur Dental College", "Exp : 5 yrs", "Mobile No: 01613456792", "500"},
                {"Dr. Nusrat Jahan", "Apollo Hospital, Dhaka", "Exp : 11 yrs", "Mobile No: 01513456793", "900"},
        };
        prepareItems("Dietitian", doctor_details);
    }

    public void doctor3() {
        String[][] doctor_details = {
                {"Dr. Mahbub Alam", "Dental Unit, DMC", "Exp : 18 yrs", "Mobile No: 01714567890", "1200"},
                {"Dr. Sabrina Rahman", "dental.care Clinic, Gulshan", "Exp : 6 yrs", "Mobile No: 01814567891", "700"},
                {"Dr. Imran Chowdhury", "National Dental College", "Exp : 10 yrs", "Mobile No: 01914567892", "900"},
                {"Dr. Farzana Yasmin", "SmilePlus Dental, Banani", "Exp : 4 yrs", "Mobile No: 01614567893", "500"},
                {"Dr. Arif Hossain", "City Dental Hospital, Ctg", "Exp : 16 yrs", "Mobile No: 01514567894", "1100"},
        };
        prepareItems("Dentist", doctor_details);
    }

    public void doctor4() {
        String[][] doctor_details = {
                {"Dr. Shahidul Islam", "CMH Dhaka Cantonment", "Exp : 22 yrs", "Mobile No: 01715678901", "2000"},
                {"Dr. Meherun Nessa", "BSMMU, Shahbag", "Exp : 15 yrs", "Mobile No: 01815678902", "1800"},
                {"Dr. Khaled Mahmud", "Evercare Hospital, Dhaka", "Exp : 12 yrs", "Mobile No: 01915678903", "1500"},
                {"Dr. Sumaiya Akter", "Holy Family Hospital", "Exp : 8 yrs", "Mobile No: 01615678904", "1000"},
                {"Dr. Rezaul Karim", "Comfort Nursing Home, Ctg", "Exp : 19 yrs", "Mobile No: 01515678905", "1700"},
        };
        prepareItems("Surgeon", doctor_details);
    }

    public void doctor5() {
        String[][] doctor_details = {
                {"Dr. Abdul Wadud", "National Heart Foundation", "Exp : 25 yrs", "Mobile No: 01716789012", "2500"},
                {"Dr. Hasina Begum", "Ibrahim Cardiac Hospital", "Exp : 18 yrs", "Mobile No: 01816789013", "2000"},
                {"Dr. Sohel Rana", "Square Hospital Cardiac", "Exp : 14 yrs", "Mobile No: 01916789014", "1800"},
                {"Dr. Taslima Khatun", "United Hospital Cardiology", "Exp : 10 yrs", "Mobile No: 01616789015", "1500"},
                {"Dr. Monir Uddin", "Evercare Heart Center", "Exp : 20 yrs", "Mobile No: 01516789016", "2200"},
        };
        prepareItems("Cardiologist", doctor_details);
    }

    public void doctor6() {
        String[][] doctor_details = {
                {"Dr. Ayesha Karim", "General Specialty Clinic, Dhaka", "Exp : 11 yrs", "Mobile No: 01717890123", "1000"},
                {"Dr. Nabil Rahman", "City Care Center, Chattogram", "Exp : 9 yrs", "Mobile No: 01817890124", "900"},
                {"Dr. Sharifa Islam", "Prime Health Point, Rajshahi", "Exp : 13 yrs", "Mobile No: 01917890125", "1200"},
                {"Dr. Towhid Hasan", "Central Medical Hub, Khulna", "Exp : 7 yrs", "Mobile No: 01617890126", "850"},
                {"Dr. Nusrat Ahmed", "Community Health Service, Sylhet", "Exp : 10 yrs", "Mobile No: 01517890127", "950"},
        };
        prepareItems("Other", doctor_details);
    }

    public void prepareItems(String category, String[][] data) {
        List<Items> datalist = new ArrayList<>();
        for (String[] details : data) {
            datalist.add(new Items(details, category));
        }
        new Thread(() -> {
            for (Items item : datalist) {
                roomDB.mainDAO().saveItem(item);
            }
        }).start();
    }
}
