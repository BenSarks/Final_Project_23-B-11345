package com.example.final_project_23b11345.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.final_project_23b11345.ui.Packages.TabMainFragments.ProcessedPackageFragment;
import com.example.final_project_23b11345.ui.Packages.TabMainFragments.ProcessingPackageFragment;


public class PackagesViewPagerAdapter extends FragmentStateAdapter {
    public PackagesViewPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

    @NonNull
    @Override
        public Fragment createFragment(int position) {
        Fragment fragment;
        if (position == 1) {
            fragment = new ProcessedPackageFragment();
            fragment.setArguments(new Bundle());
            return fragment;
        }
        fragment = new ProcessingPackageFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
