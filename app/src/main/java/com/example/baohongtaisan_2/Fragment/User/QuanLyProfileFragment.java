package com.example.baohongtaisan_2.Fragment.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.baohongtaisan_2.Model.IsLogin;
import com.example.baohongtaisan_2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class QuanLyProfileFragment extends Fragment {

    TextInputEditText txtFullname_Edit, txtSDT_Edit, txtTenDangNhap_Edit, txtEmail_Edit;
    private ImageView imgProfile;
    private View view;
    private int KhoiTao = 0;
    private TextView a;
    private FirebaseUser user;

    public QuanLyProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        txtFullname_Edit = view.findViewById(R.id.Profile_txtFullname_Edit);
        txtTenDangNhap_Edit = view.findViewById(R.id.Profile_txtTenDangNhap_Edit);
        txtEmail_Edit = view.findViewById(R.id.Profile_txtEmail_Edit);
        txtSDT_Edit = view.findViewById(R.id.Profile_txtSDT_Edit);
        imgProfile = view.findViewById(R.id.imgProfile_1);

        if (KhoiTao == 0) {
            KhoiTao = 1;
            txtFullname_Edit.setText(IsLogin.getInstance().getHoVaTen());
            txtEmail_Edit.setText(IsLogin.getInstance().getEmail());
            txtTenDangNhap_Edit.setText(IsLogin.getInstance().getTenDangNhap());
            txtSDT_Edit.setText(IsLogin.getInstance().getSoDienThoai());
            Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.logo_tdmu_2).into(imgProfile);
        }
        return view;
    }


}