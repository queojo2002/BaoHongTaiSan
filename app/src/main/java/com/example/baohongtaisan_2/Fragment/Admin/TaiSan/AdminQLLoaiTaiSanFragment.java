package com.example.baohongtaisan_2.Fragment.Admin.TaiSan;

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

import com.example.baohongtaisan_2.Activity.Admin.TaiSan.AdminLoaiTaiSanAddActivity;
import com.example.baohongtaisan_2.Activity.Admin.TaiSan.AdminNhomTaiSanAddActivity;
import com.example.baohongtaisan_2.Adapter.Admin.AdminLoaiTaiSanAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.AdminNhomTaiSanAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.LoaiTaiSan;
import com.example.baohongtaisan_2.Model.NhomTaiSan;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminQLLoaiTaiSanFragment extends Fragment {

    private SearchView SVlts;
    private RecyclerView rcvLTS;
    private AdminLoaiTaiSanAdapter lts_adapter;
    private List<LoaiTaiSan> listLTS;
    private Button btnaddlts;


    private void initializeViews(View rootView) {
        rcvLTS = rootView.findViewById(R.id.rvLoaiTS);

        SVlts = rootView.findViewById(R.id.txtSearchLoaiTS);

        btnaddlts = rootView.findViewById(R.id.btnloaits_add);

    }

    private void setupButtonClickListeners() {

        btnaddlts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminLoaiTaiSanAddActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rcvLTS.setLayoutManager(linearLayoutManager);

        listLTS = new ArrayList<>();
        lts_adapter = new AdminLoaiTaiSanAdapter(listLTS);
        rcvLTS.setAdapter(lts_adapter);

        GetListLTS();
    }

    private void setupSearchViewListeners() {
        SVlts.clearFocus();
        SVlts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchLTS(s);
                return false;
            }
        });

    }

    public void GetListLTS() {
        ApiServices.apiServices.get_list_loaitaisan().enqueue(new Callback<List<LoaiTaiSan>>() {
            @Override
            public void onResponse(Call<List<LoaiTaiSan>> call, Response<List<LoaiTaiSan>> response) {
                listLTS.clear();
                listLTS = response.body();
                if (listLTS != null && getContext() != null) {
                    lts_adapter = new AdminLoaiTaiSanAdapter(listLTS);
                    rcvLTS.setAdapter(lts_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<LoaiTaiSan>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchLTS(String text) {
        ArrayList<LoaiTaiSan> searchlist = new ArrayList<>();
        for (LoaiTaiSan loaiTaiSan : listLTS) {
            if (loaiTaiSan.getTenLTS().toLowerCase().contains(text.toLowerCase())) {
                searchlist.add(loaiTaiSan);
            }
        }
        lts_adapter.searchDataList(searchlist);
    }

    @Override
    public void onResume() {
        super.onResume();
        GetListLTS();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_q_l_loai_tai_san, container, false);
        initializeViews(rootView);

        setupRecyclerView();
        setupSearchViewListeners();
        setupButtonClickListeners();

        return rootView;
    }
}