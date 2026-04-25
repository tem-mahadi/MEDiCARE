package com.temmahadi.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.temmahadi.healthcare.Adapter.BookedLabTestsAdapter;
import com.temmahadi.healthcare.RoomDB.BookedLabTest;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UpcomingLabTestsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout emptyState;
    private DatabaseHelper db;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_lab_tests);

        Button backBtn = findViewById(R.id.upcomingLabBackBtn);
        backBtn.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerUpcomingLabTests);
        emptyState = findViewById(R.id.emptyUpcomingLabState);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String storedUsername = sharedPreferences.getString("username", "");
        username = storedUsername.isEmpty() ? "guest" : storedUsername;

        db = DatabaseHelper.getInstance(this);
        loadUpcomingTests();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUpcomingTests();
    }

    private void loadUpcomingTests() {
        new Thread(() -> {
            List<BookedLabTest> allTests = db.bookedLabTestDao().getByUser(username);
            List<BookedLabTest> upcomingTests = filterAndSortUpcoming(allTests);

            runOnUiThread(() -> {
                if (upcomingTests.isEmpty()) {
                    emptyState.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyState.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(UpcomingLabTestsActivity.this));
                    recyclerView.setAdapter(new BookedLabTestsAdapter(upcomingTests, test -> showDeleteConfirmation(test)));
                }
            });
        }).start();
    }

    private void showDeleteConfirmation(BookedLabTest test) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Upcoming Lab Test")
                .setMessage("Remove this paid lab test from upcoming list?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    new Thread(() -> {
                        db.bookedLabTestDao().deleteById(test.id);
                        runOnUiThread(this::loadUpcomingTests);
                    }).start();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private List<BookedLabTest> filterAndSortUpcoming(List<BookedLabTest> tests) {
        List<BookedLabTest> upcoming = new ArrayList<>();
        Date now = new Date();

        for (BookedLabTest test : tests) {
            Date dateTime = parseDateTime(test.date, test.time);
            if (dateTime != null && !dateTime.before(now)) {
                upcoming.add(test);
            }
        }

        Collections.sort(upcoming, Comparator.comparing(test -> parseDateTime(test.date, test.time)));
        return upcoming;
    }

    private Date parseDateTime(String date, String time) {
        String dateTimeText = date + " " + time;
        String[] patterns = {
                "d/M/yyyy hh:mm a",
                "dd/MM/yyyy hh:mm a",
                "d/M/yyyy HH:mm",
                "dd/MM/yyyy HH:mm"
        };

        for (String pattern : patterns) {
            try {
                return new SimpleDateFormat(pattern, Locale.getDefault()).parse(dateTimeText);
            } catch (Exception ignored) {
            }
        }

        return null;
    }
}
