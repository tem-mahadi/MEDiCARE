package com.temmahadi.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EmergencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        Button backBtn = findViewById(R.id.emergencyBackBtn);
        backBtn.setOnClickListener(view -> {
            finish();
        });

        LinearLayout emergencyList = findViewById(R.id.emergencyList);

        String[][] contacts = {
                {"🚑", "Ambulance (National)", "999", "National Emergency Service"},
                {"🚒", "Fire Service", "199", "Bangladesh Fire Service & Civil Defence"},
                {"👮", "Police Emergency", "999", "Bangladesh Police Emergency"},
                {"🏥", "Dhaka Medical College", "02-55165088", "Emergency Department"},
                {"💉", "Poison Control", "01779554391", "National Poison Information Centre"},
                {"🩸", "Sandhani Blood Bank", "01779-554391", "Blood Donation & Supply"},
                {"🆘", "Child Helpline", "1098", "National Child Protection Helpline"},
                {"👩", "Women Helpline", "10921", "National Women's Helpline"},
                {"🧠", "Mental Health", "16789", "Kaan Pete Roi — Mental Health Support"},
        };

        for (String[] contact : contacts) {
            addEmergencyCard(emergencyList, contact[0], contact[1], contact[2], contact[3]);
        }
    }

    private void addEmergencyCard(LinearLayout parent, String emoji, String title, String number, String description) {
        CardView card = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.bottomMargin = dpToPx(10);
        card.setLayoutParams(cardParams);
        card.setRadius(dpToPx(16));
        card.setCardElevation(dpToPx(2));
        card.setCardBackgroundColor(Color.WHITE);

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));
        row.setGravity(Gravity.CENTER_VERTICAL);

        // Emoji
        TextView emojiView = new TextView(this);
        emojiView.setText(emoji);
        emojiView.setTextSize(28);
        emojiView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams emojiParams = new LinearLayout.LayoutParams(dpToPx(48), dpToPx(48));
        emojiView.setLayoutParams(emojiParams);
        row.addView(emojiView);

        // Text column
        LinearLayout textCol = new LinearLayout(this);
        textCol.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        textParams.setMarginStart(dpToPx(14));
        textCol.setLayoutParams(textParams);

        TextView titleView = new TextView(this);
        titleView.setText(title);
        titleView.setTextColor(Color.parseColor("#1A1A2E"));
        titleView.setTextSize(16);
        titleView.setTypeface(null, android.graphics.Typeface.BOLD);
        textCol.addView(titleView);

        TextView descView = new TextView(this);
        descView.setText(description);
        descView.setTextColor(Color.parseColor("#5A6178"));
        descView.setTextSize(12);
        textCol.addView(descView);

        TextView numView = new TextView(this);
        numView.setText("📞 " + number);
        numView.setTextColor(Color.parseColor("#1A73E8"));
        numView.setTextSize(14);
        numView.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams numParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        numParams.topMargin = dpToPx(4);
        numView.setLayoutParams(numParams);
        textCol.addView(numView);

        row.addView(textCol);
        card.addView(row);
        parent.addView(card);

        // Click to dial
        card.setOnClickListener(view -> {
            try {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + number.replaceAll("[^0-9+]", "")));
                startActivity(dialIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Unable to open dialer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}
