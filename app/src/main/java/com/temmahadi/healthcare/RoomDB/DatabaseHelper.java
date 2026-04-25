package com.temmahadi.healthcare.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@TypeConverters({Converters.class})
@Database(entities = {Items.class, Appointment.class, CartItem.class, BookedLabTest.class, CustomDoctor.class, CustomLabTest.class}, version = 4, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {
    private static DatabaseHelper database;
    private static String DATABASE_NAME = "MyDB";

    public synchronized static DatabaseHelper getInstance(Context context){
        if(database==null){
            database = Room.databaseBuilder(context.getApplicationContext(),DatabaseHelper.class,DATABASE_NAME)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }
    public abstract ItemsDao mainDAO();
    public abstract AppointmentDao appointmentDao();
    public abstract CartDao cartDao();
    public abstract BookedLabTestDao bookedLabTestDao();
    public abstract CustomDoctorDao customDoctorDao();
    public abstract CustomLabTestDao customLabTestDao();
}
