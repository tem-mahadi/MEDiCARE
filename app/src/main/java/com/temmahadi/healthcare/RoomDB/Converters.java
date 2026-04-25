package com.temmahadi.healthcare.RoomDB;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromStringArray(String[] array) {
        if (array == null) {
            return null;
        }
        // JSON keeps commas and empty trailing values intact.
        return gson.toJson(array);
    }

    @TypeConverter
    public static String[] toStringArray(String data) {
        if (data == null) {
            return new String[0];
        }

        String trimmed = data.trim();
        if (trimmed.startsWith("[")) {
            try {
                String[] parsed = gson.fromJson(trimmed, String[].class);
                return parsed != null ? parsed : new String[0];
            } catch (Exception ignored) {
                // Fall back to legacy parser for malformed values.
            }
        }

        // Legacy format fallback used by old rows created before JSON storage.
        return data.split(",", -1);
    }
}
