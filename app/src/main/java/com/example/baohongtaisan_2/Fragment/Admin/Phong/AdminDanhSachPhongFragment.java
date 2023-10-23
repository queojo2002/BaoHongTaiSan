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

import com.example.baohongtaisan_2.Activity.Admin.Phong.AdminPhongAddActivity;
import com.example.baohongtaisan_2.Adapter.Admin.AdminPhongAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminDanhSachPhongFragment extends Fragment {

    private SearchView svphong;
    private RecyclerView rvPhong;
    private LinearLayoutManager linearLayoutManager;
    private AdminPhongAdapter adminPhongAdapter;
    private List<Phong> phongList;
    private Button btnaddPhong;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_danh_sach_phong, container, false);
        _AnhXa();
        _SuKien();
        GetListPhong();
        return view;
    }


    public void _AnhXa() {
        rvPhong = view.findViewById(R.id.rvPhong);
        svphong = view.findViewById(R.id.txtSearchPhong);
        btnaddPhong = view.findViewById(R.id.btnphong_add);
        linearLayoutManager = new LinearLayoutManager(requireContext());
        rvPhong.setLayoutManager(linearLayoutManager);

        phongList = new ArrayList<>();
        adminPhongAdapter = new AdminPhongAdapter(phongList);
        rvPhong.setAdapter(adminPhongAdapter);

        svphong.clearFocus();
        svphong.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Phong> searchlist = new ArrayList<>();
                for (Phong phong : phongList) {
                    if (phong.getTenP().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(phong);
                    }
                }
                adminPhongAdapter.searchDataList(searchlist);
                return false;
            }
        });

    }

    public void _SuKien() {
        btnaddPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminPhongAddActivity.class);
                startActivity(intent);
            }
        });
    }


    public void GetListPhong() {
        ApiServices.apiServices.get_list_phong().enqueue(new Callback<List<Phong>>() {
            @Override
            public void onResponse(Call<List<Phong>> call, Response<List<Phong>> response) {
                phongList.clear();
                phongList = response.body();
                if (phongList != null && getContext() != null) {
                    adminPhongAdapter = new AdminPhongAdapter(phongList);
                    rvPhong.setAdapter(adminPhongAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Phong>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        GetListPhong();

    }

}