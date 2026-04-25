package com.temmahadi.healthcare.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CustomDoctorDao {
    @Insert
    void insert(CustomDoctor customDoctor);

    @Query("SELECT * FROM custom_doctors WHERE category = :category ORDER BY id DESC")
    List<CustomDoctor> getByCategory(String category);
}
