package com.temmahadi.healthcare.BackEnd;

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
import android.widget.TextView;
import android.widget.Toast;

import com.temmahadi.healthcare.HomeActivity;
import com.temmahadi.healthcare.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {
    
    EditText edotp;
    Button submitbtn;
    TextView tvPrompt;
    String phone, ref, username; 
    ProgressBar progressBar; 
    OTPRequest otpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        Log.d("ActivityCheck", "OTPActivity started");

        edotp = findViewById(R.id.editTextOTP);
        submitbtn = findViewById(R.id.buttonSubmit);
        progressBar = findViewById(R.id.progressBarOTP);
        tvPrompt = findViewById(R.id.tvOTPPrompt);

        phone = getIntent().getStringExtra("mobile_number");
        username = getIntent().getStringExtra("username");
        ref = getIntent().getStringExtra("referenceNo");

        if (phone != null) {
            tvPrompt.setText("Please enter the verification code sent to " + phone + ".");
        }

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = edotp.getText().toString().trim();
                if (!otp.isEmpty()) {
                    verifyOTPWithServer(ref, otp);
                } else {
                    Toast.makeText(OTPActivity.this, "Enter a valid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void verifyOTPWithServer(String referenceNo, String otp) {
        progressBar.setVisibility(View.VISIBLE);
        submitbtn.setEnabled(false);
        
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<OTPRequest> call = apiService.verifyOTP(otp, referenceNo);
        
        call.enqueue(new Callback<OTPRequest>() {
            @Override
            public void onResponse(Call<OTPRequest> call, Response<OTPRequest> response) {
                progressBar.setVisibility(View.GONE);
                submitbtn.setEnabled(true);
                
                if (response.isSuccessful() && response.body() != null) {
                    otpRequest = response.body();
                    
                    // DEBUG LOGGING
                    Log.d("OTPActivity_DEBUG", "subscriptionStatus: " + otpRequest.getsubscriptionStatus());
                    Log.d("OTPActivity_DEBUG", "referenceNo: " + otpRequest.getreferenceNo());
                    Log.d("OTPActivity_DEBUG", "Raw JSON string: " + new com.google.gson.Gson().toJson(otpRequest));

                    String status = otpRequest.getsubscriptionStatus();
                    if ("S1000".equals(status) || 
                        "INITIAL CHARGING PENDING".equals(status) || 
                        "REGISTERED".equals(status)) {
                        
                        // Save subscription state, username, and phone to local storage
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isSubscribed", true);
                        editor.putString("username", username); // Save actual name for display
                        editor.putString("mobile_number", phone); // Save phone number for API/Unsubscribe
                        editor.apply();

                        Toast.makeText(OTPActivity.this, "Successfully Subscribed!", Toast.LENGTH_SHORT).show();
                        
                        // Go to Home
                        Intent intent = new Intent(OTPActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(OTPActivity.this, "Invalid OTP or Server rejected. Status: " + otpRequest.getsubscriptionStatus(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(OTPActivity.this, "Verification Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OTPRequest> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                submitbtn.setEnabled(true);
                Log.e("OTPActivity", "API Error: " + t.getMessage());
                Toast.makeText(OTPActivity.this, "Network Error. Check connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}