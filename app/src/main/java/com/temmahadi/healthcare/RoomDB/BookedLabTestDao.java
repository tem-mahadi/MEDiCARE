package com.temmahadi.healthcare.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookedLabTestDao {
    @Insert
    void insert(BookedLabTest bookedLabTest);

    @Query("SELECT * FROM booked_lab_tests WHERE username = :username ORDER BY id DESC")
    List<BookedLabTest> getByUser(String username);

    @Query("DELETE FROM booked_lab_tests WHERE id = :id")
    void deleteById(int id);
}
