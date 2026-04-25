package com.temmahadi.healthcare.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "appointments")
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "doctor_name")
    public String doctorName;

    @ColumnInfo(name = "hospital")
    public String hospital;

    @ColumnInfo(name = "contact")
    public String contact;

    @ColumnInfo(name = "fee")
    public String fee;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "username")
    public String username;

    public Appointment() {}

    public Appointment(String doctorName, String hospital, String contact, String fee, String date, String time, String username) {
        this.doctorName = doctorName;
        this.hospital = hospital;
        this.contact = contact;
        this.fee = fee;
        this.date = date;
        this.time = time;
        this.username = username;
    }
}
