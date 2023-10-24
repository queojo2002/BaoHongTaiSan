package com.example.baohongtaisan_2.Fragment.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.User.AdapterBaoLoi_QLBH;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.Model.NotificationDataBaoHong;
import com.example.baohongtaisan_2.Model.NotificationReponse;
import com.example.baohongtaisan_2.Model.NotificationSendData;
import com.example.baohongtaisan_2.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuanLyBaoHongFragment extends Fragment {
    private View view;
    private RecyclerView rvQLBH;
    private List<BaoHong> baoHongList;
    private LinearLayoutManager linearLayoutManager;

    public QuanLyBaoHongFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baoHongList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quan_ly_bao_hong, container, false);
        _AnhXa();
        _Get_BaoHong_byMaND();
        return view;
    }

    public void _Get_BaoHong_byMaND() {
        ApiServices.apiServices.get_nguoidung_byEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                NguoiDung nguoiDung = response.body();
                if (nguoiDung != null) {
                    ApiServices.apiServices.get_list_baohong_byMaND(nguoiDung.getMaND()).enqueue(new Callback<List<BaoHong>>() {
                        @Override
                        public void onResponse(Call<List<BaoHong>> call, Response<List<BaoHong>> response) {
                            baoHongList = response.body();
                            if (getContext() != null && baoHongList != null) {
                                AdapterBaoLoi_QLBH adapterBaoLoi = new AdapterBaoLoi_QLBH(baoHongList);
                                rvQLBH.setAdapter(adapterBaoLoi);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<BaoHong>> call, Throwable t) {

                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                Toast.makeText(getContext(), "Load dữ liệu người dùng không thành công !!!", Toast.LENGTH_SHORT).show();
            }
        });


        NotificationDataBaoHong notificationDataBaoHong = new NotificationDataBaoHong(1, "duc 1","duc 2","duc 3","duc 4","duc 5");
        NotificationSendData notificationSendData = new NotificationSendData(notificationDataBaoHong, "d7Tw-dzRTR6hlM1UEn1xwY:APA91bFCo_bnfA0EuEaZB41fZC98f25H0HFDI1KMoRGuU1qdLKLMdtcdpUcZG816BZwFPP9d7kolhbfLuY6lbeJrtKiNTQsMLJVJzyQ-Uhq2xI-XwHQGbdp8QV6wyaV23UuUs3_A6gm8");
        ApiServices.apiServices_Noti.sendNoti(notificationSendData).enqueue(new Callback<NotificationReponse>() {
            @Override
            public void onResponse(Call<NotificationReponse> call, Response<NotificationReponse> response) {

            }

            @Override
            public void onFailure(Call<NotificationReponse> call, Throwable t) {

            }
        });




    }

    public void _AnhXa() {
        rvQLBH = view.findViewById(R.id.rvQLBH);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvQLBH.setLayoutManager(linearLayoutManager);
    }
}