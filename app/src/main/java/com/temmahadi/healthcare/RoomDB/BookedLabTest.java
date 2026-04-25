package com.temmahadi.healthcare.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "booked_lab_tests")
public class BookedLabTest {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "package_name")
    public String packageName;

    @ColumnInfo(name = "cost")
    public String cost;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    public BookedLabTest() {}

    public BookedLabTest(String username, String packageName, String cost, String date, String time) {
        this.username = username;
        this.packageName = packageName;
        this.cost = cost;
        this.date = date;
        this.time = time;
    }
}
