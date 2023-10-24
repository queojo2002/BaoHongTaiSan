package com.example.baohongtaisan_2.Fragment.Admin.TaiSan;

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

import com.example.baohongtaisan_2.Adapter.Admin.AdminLoaiTaiSanAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.AdminNguoiDungAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.AdminTaiSanAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.LoaiTaiSan;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.Model.TaiSan;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminDanhSachTaiSanFragment extends Fragment {


    private SearchView sv;
    private RecyclerView rcv;
    private AdminTaiSanAdapter taiSanAdapter;
    private List<TaiSan> taiSanList;
    private Button btnAddTaiSan;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_danh_sach_tai_san, container, false);
        _AnhXa();
        _SuKien();
        return view;

    }

    public void _AnhXa()
    {
        taiSanList = new ArrayList<>();
        rcv = view.findViewById(R.id.rvTaiSan);
        sv = view.findViewById(R.id.txtSearchTS);
        btnAddTaiSan = view.findViewById(R.id.btnts_add);
        LinearLayoutManager linearLayoutManagerND = new LinearLayoutManager(requireContext());
        rcv.setLayoutManager(linearLayoutManagerND);
        taiSanAdapter = new AdminTaiSanAdapter(taiSanList);
        rcv.setAdapter(taiSanAdapter);
        GetListTaiSan();

    }

    public void _SuKien()
    {
        sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<TaiSan> searchlist = new ArrayList<>();
                for (TaiSan taiSan : taiSanList) {
                    if (taiSan.getTenTS().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(taiSan);
                    }
                }
                taiSanAdapter.searchDataList(searchlist);
                return false;
            }
        });
    }
    public void GetListTaiSan() {
        ApiServices.apiServices.get_list_taisan().enqueue(new Callback<List<TaiSan>>() {
            @Override
            public void onResponse(Call<List<TaiSan>> call, Response<List<TaiSan>> response) {
                taiSanList.clear();
                taiSanList = response.body();
                if (taiSanList != null && getContext() != null) {
                    taiSanAdapter = new AdminTaiSanAdapter(taiSanList);
                    rcv.setAdapter(taiSanAdapter);
                }

            }
            @Override
            public void onFailure(Call<List<TaiSan>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }



}