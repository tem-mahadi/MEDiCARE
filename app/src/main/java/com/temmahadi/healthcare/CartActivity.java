package com.temmahadi.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.temmahadi.healthcare.Adapter.CartAdapter;
import com.temmahadi.healthcare.RoomDB.BookedLabTest;
import com.temmahadi.healthcare.RoomDB.CartItem;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerCart;
    TextView cartTotalCost;
    Button btnCheckout;
    LinearLayout emptyCartState, bottomPanel;
    DatabaseHelper db;
    String username;
    List<CartItem> cartItems;
    CartAdapter adapter;
    int totalAmount = 0;

    String selectedDate = "";
    String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Button backBtn = findViewById(R.id.cartBackBtn);
        backBtn.setOnClickListener(v -> finish());

        recyclerCart = findViewById(R.id.recyclerCart);
        cartTotalCost = findViewById(R.id.cartTotalCost);
        btnCheckout = findViewById(R.id.btnCheckout);
        emptyCartState = findViewById(R.id.emptyCartState);
        bottomPanel = findViewById(R.id.bottomPanel);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String storedUsername = sharedPreferences.getString("username", "");
        username = storedUsername.isEmpty() ? "guest" : storedUsername;
        db = DatabaseHelper.getInstance(this);

        loadCartItems();

        btnCheckout.setOnClickListener(v -> {
            showDateTimePicker();
        });
    }

    private void loadCartItems() {
        cartItems = db.cartDao().getByUser(username);

        if (cartItems.isEmpty()) {
            emptyCartState.setVisibility(View.VISIBLE);
            recyclerCart.setVisibility(View.GONE);
            bottomPanel.setVisibility(View.GONE);
        } else {
            emptyCartState.setVisibility(View.GONE);
            recyclerCart.setVisibility(View.VISIBLE);
            bottomPanel.setVisibility(View.VISIBLE);

            recyclerCart.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CartAdapter(this, cartItems, cartItem -> {
                // Delete item
                db.cartDao().deleteById(cartItem.id);
                Toast.makeText(CartActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
                loadCartItems(); // refresh
            });
            recyclerCart.setAdapter(adapter);

            calculateTotal();
        }
    }

    private void calculateTotal() {
        totalAmount = 0;
        for (CartItem item : cartItems) {
            try {
                totalAmount += Integer.parseInt(item.cost);
            } catch (NumberFormatException ignored) {}
        }
        cartTotalCost.setText("৳" + totalAmount);
    }

    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;

            // Now show time picker
            int hrs = calendar.get(Calendar.HOUR_OF_DAY);
            int mins = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                int displayHour = hourOfDay % 12;
                if (displayHour == 0) {
                    displayHour = 12;
                }
                String amPm = hourOfDay < 12 ? "AM" : "PM";
                selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", displayHour, minute, amPm);
                processCheckout();
            }, hrs, mins, false);
            timePickerDialog.show();

        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void processCheckout() {
        new Thread(() -> {
            for (CartItem item : cartItems) {
                BookedLabTest bookedTest = new BookedLabTest(username, item.packageName, item.cost, selectedDate, selectedTime);
                db.bookedLabTestDao().insert(bookedTest);

                // Schedule reminder 1 hour before
                int notificationId = (item.packageName + selectedDate + selectedTime).hashCode();
                NotificationHelper.scheduleReminder(
                        CartActivity.this,
                        notificationId,
                        "Lab Test Reminder",
                        "You have a scheduled " + item.packageName + " test today at " + selectedTime,
                        selectedDate,
                        selectedTime
                );
            }
            // Clear cart
            db.cartDao().clearCart(username);

            runOnUiThread(() -> {
                new AlertDialog.Builder(CartActivity.this)
                        .setTitle("✅ Booking Confirmed!")
                        .setMessage("Your lab tests have been booked for:\n\n" +
                                "📅 Date: " + selectedDate + "\n" +
                                "🕐 Time: " + selectedTime + "\n" +
                                "💰 Total Paid: ৳" + totalAmount)
                        .setPositiveButton("OK", (dialog, which) -> finish())
                        .setCancelable(false)
                        .show();
                loadCartItems(); // refresh to show empty state
            });
        }).start();
    }
}
