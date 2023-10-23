package com.example.baohongtaisan_2.Activity.User;

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

import com.bumptech.glide.Glide;
import com.example.baohongtaisan_2.Activity.LoginActivity;
import com.example.baohongtaisan_2.Fragment.User.NhanTinFragment;
import com.example.baohongtaisan_2.Fragment.User.ProfileFragment;
import com.example.baohongtaisan_2.Fragment.User.QuanLyBaoHongFragment;
import com.example.baohongtaisan_2.Fragment.User.TraCuuFragment;
import com.example.baohongtaisan_2.Fragment.User.TrangChuFragment;
import com.example.baohongtaisan_2.Model.IsLogin;
import com.example.baohongtaisan_2.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView txt_Email, txt_FullName;
    private ImageView imgProfile;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private View headerView;
    private String currentFrament_NAV = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        _AnhXa();

        if (savedInstanceState == null) { // Mở home trước
            Open_NAV_TrangChu(0);
            navigationView.setCheckedItem(R.id.nav_trangchu);
        }

        _SuKien();


    }


    public void _AnhXa() {
        System.out.println(IsLogin.getInstance().getHoVaTen());
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        txt_FullName = headerView.findViewById(R.id.txtFullName);
        txt_Email = headerView.findViewById(R.id.txtEmail);
        imgProfile = headerView.findViewById(R.id.imgProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trang chủ");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    public void _SuKien() {


        // Sự kiện click menu bên dưới
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_trangchu) { // click Trang Chủ
                    Open_NAV_TrangChu(1);
                } else if (item.getItemId() == R.id.bottom_manager_baohong) { // Click Quản lý thông tin báo hỏng
                    Open_NAV_QuanLy_ThongBaoThietBi(1);
                } else if (item.getItemId() == R.id.bottom_baohong_tracuu) { // Click Tra cứu và báo hỏng
                    Open_NAV_TraCuu(1);
                } else if (item.getItemId() == R.id.bottom_nhantin) { // Click Nhắn tin
                    Open_NAV_NhanTin(1);
                } else if (item.getItemId() == R.id.bottom_manager_info) { // click quản lý thông tin cá nhân
                    Open_NAV_QuanLy_ThongTinCaNhan(1);
                }
                return true;
            }
        });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_trangchu) { // click trang chủ
            Open_NAV_TrangChu(0);
        } else if (item.getItemId() == R.id.nav_manager_info) { // click quản lý thông tin cá nhân
            Open_NAV_QuanLy_ThongTinCaNhan(0);
        } else if (item.getItemId() == R.id.nav_manager_baohong) { // click quản lý thông báo thiết bị
            Open_NAV_QuanLy_ThongBaoThietBi(0);
        } else if (item.getItemId() == R.id.nav_baohong_tracuu) { // click tra cứu và báo hỏng
            Open_NAV_TraCuu(0);
        } else if (item.getItemId() == R.id.nav_nhantin) { // click nhắn tin
            Open_NAV_NhanTin(0);
        } else if (item.getItemId() == R.id.nav_btnLogout) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .requestProfile()
                    .build();
            GoogleSignInClient mGoogle = GoogleSignIn.getClient(HomeActivity.this, googleSignInOptions);
            mGoogle.signOut();
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void Open_NAV_TrangChu(int OptionNAV) {
        if (currentFrament_NAV != "nav_trangchu") {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new TrangChuFragment()).commit();
            getSupportActionBar().setTitle("Trang chủ");
            currentFrament_NAV = "nav_trangchu";
            if (OptionNAV == 0) {
                bottomNavigationView.getMenu().findItem(R.id.bottom_trangchu).setChecked(true); // set menu bottom
            } else {
                navigationView.setCheckedItem(R.id.nav_trangchu);
            }
        }
    }

    private void Open_NAV_QuanLy_ThongTinCaNhan(int OptionNAV) {
        if (currentFrament_NAV != "nav_manager_info") {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ProfileFragment()).commit();
            getSupportActionBar().setTitle("Quản lý thông tin cá nhân");
            currentFrament_NAV = "nav_manager_info";
            if (OptionNAV == 0) {
                bottomNavigationView.getMenu().findItem(R.id.bottom_manager_info).setChecked(true); // set menu bottom
            } else {
                navigationView.setCheckedItem(R.id.nav_manager_info);
            }
        }
    }

    private void Open_NAV_QuanLy_ThongBaoThietBi(int OptionNAV) {
        if (currentFrament_NAV != "nav_manager_thietbi") {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new QuanLyBaoHongFragment()).commit();
            getSupportActionBar().setTitle("Quản lý thông báo thiết bị");
            currentFrament_NAV = "nav_manager_thietbi";
            if (OptionNAV == 0) {
                bottomNavigationView.getMenu().findItem(R.id.bottom_manager_baohong).setChecked(true); // set menu bottom
            } else {
                navigationView.setCheckedItem(R.id.nav_manager_baohong);
            }
        }
    }


    private void Open_NAV_TraCuu(int OptionNAV) {
        if (currentFrament_NAV != "nav_tracuu") {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new TraCuuFragment()).commit();
            getSupportActionBar().setTitle("Tra cứu & Báo hỏng");
            currentFrament_NAV = "nav_tracuu";
            if (OptionNAV == 0) {
                bottomNavigationView.getMenu().findItem(R.id.bottom_baohong_tracuu).setChecked(true); // set menu bottom
            } else {
                navigationView.setCheckedItem(R.id.nav_baohong_tracuu);
            }
        }
    }

    private void Open_NAV_NhanTin(int OptionNAV) {
        if (currentFrament_NAV != "nav_nhantin") {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new NhanTinFragment()).commit();
            getSupportActionBar().setTitle("Nhắn tin trực tuyến");
            currentFrament_NAV = "nav_nhantin";
            if (OptionNAV == 0) {
                bottomNavigationView.getMenu().findItem(R.id.bottom_nhantin).setChecked(true); // set menu bottom
            } else {
                navigationView.setCheckedItem(R.id.nav_nhantin);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) finishAffinity();
        txt_FullName.setText(user.getDisplayName());
        txt_Email.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.logo_tdmu_2).into(imgProfile);
    }


}