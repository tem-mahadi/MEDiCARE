package com.temmahadi.healthcare.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "package_name")
    public String packageName;

    @ColumnInfo(name = "cost")
    public String cost;

    public CartItem() {}

    public CartItem(String username, String packageName, String cost) {
        this.username = username;
        this.packageName = packageName;
        this.cost = cost;
    }
}
