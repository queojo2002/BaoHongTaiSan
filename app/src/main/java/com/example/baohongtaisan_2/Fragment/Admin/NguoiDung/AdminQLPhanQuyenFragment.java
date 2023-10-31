package com.example.baohongtaisan_2.Fragment.Admin.NguoiDung;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.Admin.AdminDonViAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.AdminPhanQuyenAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.DonVi;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.PhanQuyen;
import com.example.baohongtaisan_2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminQLPhanQuyenFragment extends Fragment {

    private androidx.appcompat.widget.SearchView sv;
    private RecyclerView rcv;
    private AdminPhanQuyenAdapter adminPhanQuyenAdapter;
    private List<PhanQuyen> phanQuyenLists;
    private FloatingActionButton btnADD;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin_q_l_phan_quyen, container, false);
        _AnhXa();
        GetListPhanQuyen();
        _SuKien();

        return rootView;
    }

    private void _AnhXa() {
        rcv = rootView.findViewById(R.id.rvPhanquyen);
        sv = rootView.findViewById(R.id.svPQ);
        btnADD = rootView.findViewById(R.id.btnaddPQ);
        LinearLayoutManager linearLayoutManagerDV = new LinearLayoutManager(requireContext());
        rcv.setLayoutManager(linearLayoutManagerDV);
        phanQuyenLists = new ArrayList<>();
    }

    private void _SuKien() {
        sv.clearFocus();
        sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s == null || phanQuyenLists == null) return false;
                ArrayList<PhanQuyen> searchlist = new ArrayList<>();
                for (PhanQuyen phanQuyen : phanQuyenLists) {
                    if (phanQuyen.getTenPQ().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(phanQuyen);
                    }
                }
                if (adminPhanQuyenAdapter != null) {
                    adminPhanQuyenAdapter.searchDataList(searchlist);
                }

                return false;
            }
        });

        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Add();
            }
        });

        rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) btnADD.hide();
                else btnADD.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }



    private void GetListPhanQuyen() {
        ApiServices.apiServices.get_list_phanquyen().enqueue(new Callback<List<PhanQuyen>>() {
            @Override
            public void onResponse(Call<List<PhanQuyen>> call, Response<List<PhanQuyen>> response) {
                phanQuyenLists.clear();
                phanQuyenLists = response.body();
                if (phanQuyenLists != null && getContext() != null) {
                    adminPhanQuyenAdapter = new AdminPhanQuyenAdapter(phanQuyenLists, new RCVClickItem() {
                        @Override
                        public void onClickRCV(Object object, String CRUD) {
                            PhanQuyen phanQuyen = (PhanQuyen) object;
                            if (CRUD.equals("EDIT")) {
                                Open_Dialog_Edit(phanQuyen);
                            } else if (CRUD.equals("DELETE")) {
                                Open_Dialog_Delete(phanQuyen);
                            }
                        }
                    });
                    rcv.setAdapter(adminPhanQuyenAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<PhanQuyen>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Open_Dialog_Add() {
    }

    private void Open_Dialog_Edit(PhanQuyen phanQuyen) {
    }

    private void Open_Dialog_Delete(PhanQuyen phanQuyen) {
    }






    @Override
    public void onResume() {
        super.onResume();
        GetListPhanQuyen();
    }
}