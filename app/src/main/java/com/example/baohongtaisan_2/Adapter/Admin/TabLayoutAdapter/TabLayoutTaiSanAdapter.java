package com.example.baohongtaisan_2.Adapter.Admin.TabLayoutAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.baohongtaisan_2.Fragment.Admin.TaiSan.AdminDanhSachTaiSanFragment;
import com.example.baohongtaisan_2.Fragment.Admin.TaiSan.AdminQLLoaiTaiSanFragment;
import com.example.baohongtaisan_2.Fragment.Admin.TaiSan.AdminQLNhomTaiSanFragment;

public class TabLayoutTaiSanAdapter extends FragmentStatePagerAdapter {
    public TabLayoutTaiSanAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AdminDanhSachTaiSanFragment();
            case 1:
                return new AdminQLNhomTaiSanFragment();
            case 2:
                return new AdminQLLoaiTaiSanFragment();
            default:
                return new AdminDanhSachTaiSanFragment();
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
                title = "Nhóm tài sản";
                break;
            case 2:
                title = "Loại tài sản";
                break;
        }
        return title;
    }
}
