package com.example.baohongtaisan_2.Adapter.Admin.TabLayoutAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.baohongtaisan_2.Fragment.Admin.PhanBo.AdminDSTSTrongPhongFragment;
import com.example.baohongtaisan_2.Fragment.Admin.PhanBo.AdminPBTSFragment;

public class TabLayoutPBTSAdapter extends FragmentStatePagerAdapter {

    private final int MaP;
    private final String TenP;

    public TabLayoutPBTSAdapter(@NonNull FragmentManager fm, int behavior, int MaP, String TenP) {
        super(fm, behavior);
        this.MaP = MaP;
        this.TenP = TenP;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new AdminDSTSTrongPhongFragment(MaP, TenP);
        }
        return new AdminPBTSFragment(MaP, TenP);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Phân bổ";
                break;
            case 1:
                title = "Danh sách tài sản";
                break;
        }
        return title;
    }
}
