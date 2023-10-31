package com.example.baohongtaisan_2.Fragment.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.User.AdapterBaoLoi_QLBH;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.Model.IsLogin;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;

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

    private AdapterBaoLoi_QLBH adapterBaoLoi;
    private androidx.appcompat.widget.SearchView sv;

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
        _SuKien();
        return view;
    }


    public void _AnhXa() {
        rvQLBH = view.findViewById(R.id.rvQLBH);
        sv = view.findViewById(R.id.svQLBH_1);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvQLBH.setLayoutManager(linearLayoutManager);
        rvQLBH.setFocusable(false);
    }

    public void _SuKien() {
        sv.clearFocus();
        sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<BaoHong> searchlist = new ArrayList<>();
                for (BaoHong baoHong : baoHongList) {
                    if (baoHong.getTenP().toLowerCase().contains(s.toLowerCase()) || baoHong.getTenTS().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(baoHong);
                    }
                }
                adapterBaoLoi.searchDataList(searchlist);
                return false;
            }
        });

    }


    public void _Get_BaoHong_byMaND() {
        ApiServices.apiServices.get_list_baohong_byMaND(IsLogin.getInstance().getMaND()).enqueue(new Callback<List<BaoHong>>() {
            @Override
            public void onResponse(Call<List<BaoHong>> call, Response<List<BaoHong>> response) {
                baoHongList = response.body();
                if (getContext() != null && baoHongList != null) {
                    adapterBaoLoi = new AdapterBaoLoi_QLBH(baoHongList);
                    rvQLBH.setAdapter(adapterBaoLoi);
                }
            }

            @Override
            public void onFailure(Call<List<BaoHong>> call, Throwable t) {

            }
        });
    }


}