package com.example.baohongtaisan_2.Adapter.Admin.TabLayoutAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.baohongtaisan_2.Fragment.Admin.Phong.AdminDanhSachPhongFragment;
import com.example.baohongtaisan_2.Fragment.Admin.Phong.AdminKhuVucPhongFragment;
import com.example.baohongtaisan_2.Fragment.Admin.Phong.AdminLoaiPhongFragment;

public class TabLayoutPhongAdapter extends FragmentStatePagerAdapter {
    public TabLayoutPhongAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AdminDanhSachPhongFragment();
            case 1:
                return new AdminKhuVucPhongFragment();
            case 2:
                return new AdminLoaiPhongFragment();
            default:
                return new AdminDanhSachPhongFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Danh sách";
                break;
            case 1:
                title = "Khu vực phòng";
                break;
            case 2:
                title = "Loại phòng";
                break;
        }
        return title;
    }
}
