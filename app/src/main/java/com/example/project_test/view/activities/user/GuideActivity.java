package com.example.project_test.view.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.project_test.R;
import com.example.project_test.model.utils.guide;
import com.example.project_test.view.adapter.viewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    List<guide> guideList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        guideList.add(new guide(R.drawable.guide1, "to create a new account click in button signup"));
        guideList.add(new guide(R.drawable.guide2, "Enter your data to complete your login"));
        guideList.add(new guide(R.drawable.guide3, "you are in main page select what you want from service"));
        guideList.add(new guide(R.drawable.guide4, "shift to right to show you own menu"));


        TabLayout tabLayout = findViewById(R.id.tab_layout_guide);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(120, 0, 120, 0);
        viewPager.setAdapter(new viewPagerAdapter(guideList));
        tabLayout.setupWithViewPager(viewPager, true);
        findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,PolicyActivity.class));
                finish();
            }
        });

    }


}