package com.example.project_test.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project_test.view.activities.company.CommentCompanyFragment;
import com.example.project_test.view.activities.company.RequestCompanyFragment;

public class ViewPagerCompanyAdapter extends FragmentStateAdapter {
    public ViewPagerCompanyAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new RequestCompanyFragment();
        }
        return new CommentCompanyFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
