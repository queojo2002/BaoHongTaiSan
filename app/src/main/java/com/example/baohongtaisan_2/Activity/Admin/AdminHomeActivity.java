package com.example.baohongtaisan_2.Activity.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.baohongtaisan_2.Activity.LoginActivity;
import com.example.baohongtaisan_2.Fragment.Admin.AdminBaoHongFragment;
import com.example.baohongtaisan_2.Fragment.Admin.AdminNguoiDungFragment;
import com.example.baohongtaisan_2.Fragment.Admin.AdminPhongFragment;
import com.example.baohongtaisan_2.Fragment.Admin.AdminTaiSanFragment;
import com.example.baohongtaisan_2.Fragment.User.TrangChuFragment;
import com.example.baohongtaisan_2.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txt_Email, txt_FullName, Title;
    private ImageView imgProfile;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private String currentFrament_NAV = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        _AnhXa();

        if (savedInstanceState == null) { // Mở home trước
            Open_NAV_TrangChu(0);
            navigationView.setCheckedItem(R.id.nav_admin_trangchu);
        }

        _SuKien();


    }


    private void _AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        Title = findViewById(R.id.Title);
        bottomNavigationView = findViewById(R.id.bottom_admin_view);
        drawerLayout = findViewById(R.id.drawer_layout_admin);
        navigationView = findViewById(R.id.nav_admin_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        txt_FullName = headerView.findViewById(R.id.txtFullName);
        txt_Email = headerView.findViewById(R.id.txtEmail);
        imgProfile = headerView.findViewById(R.id.imgProfile);

        setSupportActionBar(toolbar);
        Title.setText("Tổng quan");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }

    private void _SuKien() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_admin_trangchu) {
                    Open_NAV_TrangChu(1);
                } else if (item.getItemId() == R.id.bottom_admin_qlnguoidung) {
                    Open_NAV_QuanLy_NguoiDung(1);
                } else if (item.getItemId() == R.id.bottom_admin_qlphong) {
                    Open_NAV_QuanLy_Phong(1);
                } else if (item.getItemId() == R.id.bottom_admin_qltaisan) {
                    Open_NAV_QuanLy_TaiSan(1);
                } else if (item.getItemId() == R.id.bottom_admin_qlbaohong) {
                    Open_NAV_Quanly_BaoHong(1);
                }
                return true;
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_admin_trangchu) { // click trang chủ
            Open_NAV_TrangChu(0);
        } else if (item.getItemId() == R.id.nav_admin_qlnguoidung) { // click quản lý người dùng
            Open_NAV_QuanLy_NguoiDung(0);
        } else if (item.getItemId() == R.id.nav_admin_qlphong) { // click quản lý phòng
            Open_NAV_QuanLy_Phong(0);
        } else if (item.getItemId() == R.id.nav_admin_qltaisan) { // click quản lý tài sản
            Open_NAV_QuanLy_TaiSan(0);
        } else if (item.getItemId() == R.id.nav_admin_qlbaohong) { // click quản lý báo hỏng
            Open_NAV_Quanly_BaoHong(0);
        } else if (item.getItemId() == R.id.nav_admin_dangxuat) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .requestProfile()
                    .build();
            GoogleSignInClient mGoogle = GoogleSignIn.getClient(AdminHomeActivity.this, googleSignInOptions);
            mGoogle.signOut();
            mAuth.signOut();
            Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void Open_NAV_TrangChu(int OptionNAV) {
        if (currentFrament_NAV != "nav_admin_trangchu") {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_admin, new TrangChuFragment()).commit();
            Title.setText("Trang chủ");
            currentFrament_NAV = "nav_admin_trangchu";
            if (OptionNAV == 0) {
                bottomNavigationView.getMenu().findItem(R.id.bottom_admin_trangchu).setChecked(true); // set menu bottom
            } else {
                navigationView.setCheckedItem(R.id.nav_admin_trangchu);
            }
        }
    }

    private void Open_NAV_QuanLy_NguoiDung(int OptionNAV) {
        if (currentFrament_NAV != "nav_admin_qlnguoidung") {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_admin, new AdminNguoiDungFragment()).commit();
            Title.setText("Quản lí người dùng");
            currentFrament_NAV = "nav_admin_qlnguoidung";
            if (OptionNAV == 0) {
                bottomNavigationView.getMenu().findItem(R.id.bottom_admin_qlnguoidung).setChecked(true); // set menu bottom
            } else {
                navigationView.setCheckedItem(R.id.nav_admin_qlnguoidung);
            }
        }
    }

    private void Open_NAV_QuanLy_Phong(int OptionNAV) {
        if (currentFrament_NAV != "nav_admin_qlphong") {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_admin, new AdminPhongFragment()).commit();
            Title.setText("Quản lí phòng");
            currentFrament_NAV = "nav_admin_qlphong";
            if (OptionNAV == 0) {
                bottomNavigationView.getMenu().findItem(R.id.bottom_admin_qlphong).setChecked(true); // set menu bottom
            } else {
                navigationView.setCheckedItem(R.id.nav_admin_qlphong);
            }
        }
    }

    private void Open_NAV_QuanLy_TaiSan(int OptionNAV) {
        if (currentFrament_NAV != "nav_admin_qltaisan") {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_admin, new AdminTaiSanFragment()).commit();
            Title.setText("Quản lí tài sản");
            currentFrament_NAV = "nav_admin_qltaisan";
            if (OptionNAV == 0) {
                bottomNavigationView.getMenu().findItem(R.id.bottom_admin_qltaisan).setChecked(true); // set menu bottom
            } else {
                navigationView.setCheckedItem(R.id.nav_admin_qltaisan);
            }
        }
    }


    private void Open_NAV_Quanly_BaoHong(int OptionNAV) {
        if (currentFrament_NAV != "nav_admin_qlbaohong") {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_admin, new AdminBaoHongFragment()).commit();
            Title.setText("Quản lí báo hỏng");
            currentFrament_NAV = "nav_admin_qlbaohong";
            if (OptionNAV == 0) {
                bottomNavigationView.getMenu().findItem(R.id.bottom_admin_qlbaohong).setChecked(true);
            } else {
                navigationView.setCheckedItem(R.id.nav_admin_qlbaohong);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}