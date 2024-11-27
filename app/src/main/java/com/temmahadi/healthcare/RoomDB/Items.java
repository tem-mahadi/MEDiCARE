package com.temmahadi.healthcare.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName= "items")
public class Items {
    @PrimaryKey(autoGenerate = true)
    int ID=0;
    @ColumnInfo(name="category")
    String category;
    @ColumnInfo(name= "Details")
    String[] Details;

    public Items(){}

    public Items(String[] Details, String category) {
        this.Details = Details;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getDetails() {
        return Details;
    }

    public void setDetails(String[] details) {
        this.Details = details;
    }
}
