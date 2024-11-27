package com.temmahadi.healthcare.RoomDB;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemsDao {
    @Insert(onConflict = REPLACE)
    void saveItem(Items item);
    @Query("SELECT * FROM items WHERE category = :category ORDER BY ID ASC")
    List<Items> getAll(String category);
    @Query("DELETE FROM items")
    void clearAll();
//    @Query("SELECT * FROM items WHERE category = :category and Details = :details")
//    boolean check(String category, String[] details);
}
