package com.example.baohongtaisan_2.Adapter.Admin.TabLayoutAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.baohongtaisan_2.Fragment.Admin.NguoiDung.AdminDanhSachNguoiDungFragment;
import com.example.baohongtaisan_2.Fragment.Admin.NguoiDung.AdminQLChucDanhFragment;
import com.example.baohongtaisan_2.Fragment.Admin.NguoiDung.AdminQLDonViFragment;
import com.example.baohongtaisan_2.Fragment.Admin.NguoiDung.AdminQLPhanQuyenFragment;


public class TabLayoutNguoiDungAdapter extends FragmentStatePagerAdapter {
    public TabLayoutNguoiDungAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AdminDanhSachNguoiDungFragment();
            case 1:
                return new AdminQLDonViFragment();
            case 2:
                return new AdminQLChucDanhFragment();
            case 3:
                return new AdminQLPhanQuyenFragment();
            default:
                return new AdminDanhSachNguoiDungFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
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
                title = "Đơn vị";
                break;
            case 2:
                title = "Chức danh";
                break;
            case 3:
                title = "Phân quyền";
                break;
        }
        return title;
    }
}
