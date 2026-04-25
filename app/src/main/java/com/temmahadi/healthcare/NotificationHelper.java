package com.temmahadi.healthcare;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationHelper {

    public static final String CHANNEL_ID = "medicare_reminders";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MediCare Reminders";
            String description = "Reminders for Lab Tests and Appointments";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void scheduleReminder(Context context, int notificationId, String title, String message, String date, String time) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("notificationId", notificationId);

        // FLAG_IMMUTABLE is required for Android 12+
        int flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, flags);

        try {
            Date parsedDate = parseDateTime(date, time);
            
            if (parsedDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(parsedDate);
                
                // Set the reminder 1 hour before the scheduled time
                calendar.add(Calendar.HOUR_OF_DAY, -1);
                
                long alarmTimeMillis = calendar.getTimeInMillis();
                
                // Ensure we don't schedule alarms in the past
                if (alarmTimeMillis > System.currentTimeMillis()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTimeMillis, pendingIntent);
                    } else {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeMillis, pendingIntent);
                    }
                    Log.d("Reminder", "Reminder scheduled for: " + calendar.getTime().toString());
                } else {
                    Log.d("Reminder", "Time is in the past, not scheduling reminder.");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("Reminder", "Failed to parse date/time: " + date + " " + time);
        }
    }

    private static Date parseDateTime(String date, String time) throws ParseException {
        String dateTimeText = date + " " + time;
        String[] patterns = {
                "d/M/yyyy hh:mm a",
                "dd/MM/yyyy hh:mm a",
                "d/M/yyyy HH:mm",
                "dd/MM/yyyy HH:mm"
        };

        ParseException last = null;
        for (String pattern : patterns) {
            try {
                return new SimpleDateFormat(pattern, Locale.getDefault()).parse(dateTimeText);
            } catch (ParseException e) {
                last = e;
            }
        }

        throw last != null ? last : new ParseException("Unsupported date/time format", 0);
    }
}
