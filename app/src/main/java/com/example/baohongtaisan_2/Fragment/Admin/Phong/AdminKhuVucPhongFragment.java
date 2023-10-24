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

import com.example.baohongtaisan_2.Adapter.Admin.AdminKhuVucPhongAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_khu_vuc_phong, container, false);
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
                Open_Dialog_Add();
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

        tv.setText("Thêm mới khu vực phòng");
        btnthemmoi.setText("Thêm mới");

        btnthemmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.add_khuvucphong(txtinput.getText().toString()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(getContext(), "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            GetListKVP();
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