package com.temmahadi.healthcare.RoomDB;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static String fromStringArray(String[] array) {
        return array != null ? String.join(",", array) : null;
    }
    @TypeConverter
    public static String[] toStringArray(String data) {
        return data != null ? data.split(",") : new String[0];
    }
}
