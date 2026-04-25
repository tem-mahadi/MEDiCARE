package com.temmahadi.healthcare.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "custom_lab_tests")
public class CustomLabTest {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "package_name")
    public String packageName;

    @ColumnInfo(name = "cost")
    public String cost;

    @ColumnInfo(name = "details")
    public String details;

    public CustomLabTest() {}

    public CustomLabTest(String username, String packageName, String cost, String details) {
        this.username = username;
        this.packageName = packageName;
        this.cost = cost;
        this.details = details;
    }
}
