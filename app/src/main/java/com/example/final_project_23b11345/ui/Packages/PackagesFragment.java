package com.example.final_project_23b11345.ui.Packages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.final_project_23b11345.Adapters.PackagesViewPagerAdapter;
import com.example.final_project_23b11345.R;
import com.example.final_project_23b11345.databinding.FragmentPackagesBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
    public class PackagesFragment extends Fragment {
        private FragmentPackagesBinding binding;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentPackagesBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            TabLayout tabLayout = binding.navPackagesTabLayout;
            ViewPager2 viewPager2 = binding.packagesViewPager;
            PackagesViewPagerAdapter packagesViewPagerAdapter = new PackagesViewPagerAdapter(getActivity());
            viewPager2.setAdapter(packagesViewPagerAdapter);
            viewPager2.setUserInputEnabled(false);
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
                switch (position) {
                    case 0:
                        tab.setText(R.string.packages_fragment_top_headline);
                        break;
                    case 1:
                        tab.setText(R.string.packages_fragment_bottom_headline);
                        break;
                }
            }).attach();
            return root;
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
