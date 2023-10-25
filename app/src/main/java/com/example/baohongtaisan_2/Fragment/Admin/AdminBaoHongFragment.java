package com.example.baohongtaisan_2.Fragment.Admin;

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

import com.example.baohongtaisan_2.Adapter.Admin.AdminBaoHongAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBaoHongFragment extends Fragment {

    private SearchView svBH;
    private RecyclerView rvBH;
    private LinearLayoutManager linearLayoutManager;
    private AdminBaoHongAdapter adminBaoHongAdapter;
    private List<BaoHong> baoHongList;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_bao_hong, container, false);
        _AnhXa();
        GetListBaoHong();
        return view;
    }


    public void _AnhXa() {
        rvBH = view.findViewById(R.id.rvBaohong);
        svBH = view.findViewById(R.id.txtSearchBH);

        linearLayoutManager = new LinearLayoutManager(requireContext());
        rvBH.setLayoutManager(linearLayoutManager);

        baoHongList = new ArrayList<>();
        adminBaoHongAdapter = new AdminBaoHongAdapter(baoHongList);
        rvBH.setAdapter(adminBaoHongAdapter);


        svBH.clearFocus();
        svBH.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<BaoHong> searchlist = new ArrayList<>();
                for (BaoHong baoHong : baoHongList) {
                    if (baoHongMatchesSearch(baoHong, s)) {
                        searchlist.add(baoHong);
                    }
                }
                adminBaoHongAdapter.searchDataList(searchlist);
                return false;
            }

            private boolean baoHongMatchesSearch(BaoHong baoHong, String query) {
                // Thay đổi logic tìm kiếm.
                String queryLower = query.toLowerCase();
                String tenP = baoHong.getTenP().toLowerCase();
                String tenTS = baoHong.getTenTS().toLowerCase();
                String ngay = baoHong.getNgayCapNhat().toLowerCase();

                // Sử dụng nhiều thuộc tính cho tìm kiếm phù hợp.
                if (tenP.contains(queryLower) || tenTS.contains(queryLower) || ngay.contains(queryLower)) {
                    return true;
                }
                return false;
            }
        });

    }



    public void GetListBaoHong() {
        ApiServices.apiServices.get_list_baohong().enqueue(new Callback<List<BaoHong>>() {
            @Override
            public void onResponse(Call<List<BaoHong>> call, Response<List<BaoHong>> response) {
                baoHongList.clear();
                baoHongList = response.body();
                if (baoHongList != null && getContext() != null) {
                    adminBaoHongAdapter = new AdminBaoHongAdapter(baoHongList);
                    rvBH.setAdapter(adminBaoHongAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<BaoHong>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        GetListBaoHong();

    }
    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }
}