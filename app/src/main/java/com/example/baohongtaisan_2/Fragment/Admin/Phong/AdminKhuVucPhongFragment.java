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

import com.example.baohongtaisan_2.Activity.Admin.Phong.AdminKhuVucPhongAddActivity;
import com.example.baohongtaisan_2.Adapter.Admin.AdminKhuVucPhongAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminKhuVucPhongFragment extends Fragment {

    private SearchView SVkvp;
    private RecyclerView rcvKVP;
    private AdminKhuVucPhongAdapter kvp_adapter;
    private List<KhuVucPhong> listKVP;
    private FirebaseDatabase db;
    private Button btnaddKvp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_admin_khu_vuc_phong, container, false);
        db = FirebaseDatabase.getInstance();
        initializeViews(rootView);

        setupRecyclerView();
        setupSearchViewListeners();
        setupButtonClickListeners();

        return rootView;
    }

    private void initializeViews(View rootView) {
        rcvKVP = rootView.findViewById(R.id.rvKhuvuc);

        SVkvp = rootView.findViewById(R.id.txtSearchKV);

        btnaddKvp = rootView.findViewById(R.id.btnkv_add);

    }

    private void setupButtonClickListeners() {

        btnaddKvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminKhuVucPhongAddActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManagerKVP = new LinearLayoutManager(requireContext());

        rcvKVP.setLayoutManager(linearLayoutManagerKVP);

        listKVP = new ArrayList<>();
        kvp_adapter = new AdminKhuVucPhongAdapter(listKVP);
        rcvKVP.setAdapter(kvp_adapter);

        GetListKVP();
    }

    private void setupSearchViewListeners() {
        SVkvp.clearFocus();
        SVkvp.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchKhuVucPhong(s);
                return false;
            }
        });

    }

    public void GetListKVP() {
        ApiServices.apiServices.get_list_khuvucphong().enqueue(new Callback<List<KhuVucPhong>>() {
            @Override
            public void onResponse(Call<List<KhuVucPhong>> call, Response<List<KhuVucPhong>> response) {
                listKVP.clear();
                listKVP = response.body();
                if (listKVP != null && getContext() != null) {
                    kvp_adapter = new AdminKhuVucPhongAdapter(listKVP);
                    rcvKVP.setAdapter(kvp_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<KhuVucPhong>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchKhuVucPhong(String text) {
        ArrayList<KhuVucPhong> searchlist = new ArrayList<>();
        for (KhuVucPhong khuVucPhong : listKVP) {
            if (khuVucPhong.getTenKVP().toLowerCase().contains(text.toLowerCase())) {
                searchlist.add(khuVucPhong);
            }
        }
        kvp_adapter.searchDataList(searchlist);
    }

    @Override
    public void onResume() {
        super.onResume();
        GetListKVP();

    }


}