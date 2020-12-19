package com.example.project_test.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.project_test.R;
import com.example.project_test.user.guide;
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
        viewPager.setAdapter(new viewPagerAdapter());
        tabLayout.setupWithViewPager(viewPager, true);
      /*  ViewPager2 viewPager2=findViewById(R.id.view_pager2);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(new viewPagerguideAdapter(this,guideList));*/

    }

    class viewPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.image_guide_cell, container, false);

            ImageView imageView = view.findViewById(R.id.image_guide);
            TextView textView = view.findViewById(R.id.text_guide);
            imageView.setImageResource(guideList.get(position).getImage());
            textView.setText(guideList.get(position).getText());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return guideList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

    }
}