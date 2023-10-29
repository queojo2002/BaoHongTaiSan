package com.example.baohongtaisan_2.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.baohongtaisan_2.Adapter.Admin.TabLayoutAdapter.TabLayoutPBTSAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.TabLayoutAdapter.TabLayoutPhongAdapter;
import com.example.baohongtaisan_2.Fragment.Admin.PhanBo.AdminDSTSTrongPhongFragment;
import com.example.baohongtaisan_2.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class AdminPhanBoTaiSanActivity extends AppCompatActivity {

    private Intent intent;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_phan_bo_tai_san);
        _AnhXa();
        _SuKien();
    }

    private void _AnhXa() {
        intent = getIntent();
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager_pbts);
        tabLayout = findViewById(R.id.tablayout_pbts);
        TabLayoutPBTSAdapter tabLayoutPBTSAdapter = new TabLayoutPBTSAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, intent.getIntExtra("MaP", -1), intent.getStringExtra("TenP"));
        viewPager.setAdapter(tabLayoutPBTSAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        getSupportActionBar().setTitle("Phân bổ: " + intent.getStringExtra("TenP") + "");
    }


    public void _SuKien(){

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1)
                {
                    String tag = "android:switcher:" + viewPager.getId() + ":" +  tab.getPosition();
                    Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
                    if(f instanceof AdminDSTSTrongPhongFragment) {
                        ((AdminDSTSTrongPhongFragment) f).onResume();
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}