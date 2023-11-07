package com.example.baohongtaisan_2.Fragment.Admin.PhanBo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.Admin.AdminDSPBTSAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.PhanBo;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminDSTSTrongPhongFragment extends Fragment {


    private final int MaP;
    private final String TenP;
    private View view;
    private RecyclerView rv;
    private androidx.appcompat.widget.SearchView sv;
    private List<PhanBo> phanBoList;
    private AdminDSPBTSAdapter dspbtsAdapter;

    public AdminDSTSTrongPhongFragment(int MaP, String TenP) {
        this.MaP = MaP;
        this.TenP = TenP;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_phan_bo, container, false);
        _AnhXa();
        _SuKien();

        return view;
    }


    private void _AnhXa() {
        rv = view.findViewById(R.id.rvPBTS_TaiSan_2);
        sv = view.findViewById(R.id.svTaiSan_2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rv.setLayoutManager(linearLayoutManager);
        phanBoList = new ArrayList<>();

        GetListPhanBoTrongPhong();
    }


    public void _SuKien() {
        sv.clearFocus();
        sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<PhanBo> searchlist = new ArrayList<>();
                for (PhanBo phanBo : phanBoList) {
                    if (phanBo.getTenTS().toLowerCase().contains(s.toLowerCase()) || phanBo.getTenLTS().toLowerCase().contains(s.toLowerCase()) || phanBo.getTenNTS().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(phanBo);
                    }
                }
                dspbtsAdapter.searchDataList(searchlist);
                return false;
            }
        });


    }

    private void GetListPhanBoTrongPhong() {
        ApiServices.apiServices.get_list_phanbo_byMaP(MaP).enqueue(new Callback<List<PhanBo>>() {
            @Override
            public void onResponse(Call<List<PhanBo>> call, Response<List<PhanBo>> response) {
                phanBoList.clear();
                phanBoList = response.body();
                if (phanBoList == null) return;
                if (phanBoList.size() != 0) {
                    dspbtsAdapter = new AdminDSPBTSAdapter(phanBoList);
                    rv.setAdapter(dspbtsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<PhanBo>> call, Throwable t) {
                Toast.makeText(requireContext(), "Có lỗi khi lấy dữ liệu tài sản cho phòng này !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        GetListPhanBoTrongPhong();
    }
}