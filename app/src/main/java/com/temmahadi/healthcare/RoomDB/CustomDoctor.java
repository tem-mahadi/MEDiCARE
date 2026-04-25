package com.temmahadi.healthcare.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "custom_doctors")
public class CustomDoctor {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "contact")
    public String contact;

    @ColumnInfo(name = "experience")
    public String experience;

    @ColumnInfo(name = "fee")
    public String fee;

    public CustomDoctor() {}

    public CustomDoctor(String username, String category, String name, String address, String contact, String experience, String fee) {
        this.username = username;
        this.category = category;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.experience = experience;
        this.fee = fee;
    }
}
