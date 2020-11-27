package com.example.project_test;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = findViewById(R.id.text_view);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("Google");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        foregroundColorSpan = new ForegroundColorSpan(Color.BLACK);
        spannableStringBuilder.setSpan(foregroundColorSpan, 1, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        foregroundColorSpan = new ForegroundColorSpan(Color.RED);
        spannableStringBuilder.setSpan(foregroundColorSpan, 2, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        foregroundColorSpan = new ForegroundColorSpan(Color.YELLOW);
        spannableStringBuilder.setSpan(foregroundColorSpan, 3, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
        spannableStringBuilder.setSpan(foregroundColorSpan, 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableStringBuilder);
        textView.setTextSize(30);
    }
}