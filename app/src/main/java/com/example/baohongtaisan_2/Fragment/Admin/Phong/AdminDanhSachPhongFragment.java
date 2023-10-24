package com.example.baohongtaisan_2.Fragment.Admin.Phong;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.Admin.AdminPhongAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerKhuVucPhong_Adapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerLoaiPhong_Adapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
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
    private List<KhuVucPhong> khuVucPhongList;
    private List<LoaiPhong> loaiPhongList;
    private List<Phong> phongList;
    private Button btnaddPhong;
    private View view;
    private int MaLP_Add = -1, MaKVP_Add = -1;


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
                Open_Dialog_Add();
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


    public void Open_Dialog_Add() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_add);

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

        TextView tv = dialog.findViewById(R.id.tvAdd_TenChucNangEdit);
        EditText txtinput = dialog.findViewById(R.id.txtAdd_Input);
        Button btnhuybo = dialog.findViewById(R.id.btnAdd_HuyBo);
        Button btnthem = dialog.findViewById(R.id.btnAdd);
        Spinner spnLP = dialog.findViewById(R.id.spnAdd_1);
        Spinner spnKVP = dialog.findViewById(R.id.spnAdd_2);
        btnthem.setText("Thêm mới");
        tv.setText("Thêm mới Phòng");
        Load_Data_LoaiPhong(spnLP);
        Load_Data_KhuVucPhong(spnKVP);
        txtinput.setHint("Nhập tên Phòng");


        spnLP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<LoaiPhong> adapter = (ArrayAdapter<LoaiPhong>) spnLP.getAdapter();
                LoaiPhong selected = adapter.getItem(i);
                if (selected != null) {
                    MaLP_Add = selected.getMaLP();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spnKVP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<KhuVucPhong> adapter = (ArrayAdapter<KhuVucPhong>) spnKVP.getAdapter();
                KhuVucPhong selected = adapter.getItem(i);
                if (selected != null) {
                    MaKVP_Add = selected.getMaKVP();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MaLP_Add == -1 || MaKVP_Add == -1) return;
                ApiServices.apiServices.add_phong(txtinput.getText().toString(), MaKVP_Add, MaLP_Add).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(getContext(), "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            GetListPhong();
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



    private void Load_Data_LoaiPhong(Spinner spinner) {
        ApiServices.apiServices.get_list_loaiphong().enqueue(new Callback<List<LoaiPhong>>() {
            @Override
            public void onResponse(@NonNull Call<List<LoaiPhong>> call, @NonNull Response<List<LoaiPhong>> response) {
                if (response.isSuccessful())
                {
                    loaiPhongList = response.body();
                    SpinnerLoaiPhong_Adapter spinnerLoaiPhongAdapter = new SpinnerLoaiPhong_Adapter(getContext(), R.layout.custom_spinner_selected, loaiPhongList);
                    spinner.setAdapter(spinnerLoaiPhongAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<LoaiPhong>> call, @NonNull Throwable t) {

            }
        });
    }

    private void Load_Data_KhuVucPhong(Spinner spinner) {
        ApiServices.apiServices.get_list_khuvucphong().enqueue(new Callback<List<KhuVucPhong>>() {
            @Override
            public void onResponse(@NonNull Call<List<KhuVucPhong>> call, @NonNull Response<List<KhuVucPhong>> response) {
                if (response.isSuccessful())
                {
                    khuVucPhongList = response.body();
                    SpinnerKhuVucPhong_Adapter spinner_2 = new SpinnerKhuVucPhong_Adapter(getContext(), R.layout.custom_spinner_selected, khuVucPhongList);
                    spinner.setAdapter(spinner_2);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<KhuVucPhong>> call, @NonNull Throwable t) {

            }
        });
    }



}