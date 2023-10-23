package com.example.baohongtaisan_2.Fragment.Admin.Phong;

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

import com.example.baohongtaisan_2.Activity.Admin.Phong.AdminLoaiPhongAddActivity;
import com.example.baohongtaisan_2.Adapter.Admin.AdminLoaiPhongAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminLoaiPhongFragment extends Fragment {

    private SearchView SVlp;
    private RecyclerView rcvLP;
    private AdminLoaiPhongAdapter lp_adapter;
    private List<LoaiPhong> listLP;
    private Button btnaddLp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_loai_phong, container, false);
        initializeViews(rootView);

        setupRecyclerView();
        setupSearchViewListeners();
        setupButtonClickListeners();

        return rootView;

    }

    private void initializeViews(View rootView) {
        rcvLP = rootView.findViewById(R.id.rvLoaiphong);

        SVlp = rootView.findViewById(R.id.txtSearchLoaiphong);

        btnaddLp = rootView.findViewById(R.id.btnloaiphong_add);

    }

    private void setupButtonClickListeners() {

        btnaddLp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminLoaiPhongAddActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManagerLP = new LinearLayoutManager(requireContext());
        rcvLP.setLayoutManager(linearLayoutManagerLP);

        listLP = new ArrayList<>();
        lp_adapter = new AdminLoaiPhongAdapter(listLP);
        rcvLP.setAdapter(lp_adapter);

        GetListLP();
    }

    private void setupSearchViewListeners() {
        SVlp.clearFocus();
        SVlp.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchLoaiPhong(s);
                return false;
            }
        });

    }

    public void GetListLP() {
        ApiServices.apiServices.get_list_loaiphong().enqueue(new Callback<List<LoaiPhong>>() {
            @Override
            public void onResponse(Call<List<LoaiPhong>> call, Response<List<LoaiPhong>> response) {
                listLP.clear();
                listLP = response.body();
                if (listLP != null && getContext() != null) {
                    lp_adapter = new AdminLoaiPhongAdapter(listLP);
                    rcvLP.setAdapter(lp_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<LoaiPhong>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchLoaiPhong(String text) {
        ArrayList<LoaiPhong> searchlist = new ArrayList<>();
        for (LoaiPhong loaiPhong : listLP) {
            if (loaiPhong.getTenLP().toLowerCase().contains(text.toLowerCase())) {
                searchlist.add(loaiPhong);
            }
        }
        lp_adapter.searchDataList(searchlist);
    }

    @Override
    public void onResume() {
        super.onResume();
        GetListLP();

    }


}