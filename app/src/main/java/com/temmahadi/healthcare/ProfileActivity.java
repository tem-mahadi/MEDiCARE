package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.temmahadi.healthcare.BackEnd.ApiService;
import com.temmahadi.healthcare.BackEnd.RetrofitClient;
import com.temmahadi.healthcare.BackEnd.UnsubscribeRequest;
import com.temmahadi.healthcare.BackEnd.UnsubscribeResponse;
import com.temmahadi.healthcare.RoomDB.DatabaseHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        String mobileNumber = sharedPreferences.getString("mobile_number", "");
        String lastBMI = sharedPreferences.getString("lastBMI", "--");
        String lastBMICategory = sharedPreferences.getString("lastBMICategory", "");

        // Set profile data
        TextView profileInitial = findViewById(R.id.profileInitial);
        TextView profileUsername = findViewById(R.id.profileUsername);
        TextView profilePhone = findViewById(R.id.profilePhone);
        TextView profileBMI = findViewById(R.id.profileBMI);
        TextView profileBMICategory = findViewById(R.id.profileBMICategory);
        TextView profileAppointments = findViewById(R.id.profileAppointments);

        profileInitial.setText(username.isEmpty() ? "U" : String.valueOf(username.charAt(0)).toUpperCase());
        profileUsername.setText(username);
        profilePhone.setText(mobileNumber);
        profileBMI.setText(lastBMI);
        profileBMICategory.setText(lastBMICategory);

        // Get appointment count
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        int appointmentCount = db.appointmentDao().getCount(username);
        profileAppointments.setText(String.valueOf(appointmentCount));

        // Logout
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("isSubscribed");
            editor.apply();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });

        // Back to Home
        Button btnBackHome = findViewById(R.id.btnBackHome);
        btnBackHome.setOnClickListener(view -> finish());

        // Unsubscribe
        Button btnUnsubscribe = findViewById(R.id.btnUnsubscribe);
        btnUnsubscribe.setOnClickListener(view -> showUnsubscribeDialog(mobileNumber, sharedPreferences));
    }

    private void showUnsubscribeDialog(String phone, SharedPreferences sharedPreferences) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Unsubscribe")
                .setMessage("Are you sure you want to unsubscribe from MEDiCARE? You will lose access to premium features.")
                .setPositiveButton("Yes, Unsubscribe", (dialog, which) -> performUnsubscribe(phone, sharedPreferences))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void performUnsubscribe(String phone, SharedPreferences sharedPreferences) {
        if (phone == null || phone.trim().isEmpty()) {
            Toast.makeText(ProfileActivity.this, "No mobile number found for unsubscribe", Toast.LENGTH_SHORT).show();
            return;
        }

        String subscriberId = phone.trim();
        if (subscriberId.startsWith("0")) {
            subscriberId = "88" + subscriberId;
        }

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        UnsubscribeRequest request = new UnsubscribeRequest(subscriberId);

        Call<UnsubscribeResponse> call = apiService.unsubscribeUser(request);
        
        call.enqueue(new Callback<UnsubscribeResponse>() {
            @Override
            public void onResponse(Call<UnsubscribeResponse> call,
                                   Response<UnsubscribeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ("S1000".equals(response.body().getStatusCode())) {
                        Toast.makeText(ProfileActivity.this, "Successfully Unsubscribed", Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed to unsubscribe: " + response.body().getStatusDetail(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Server error. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UnsubscribeResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Network error. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
