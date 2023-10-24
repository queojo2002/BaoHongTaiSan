package com.example.baohongtaisan_2.Fragment.Admin.TaiSan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Activity.Admin.TaiSan.AdminNhomTaiSanAddActivity;
import com.example.baohongtaisan_2.Adapter.Admin.AdminNhomTaiSanAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.NhomTaiSan;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AdminQLNhomTaiSanFragment extends Fragment {

    private SearchView SVnts;
    private RecyclerView rcvNTS;
    private AdminNhomTaiSanAdapter nts_adapter;
    private List<NhomTaiSan> listNTS;
    private Button btnaddnts;


    private void initializeViews(View rootView) {
        rcvNTS = rootView.findViewById(R.id.rvNhomTS);

        SVnts = rootView.findViewById(R.id.txtSearchNhomTS);

        btnaddnts = rootView.findViewById(R.id.btnnhomts_add);

    }

    private void setupButtonClickListeners() {

        btnaddnts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminNhomTaiSanAddActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rcvNTS.setLayoutManager(linearLayoutManager);

        listNTS = new ArrayList<>();
        nts_adapter = new AdminNhomTaiSanAdapter(listNTS);
        rcvNTS.setAdapter(nts_adapter);

        GetListNTS();
    }

    private void setupSearchViewListeners() {
        SVnts.clearFocus();
        SVnts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchNTS(s);
                return false;
            }
        });

    }

    public void GetListNTS() {
        ApiServices.apiServices.get_list_nhomtaisan().enqueue(new Callback<List<NhomTaiSan>>() {
            @Override
            public void onResponse(Call<List<NhomTaiSan>> call, Response<List<NhomTaiSan>> response) {
                listNTS.clear();
                listNTS = response.body();
                if (listNTS != null && getContext() != null) {
                    nts_adapter = new AdminNhomTaiSanAdapter(listNTS);
                    rcvNTS.setAdapter(nts_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<NhomTaiSan>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchNTS(String text) {
        ArrayList<NhomTaiSan> searchlist = new ArrayList<>();
        for (NhomTaiSan nhomTaiSan : listNTS) {
            if (nhomTaiSan.getTenNTS().toLowerCase().contains(text.toLowerCase())) {
                searchlist.add(nhomTaiSan);
            }
        }
        nts_adapter.searchDataList(searchlist);
    }

    @Override
    public void onResume() {
        super.onResume();
        GetListNTS();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_q_l_nhom_tai_san, container, false);
        initializeViews(rootView);

        setupRecyclerView();
        setupSearchViewListeners();
        setupButtonClickListeners();

        return rootView;
    }
}