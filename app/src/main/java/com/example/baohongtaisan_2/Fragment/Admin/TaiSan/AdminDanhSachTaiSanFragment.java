package com.example.baohongtaisan_2.Fragment.Admin.TaiSan;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.Admin.AdminTaiSanAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerLoaiTaiSan_Adapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerNhomTaiSan_Adapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.LoaiTaiSan;
import com.example.baohongtaisan_2.Model.NhomTaiSan;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.TaiSan;
import com.example.baohongtaisan_2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminDanhSachTaiSanFragment extends Fragment {


    private androidx.appcompat.widget.SearchView sv;
    private RecyclerView rcv;
    private AdminTaiSanAdapter taiSanAdapter;
    private List<TaiSan> taiSanList;
    private List<NhomTaiSan> nhomTaiSanList;
    private List<LoaiTaiSan> loaiTaiSanList;

    private FloatingActionButton btnAddTaiSan;
    private View view;
    private int MaNTS_Add = -1, MaLTS_Add = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_danh_sach_tai_san, container, false);
        _AnhXa();
        _SuKien();
        return view;

    }

    public void _AnhXa() {
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

    public void _SuKien() {
        sv.clearFocus();
        sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
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

        btnAddTaiSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Add();
            }
        });

        rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    btnAddTaiSan.hide();
                }else {
                    btnAddTaiSan.show();
                }
                super.onScrolled(recyclerView, dx, dy);
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

    public void Open_Dialog_Add() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_taisan_add);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtt = window.getAttributes();
        windowAtt.gravity = Gravity.CENTER;
        window.setAttributes(windowAtt);
        dialog.setCancelable(true);

        EditText txtinput = dialog.findViewById(R.id.txtAdd_Input);
        EditText txtgiatri = dialog.findViewById(R.id.txtAdd_GiaTri);
        EditText txtsoluong = dialog.findViewById(R.id.txtAdd_SoLuong);
        EditText txthangsx = dialog.findViewById(R.id.txtAdd_HangSX);
        EditText txtnuocsx = dialog.findViewById(R.id.txtAdd_NuocSX);
        EditText txtnamsx = dialog.findViewById(R.id.txtAdd_NamSX);
        EditText txtghichu = dialog.findViewById(R.id.txtAdd_GhiChu);


        Button btnhuybo = dialog.findViewById(R.id.btnAdd_HuyBo);
        Button btnthem = dialog.findViewById(R.id.btnAdd);
        Spinner spnNTS = dialog.findViewById(R.id.spnAdd_1);
        Spinner spnLTS = dialog.findViewById(R.id.spnAdd_2);

        Load_Data_NhomTS(spnNTS, -1);
        Load_Data_LoaiTS(spnLTS, -1);


        spnNTS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<NhomTaiSan> adapter = (ArrayAdapter<NhomTaiSan>) spnNTS.getAdapter();
                NhomTaiSan selected = adapter.getItem(i);
                if (selected != null) {
                    MaNTS_Add = selected.getMaNTS();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spnLTS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<LoaiTaiSan> adapter = (ArrayAdapter<LoaiTaiSan>) spnLTS.getAdapter();
                LoaiTaiSan selected = adapter.getItem(i);
                if (selected != null) {
                    MaLTS_Add = selected.getMaLTS();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MaNTS_Add == -1 || MaLTS_Add == -1 ) return;
                int giatriValue, soluongValue, namsxValue;

                try {
                    giatriValue = Integer.parseInt(txtgiatri.getText().toString());
                    soluongValue = Integer.parseInt(txtsoluong.getText().toString());
                    namsxValue = Integer.parseInt(txtnamsx.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Chưa nhập đủ thông tin cần thiết.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ApiServices.apiServices.add_data_taisan(txtinput.getText().toString(), MaNTS_Add, MaLTS_Add, giatriValue,
                        soluongValue, txthangsx.getText().toString(), txtnuocsx.getText().toString(),
                        namsxValue, txtghichu.getText().toString()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(getContext(), "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            GetListTaiSan();
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

    private void Load_Data_NhomTS(Spinner spinner, int MaNTS) {
        ApiServices.apiServices.get_list_nhomtaisan().enqueue(new Callback<List<NhomTaiSan>>() {
            @Override
            public void onResponse(@NonNull Call<List<NhomTaiSan>> call, @NonNull Response<List<NhomTaiSan>> response) {
                if (response.isSuccessful()) {
                    nhomTaiSanList = response.body();
                    SpinnerNhomTaiSan_Adapter spinnerNhomTaiSanAdapter = new SpinnerNhomTaiSan_Adapter(getContext(), R.layout.custom_spinner_selected, nhomTaiSanList);
                    spinner.setAdapter(spinnerNhomTaiSanAdapter);
                    if (MaNTS != -1) {
                        for (int i = 0; i < nhomTaiSanList.size(); i++) {
                            if (nhomTaiSanList.get(i).getMaNTS() == MaNTS) {
                                spinner.setSelection(i);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<NhomTaiSan>> call, @NonNull Throwable t) {

            }
        });
    }

    private void Load_Data_LoaiTS(Spinner spinner, int MaLTS) {
        ApiServices.apiServices.get_list_loaitaisan().enqueue(new Callback<List<LoaiTaiSan>>() {
            @Override
            public void onResponse(@NonNull Call<List<LoaiTaiSan>> call, @NonNull Response<List<LoaiTaiSan>> response) {
                if (response.isSuccessful()) {
                    loaiTaiSanList = response.body();
                    SpinnerLoaiTaiSan_Adapter spinner_2 = new SpinnerLoaiTaiSan_Adapter(getContext(), R.layout.custom_spinner_selected, loaiTaiSanList);
                    spinner.setAdapter(spinner_2);
                    if (MaLTS != -1) {
                        for (int i = 0; i < loaiTaiSanList.size(); i++) {
                            if (loaiTaiSanList.get(i).getMaLTS() == MaLTS) {
                                spinner.setSelection(i);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LoaiTaiSan>> call, @NonNull Throwable t) {

            }
        });
    }


}