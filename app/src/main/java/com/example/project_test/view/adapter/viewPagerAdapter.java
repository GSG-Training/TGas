package com.example.project_test.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.project_test.R;
import com.example.project_test.model.utils.guide;

import java.util.List;

public class viewPagerAdapter extends PagerAdapter {
    List<guide> guideList ;

    public viewPagerAdapter(List<guide> guideList) {
        this.guideList = guideList;
    }

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

