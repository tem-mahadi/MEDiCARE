package com.temmahadi.healthcare.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CustomLabTestDao {
    @Insert
    void insert(CustomLabTest customLabTest);

    @Query("SELECT * FROM custom_lab_tests ORDER BY id DESC")
    List<CustomLabTest> getAll();
}
