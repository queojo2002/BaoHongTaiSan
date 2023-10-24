package com.example.baohongtaisan_2.Activity.Admin.TaiSan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoaiTaiSanAddActivity extends AppCompatActivity {

    private EditText txtTen;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_loai_tai_san_add);

        Button btnAdd = findViewById(R.id.btnThemLTS);
        txtTen = findViewById(R.id.txtTenLTS_add);
        Button btnBack = findViewById(R.id.btnQuaylaiLTS_add);

        pd = new ProgressDialog(AdminLoaiTaiSanAddActivity.this);


        btnAdd.setOnClickListener(v -> {
            String tenlts = txtTen.getText().toString().trim();
            if (tenlts.isEmpty()) {
                txtTen.setError("Bạn chưa nhập tên loại tài sản.");
                return;
            }
            uploadDataLTS(tenlts);
        });

        btnBack.setOnClickListener(view -> finish());

    }

    private void uploadDataLTS(String name) {
        pd.setTitle("Đang thêm dữ liệu...");
        pd.show();

        ApiServices.apiServices.add_data_loaitaisan(name).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectadd = response.body();
                if (objectadd.getCode() == 1) {
                    Toast.makeText(AdminLoaiTaiSanAddActivity.this, "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminLoaiTaiSanAddActivity.this, objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminLoaiTaiSanAddActivity.this, "Thêm mới thất bại !", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
