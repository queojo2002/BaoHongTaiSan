package com.example.baohongtaisan_2.Fragment.Admin.Phong;

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

import com.example.baohongtaisan_2.Adapter.Admin.AdminLoaiPhongAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
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
                Open_Dialog_Add();
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

    public void Open_Dialog_Add() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_edit);

        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtt = window.getAttributes();
        windowAtt.gravity = Gravity.CENTER;
        window.setAttributes(windowAtt);
        dialog.setCancelable(true);

        TextView tv = dialog.findViewById(R.id.tvTenChucNangEdit);
        EditText txtinput = dialog.findViewById(R.id.txtInput);
        Button btnhuybo =dialog.findViewById(R.id.btnHuyBo);
        Button btnthemmoi = dialog.findViewById(R.id.btnEdit);

        tv.setText("Thêm mới loại phòng");
        btnthemmoi.setText("Thêm mới");

        btnthemmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.add_loaiphong(txtinput.getText().toString()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(getContext(), "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            GetListLP();
                        } else {
                            Toast.makeText(getContext(), objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Thêm mới thất bại !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnhuybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }



}