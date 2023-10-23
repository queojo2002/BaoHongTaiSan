package com.example.baohongtaisan_2.Fragment.Admin.NguoiDung;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Activity.Admin.NguoiDung.AdminChucDanhAddActivity;
import com.example.baohongtaisan_2.Adapter.Admin.AdminChucDanhAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ChucDanh;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminQLChucDanhFragment extends Fragment {

    private SearchView SVcd;
    private RecyclerView rcvCD;
    private AdminChucDanhAdapter adminChucDanh_adapter;
    private List<ChucDanh> listCD;
    private Button btnaddCd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_q_l_chuc_danh, container, false);
        initializeViews(rootView);
        setupRecyclerView();

        setupSearchViewListeners();
        setupButtonClickListeners();

        return rootView;
    }

    private void initializeViews(View rootView) {
        rcvCD = rootView.findViewById(R.id.rvChucdanh);

        SVcd = rootView.findViewById(R.id.txtSearchCD);

        btnaddCd = rootView.findViewById(R.id.btncd_add);
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManagerCD = new LinearLayoutManager(requireContext());

        rcvCD.setLayoutManager(linearLayoutManagerCD);

        listCD = new ArrayList<>();
        adminChucDanh_adapter = new AdminChucDanhAdapter(listCD);
        rcvCD.setAdapter(adminChucDanh_adapter);

        GetListChucDanh();
    }

    private void setupSearchViewListeners() {
        SVcd.clearFocus();
        SVcd.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchChucDanh(s);
                return false;
            }
        });
    }

    private void setupButtonClickListeners() {

        btnaddCd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminChucDanhAddActivity.class);
                startActivity(intent);
            }
        });
    }

    public void GetListChucDanh() {
        ApiServices.apiServices.get_list_chucdanh().enqueue(new Callback<List<ChucDanh>>() {
            @Override
            public void onResponse(Call<List<ChucDanh>> call, Response<List<ChucDanh>> response) {
                listCD.clear();
                listCD = response.body();
                if (listCD != null && getContext() != null) {
                    adminChucDanh_adapter = new AdminChucDanhAdapter(listCD);
                    rcvCD.setAdapter(adminChucDanh_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<ChucDanh>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchChucDanh(String text) {
        ArrayList<ChucDanh> searchlist = new ArrayList<>();
        for (ChucDanh chucDanh : listCD) {
            if (chucDanh.getTenCD().toLowerCase().contains(text.toLowerCase())) {
                searchlist.add(chucDanh);
            }
        }
        adminChucDanh_adapter.searchDataList(searchlist);
    }

    @Override
    public void onResume() {
        super.onResume();
        GetListChucDanh();
    }
}