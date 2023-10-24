package com.example.baohongtaisan_2.Activity.Admin.NguoiDung;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baohongtaisan_2.Activity.Admin.Phong.AdminPhongAddActivity;
import com.example.baohongtaisan_2.Activity.Admin.Phong.AdminPhongEditActivity;
import com.example.baohongtaisan_2.Activity.User.BaoHongActivity;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerChucDanhAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerDonViAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerKhuVucPhong_Adapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerPhanQuyenAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ChucDanh;
import com.example.baohongtaisan_2.Model.DonVi;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.PhanBo;
import com.example.baohongtaisan_2.Model.PhanQuyen;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;
import com.google.protobuf.Api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminNguoiDungEditActivity extends AppCompatActivity {


    private Button btnSuaND;
    private TextView txtHoVaTen;
    private Spinner spinnerDV, spinnerCD, spinnerPQ;
    private List<DonVi> donViList;
    private List<ChucDanh> chucDanhList;
    private List<PhanQuyen> phanQuyenList;
    private SpinnerDonViAdapter spinnerDonViAdapter;
    private SpinnerChucDanhAdapter spinnerChucDanhAdapter;
    private SpinnerPhanQuyenAdapter spinnerPhanQuyenAdapter;
    private Intent intent;
    private int MaND = -1, MaDV = -1, MaCD = -1, MaPQ = -1;
    private String HoVaTen = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nguoi_dung_edit);
        _AnhXa();
        Get_Data_Intent();
        Load_Data_DonVi();
        Load_Data_ChucDanh();
        Load_Data_PhanQuyen();
        _SuKien();
    }



    public void _AnhXa() {
        intent = getIntent();
        txtHoVaTen = findViewById(R.id.txtEditND_HoVaTen);
        spinnerDV = findViewById(R.id.spnEditND_DV);
        spinnerCD = findViewById(R.id.spnEditND_CD);
        spinnerPQ = findViewById(R.id.spnEditND_PQ);
        btnSuaND = findViewById(R.id.btnEditND);
        donViList = new ArrayList<>();
        chucDanhList = new ArrayList<>();
        phanQuyenList = new ArrayList<>();

    }

    public void _SuKien() {
        spinnerDV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<DonVi> adapter = (ArrayAdapter<DonVi>) spinnerDV.getAdapter();
                DonVi selected = adapter.getItem(i);
                if (selected != null) {
                    MaDV = selected.getMaDV();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spinnerCD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<ChucDanh> adapter = (ArrayAdapter<ChucDanh>) spinnerCD.getAdapter();
                ChucDanh selected = adapter.getItem(i);
                if (selected != null) {
                    MaCD = selected.getMaCD();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spinnerPQ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<PhanQuyen> adapter = (ArrayAdapter<PhanQuyen>) spinnerPQ.getAdapter();
                PhanQuyen selected = adapter.getItem(i);
                if (selected != null) {
                    MaPQ = selected.getMaPQ();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btnSuaND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_NguoiDung();
            }
        });
    }



    public void Edit_NguoiDung()
    {
        if (MaND == -1 || MaCD == -1 || MaDV == -1 || MaPQ == -1 || TextUtils.isEmpty(txtHoVaTen.getText().toString()))
        {
            Toast.makeText(AdminNguoiDungEditActivity.this, "Dữ liệu sửa người dùng của bạn không đúng !!!", Toast.LENGTH_SHORT).show();
            return;
        }else
        {
            ApiServices.apiServices.edit_data_nguoidung(MaND, txtHoVaTen.getText().toString(), MaDV, MaCD, MaPQ).enqueue(new Callback<ObjectReponse>() {
                @Override
                public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                    ObjectReponse objectEdit = response.body();
                    if (objectEdit.getCode() == 1) {
                        Toast.makeText(AdminNguoiDungEditActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AdminNguoiDungEditActivity.this, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ObjectReponse> call, Throwable t) {
                    Toast.makeText(AdminNguoiDungEditActivity.this, "Có lỗi khi cập nhật người dùng!!!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public void Get_Data_Intent() {
        Bundle nd_data = intent.getBundleExtra("DataEditNguoiDung");
        MaND = nd_data.getInt("MaND");
        MaDV = nd_data.getInt("MaDV");
        MaCD = nd_data.getInt("MaCD");
        MaPQ = nd_data.getInt("MaPQ");
        HoVaTen = nd_data.getString("HoVaTen");
        txtHoVaTen.setText(HoVaTen);
    }

    private void Load_Data_DonVi() {
        ApiServices.apiServices.get_list_donvi().enqueue(new Callback<List<DonVi>>() {
            @Override
            public void onResponse(Call<List<DonVi>> call, Response<List<DonVi>> response) {
                if (response.isSuccessful())
                {
                    donViList = response.body();
                    spinnerDonViAdapter = new SpinnerDonViAdapter(AdminNguoiDungEditActivity.this, R.layout.custom_spinner_selected, donViList);
                    spinnerDV.setAdapter(spinnerDonViAdapter);
                    for (int i = 0; i < donViList.size(); i++) {
                        if (donViList.get(i).getMaDV() == MaDV)
                        {
                            spinnerDV.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DonVi>> call, Throwable t) {

            }
        });
    }

    private void Load_Data_ChucDanh() {
        ApiServices.apiServices.get_list_chucdanh().enqueue(new Callback<List<ChucDanh>>() {
            @Override
            public void onResponse(Call<List<ChucDanh>> call, Response<List<ChucDanh>> response) {
                if (response.isSuccessful())
                {
                    chucDanhList = response.body();
                    spinnerChucDanhAdapter = new SpinnerChucDanhAdapter(AdminNguoiDungEditActivity.this, R.layout.custom_spinner_selected, chucDanhList);
                    spinnerCD.setAdapter(spinnerChucDanhAdapter);
                    for (int i = 0; i < chucDanhList.size(); i++) {
                        if (chucDanhList.get(i).getMaCD() == MaCD)
                        {
                            spinnerCD.setSelection(i);
                            break;
                        }
                    }

                }
            }
            @Override
            public void onFailure(Call<List<ChucDanh>> call, Throwable t) {

            }
        });
    }


    private void Load_Data_PhanQuyen() {
        ApiServices.apiServices.get_list_phanquyen().enqueue(new Callback<List<PhanQuyen>>() {
            @Override
            public void onResponse(Call<List<PhanQuyen>> call, Response<List<PhanQuyen>> response) {
                if (response.isSuccessful())
                {
                    phanQuyenList = response.body();
                    spinnerPhanQuyenAdapter = new SpinnerPhanQuyenAdapter(AdminNguoiDungEditActivity.this, R.layout.custom_spinner_selected, phanQuyenList);
                    spinnerPQ.setAdapter(spinnerPhanQuyenAdapter);
                    for (int i = 0; i < phanQuyenList.size(); i++) {
                        if (phanQuyenList.get(i).getMaPQ() == MaPQ)
                        {
                            spinnerPQ.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PhanQuyen>> call, Throwable t) {

            }
        });
    }


}