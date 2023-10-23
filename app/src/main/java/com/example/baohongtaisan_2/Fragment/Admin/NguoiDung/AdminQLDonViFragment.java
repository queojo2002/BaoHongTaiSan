package com.example.baohongtaisan_2.Fragment.Admin.NguoiDung;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.baohongtaisan_2.Activity.Admin.NguoiDung.AdminDonViAddActivity;
import com.example.baohongtaisan_2.Adapter.Admin.AdminDonViAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.DonVi;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminQLDonViFragment extends Fragment {

    private SearchView SVdv;
    private RecyclerView rcvDV;
    private AdminDonViAdapter adminDonVi_adapter;
    private List<DonVi> listDV;
    private Button btnaddDv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_q_l_don_vi, container, false);
        initializeViews(rootView);
        setupRecyclerView();

        setupSearchViewListeners();
        setupButtonClickListeners();

        return rootView;
    }

    private void initializeViews(View rootView) {
        rcvDV = rootView.findViewById(R.id.rvDonvi);

        SVdv = rootView.findViewById(R.id.txtSearchDV);

        btnaddDv = rootView.findViewById(R.id.btndv_add);
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManagerDV = new LinearLayoutManager(requireContext());

        rcvDV.setLayoutManager(linearLayoutManagerDV);

        listDV = new ArrayList<>();
        adminDonVi_adapter = new AdminDonViAdapter(listDV);
        rcvDV.setAdapter(adminDonVi_adapter);
        GetListDonVi();
    }

    private void setupSearchViewListeners() {
        SVdv.clearFocus();
        SVdv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchDonVi(s);
                return false;
            }
        });
    }

    private void setupButtonClickListeners() {
        btnaddDv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminDonViAddActivity.class);
                startActivity(intent);
            }
        });
    }

    public void GetListDonVi() {
        ApiServices.apiServices.get_list_donvi().enqueue(new Callback<List<DonVi>>() {
            @Override
            public void onResponse(Call<List<DonVi>> call, Response<List<DonVi>> response) {
                listDV.clear();
                listDV = response.body();
                if (listDV != null && getContext() != null) {
                    adminDonVi_adapter = new AdminDonViAdapter(listDV);
                    rcvDV.setAdapter(adminDonVi_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<DonVi>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchDonVi(String text) {
        ArrayList<DonVi> searchlist = new ArrayList<>();
        for (DonVi donVi : listDV) {
            if (donVi.getTenDV().toLowerCase().contains(text.toLowerCase())) {
                searchlist.add(donVi);
            }
        }
        adminDonVi_adapter.searchDataList(searchlist);
    }

    @Override
    public void onResume() {
        super.onResume();
        GetListDonVi();

    }
}