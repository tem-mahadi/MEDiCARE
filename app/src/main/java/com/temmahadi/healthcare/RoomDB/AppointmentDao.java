package com.temmahadi.healthcare.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppointmentDao {
    @Insert
    void insert(Appointment appointment);

    @Update
    void update(Appointment appointment);

    @Query("SELECT * FROM appointments WHERE username = :username ORDER BY id DESC")
    List<Appointment> getByUser(String username);

    @Query("SELECT COUNT(*) FROM appointments WHERE username = :username")
    int getCount(String username);

    @Query("DELETE FROM appointments WHERE id = :id")
    void deleteById(int id);
}
