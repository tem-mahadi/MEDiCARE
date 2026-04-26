package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.temmahadi.healthcare.BackEnd.ApiService;
import com.temmahadi.healthcare.BackEnd.MobileNumberRequest;
import com.temmahadi.healthcare.BackEnd.OTPActivity;
import com.temmahadi.healthcare.BackEnd.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edPhone, edUsername;
    Button btnSendOTP;
    ProgressBar progressBar;
    MobileNumberRequest mobileNumberRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        // Check if user is already subscribed
        if (sharedPreferences.getBoolean("isSubscribed", false)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        edPhone = findViewById(R.id.editTextLoginPhone);
        edUsername = findViewById(R.id.editTextLoginUsername);
        btnSendOTP = findViewById(R.id.buttonSendOTP);
        progressBar = findViewById(R.id.progressBarLogin);

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edPhone.getText().toString().trim();
                String username = edUsername.getText().toString().trim();
                
                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                } else if (phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                } else if (phone.length() < 11) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                } else {
                    sendMobileNumberToServer(phone, username);
                }
            }
        });
    }

    private void sendMobileNumberToServer(String mobileNumber, String username) {
        progressBar.setVisibility(View.VISIBLE);
        btnSendOTP.setEnabled(false);
        
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<MobileNumberRequest> call = apiService.sendMobileNumber(mobileNumber);
        
        call.enqueue(new Callback<MobileNumberRequest>() {
            @Override
            public void onResponse(Call<MobileNumberRequest> call, Response<MobileNumberRequest> response) {
                progressBar.setVisibility(View.GONE);
                btnSendOTP.setEnabled(true);
                
                if (response.isSuccessful() && response.body() != null) {
                    mobileNumberRequest = response.body();
                    
                    // We only care if we got a reference number
                    if (mobileNumberRequest.getReferenceNo() != null && !mobileNumberRequest.getReferenceNo().isEmpty()) {
                        Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                        intent.putExtra("mobile_number", mobileNumber);
                        intent.putExtra("username", username);
                        intent.putExtra("referenceNo", mobileNumberRequest.getReferenceNo());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Failed to get reference number. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to connect to subscription server.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MobileNumberRequest> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnSendOTP.setEnabled(true);
                Log.e("LoginActivity", "API Error: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Network Error. Please check your connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}