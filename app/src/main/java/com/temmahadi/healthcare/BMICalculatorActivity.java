package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BMICalculatorActivity extends AppCompatActivity {

    EditText editTextHeight, editTextWeight;
    Button btnCalculate, btnBack;
    CardView resultCard;
    TextView tvBMIValue, tvBMICategory, tvBMIAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        btnCalculate = findViewById(R.id.btnCalculateBMI);
        btnBack = findViewById(R.id.bmiBackBtn);
        resultCard = findViewById(R.id.resultCard);
        tvBMIValue = findViewById(R.id.tvBMIValue);
        tvBMICategory = findViewById(R.id.tvBMICategory);
        tvBMIAdvice = findViewById(R.id.tvBMIAdvice);

        btnBack.setOnClickListener(view -> {
            finish();
        });

        btnCalculate.setOnClickListener(view -> calculateBMI());
    }

    private void calculateBMI() {
        String heightStr = editTextHeight.getText().toString().trim();
        String weightStr = editTextWeight.getText().toString().trim();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both height and weight", Toast.LENGTH_SHORT).show();
            return;
        }

        double heightCm = Double.parseDouble(heightStr);
        double weightKg = Double.parseDouble(weightStr);

        if (heightCm <= 0 || weightKg <= 0) {
            Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show();
            return;
        }

        double heightM = heightCm / 100.0;
        double bmi = weightKg / (heightM * heightM);
        String bmiFormatted = String.format("%.1f", bmi);

        String category;
        String advice;
        int color;

        if (bmi < 18.5) {
            category = "Underweight";
            color = Color.parseColor("#FF9800");
            advice = "You are underweight. Consider a balanced diet rich in proteins, healthy fats, and complex carbohydrates. Consult a dietitian for a personalized meal plan.";
        } else if (bmi < 25) {
            category = "Normal";
            color = Color.parseColor("#4CAF50");
            advice = "Great job! Your BMI is in the healthy range. Maintain your current lifestyle with regular exercise and a balanced diet.";
        } else if (bmi < 30) {
            category = "Overweight";
            color = Color.parseColor("#FF9800");
            advice = "You are slightly overweight. Consider increasing physical activity and reducing processed food intake. Small lifestyle changes can make a big difference!";
        } else {
            category = "Obese";
            color = Color.parseColor("#E53935");
            advice = "Your BMI indicates obesity. Please consult a healthcare professional for guidance. Regular exercise and dietary changes are strongly recommended.";
        }

        resultCard.setVisibility(View.VISIBLE);
        tvBMIValue.setText(bmiFormatted);
        tvBMIValue.setTextColor(color);
        tvBMICategory.setText(category);
        tvBMICategory.setTextColor(color);
        tvBMIAdvice.setText(advice);

        // Save last BMI to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastBMI", bmiFormatted);
        editor.putString("lastBMICategory", category);
        editor.apply();
    }
}
