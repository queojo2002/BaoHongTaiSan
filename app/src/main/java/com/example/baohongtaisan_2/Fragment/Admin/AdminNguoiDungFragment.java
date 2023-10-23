package com.example.baohongtaisan_2.Fragment.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.baohongtaisan_2.Adapter.Admin.TabLayoutAdapter.TabLayoutNguoiDungAdapter;
import com.example.baohongtaisan_2.R;
import com.google.android.material.tabs.TabLayout;


public class AdminNguoiDungFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_nguoi_dung, container, false);
        viewPager = view.findViewById(R.id.view_pagerND);
        tabLayout = view.findViewById(R.id.tab_layoutND);

        TabLayoutNguoiDungAdapter tabLayoutAdapter = new TabLayoutNguoiDungAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tabLayoutAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}