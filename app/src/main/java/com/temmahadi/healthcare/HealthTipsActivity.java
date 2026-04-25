package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.temmahadi.healthcare.Adapter.HealthTipsAdapter;

import java.util.ArrayList;
import java.util.List;

public class HealthTipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        Button backBtn = findViewById(R.id.tipsBackBtn);
        backBtn.setOnClickListener(view -> {
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String[]> tips = new ArrayList<>();
        tips.add(new String[]{"💧", "Stay Hydrated", "Drink at least 8 glasses (2 liters) of water daily. Proper hydration boosts energy, improves skin health, and helps your organs function optimally."});
        tips.add(new String[]{"🥗", "Eat a Balanced Diet", "Include fruits, vegetables, whole grains, lean proteins, and healthy fats in every meal. A colorful plate often means a nutritious plate."});
        tips.add(new String[]{"🏃", "Exercise Regularly", "Aim for at least 30 minutes of moderate exercise 5 days a week. Walking, cycling, or swimming can significantly reduce the risk of chronic diseases."});
        tips.add(new String[]{"😴", "Prioritize Sleep", "Adults need 7-9 hours of quality sleep. Maintain a consistent sleep schedule and avoid screens before bedtime for better rest."});
        tips.add(new String[]{"🧘", "Manage Stress", "Practice mindfulness, meditation, or deep breathing exercises. Chronic stress weakens your immune system and affects mental health."});
        tips.add(new String[]{"🚭", "Avoid Smoking", "Smoking damages nearly every organ in your body. Quitting smoking at any age can significantly improve your health and add years to your life."});
        tips.add(new String[]{"🧴", "Protect Your Skin", "Apply sunscreen with SPF 30+ before going outdoors. UV radiation is the leading cause of skin aging and increases cancer risk."});
        tips.add(new String[]{"🦷", "Maintain Oral Health", "Brush twice daily and floss regularly. Poor oral health is linked to heart disease, diabetes, and respiratory infections."});
        tips.add(new String[]{"🧠", "Keep Your Mind Active", "Read books, solve puzzles, or learn new skills. Mental stimulation helps prevent cognitive decline and keeps your brain sharp."});
        tips.add(new String[]{"👥", "Stay Socially Connected", "Maintain relationships with family and friends. Social isolation increases the risk of depression, anxiety, and even cardiovascular disease."});
        tips.add(new String[]{"🍎", "Limit Sugar Intake", "Excessive sugar consumption leads to obesity, type 2 diabetes, and tooth decay. Choose natural sweeteners and whole fruits instead."});
        tips.add(new String[]{"🧂", "Reduce Salt Intake", "High sodium intake raises blood pressure. Aim for less than 5g of salt per day and flavor food with herbs and spices instead."});
        tips.add(new String[]{"🩺", "Regular Health Checkups", "Visit your doctor for annual checkups even if you feel healthy. Early detection of health issues leads to better treatment outcomes."});
        tips.add(new String[]{"💊", "Take Medications on Time", "If prescribed medication, take it consistently at the same time every day. Set alarms or use pill organizers to stay on track."});
        tips.add(new String[]{"🧤", "Practice Good Hygiene", "Wash your hands frequently with soap for at least 20 seconds. Good hygiene is your first line of defense against infectious diseases."});

        HealthTipsAdapter adapter = new HealthTipsAdapter(tips);
        recyclerView.setAdapter(adapter);
    }
}
