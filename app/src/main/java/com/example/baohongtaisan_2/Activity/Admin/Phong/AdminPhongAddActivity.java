package com.example.baohongtaisan_2.Activity.Admin.Phong;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerKhuVucPhong_Adapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerLoaiPhong_Adapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPhongAddActivity extends AppCompatActivity {

    private Button btnAdd, btnBack;
    private EditText txtTen;
    private Spinner spinnerKVP, spinnerLP;
    private ProgressDialog pd;
    private List<KhuVucPhong> listKVP;
    private List<LoaiPhong> listLP;
    private int MaLP = -1, MaKVP = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_phong_add);

        btnAdd = findViewById(R.id.btnThemphong);
        txtTen = findViewById(R.id.txtTenPhong);
        spinnerKVP = findViewById(R.id.spnKVP);
        spinnerLP = findViewById(R.id.spnLP);
        btnBack = findViewById(R.id.btnQuaylaiphong);

        pd = new ProgressDialog(AdminPhongAddActivity.this);

        listKVP = new ArrayList<>();
        listLP = new ArrayList<>();

        spinnerKVP = findViewById(R.id.spnKVP);
        spinnerLP = findViewById(R.id.spnLP);

        loadDataForSpinner();

        setSpinnerListeners();
        setButtonClickListeners();
    }

    private void setSpinnerListeners() {
        spinnerLP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                handleLoaiPhongItemSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerKVP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                handleKhuVucPhongItemSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setButtonClickListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddButtonClick();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void handleLoaiPhongItemSelected(int position) {
        ArrayAdapter<LoaiPhong> adapter = (ArrayAdapter<LoaiPhong>) spinnerLP.getAdapter();
        LoaiPhong selected = adapter.getItem(position);
        if (selected != null) {
            MaLP = selected.getMaLP();
        }
    }

    private void handleKhuVucPhongItemSelected(int position) {
        ArrayAdapter<KhuVucPhong> adapter = (ArrayAdapter<KhuVucPhong>) spinnerKVP.getAdapter();
        KhuVucPhong selected = adapter.getItem(position);
        if (selected != null) {
            MaKVP = selected.getMaKVP();
        }
    }

    private void handleAddButtonClick() {
        String tenphong = txtTen.getText().toString().trim();
        if (tenphong.isEmpty()) {
            txtTen.setError("Bạn chưa nhập tên phòng.");
            return;
        }
        if (MaKVP == -1) {
            Toast.makeText(AdminPhongAddActivity.this, "Vui lòng chọn khu vực phòng.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (MaLP == -1) {
            Toast.makeText(AdminPhongAddActivity.this, "Vui lòng chọn loại phòng.", Toast.LENGTH_SHORT).show();
            return;
        }

        uploadDataPhong(tenphong, MaKVP, MaLP);
    }

    private void uploadDataPhong(String tenPhong, int MaKVP, int MaLP) {
        pd.setTitle("Đang thêm dữ liệu...");
        pd.show();
        ApiServices.apiServices.add_phong(tenPhong, MaKVP, MaLP).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectadd = response.body();
                if (objectadd.getCode() == 1) {
                    Toast.makeText(AdminPhongAddActivity.this, "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminPhongAddActivity.this, objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminPhongAddActivity.this, "Thêm mới thất bại !", Toast.LENGTH_SHORT).show();
            }
        });
        pd.dismiss();
    }

    private void loadDataForSpinner() {
        loadKVPData();
        loadLPData();
    }

    private void loadKVPData() {
        ApiServices.apiServices.get_list_khuvucphong().enqueue(new Callback<List<KhuVucPhong>>() {
            @Override
            public void onResponse(Call<List<KhuVucPhong>> call, Response<List<KhuVucPhong>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listKVP.clear();
                    listKVP = response.body();
                    SpinnerKhuVucPhong_Adapter khuVucPhong_adapter = new SpinnerKhuVucPhong_Adapter(AdminPhongAddActivity.this, R.layout.custom_spinner_selected, listKVP);
                    spinnerKVP.setAdapter(khuVucPhong_adapter);
                } else {
                    Toast.makeText(AdminPhongAddActivity.this, "Có lỗi xảy ra khi load tên khu vực phòng ...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<KhuVucPhong>> call, Throwable t) {
                Toast.makeText(AdminPhongAddActivity.this, "ERROR...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadLPData() {
        ApiServices.apiServices.get_list_loaiphong().enqueue(new Callback<List<LoaiPhong>>() {
            @Override
            public void onResponse(Call<List<LoaiPhong>> call, Response<List<LoaiPhong>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listLP.clear();
                    listLP = response.body();
                    SpinnerLoaiPhong_Adapter loaiPhong_adapter = new SpinnerLoaiPhong_Adapter(AdminPhongAddActivity.this, R.layout.custom_spinner_selected, listLP);
                    spinnerLP.setAdapter(loaiPhong_adapter);
                } else {
                    Toast.makeText(AdminPhongAddActivity.this, "Có lỗi xảy ra khi load tên loại phòng...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LoaiPhong>> call, Throwable t) {
                Toast.makeText(AdminPhongAddActivity.this, "ERROR...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}