package com.example.baohongtaisan_2.Fragment.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.baohongtaisan_2.Adapter.Admin.TabLayoutAdapter.TabLayoutTaiSanAdapter;
import com.example.baohongtaisan_2.R;
import com.google.android.material.tabs.TabLayout;


public class AdminTaiSanFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_tai_san, container, false);

        viewPager = view.findViewById(R.id.view_pagertaisan);
        tabLayout = view.findViewById(R.id.tab_layouttaisan);

        TabLayoutTaiSanAdapter tabLayoutAdapter = new TabLayoutTaiSanAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tabLayoutAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}