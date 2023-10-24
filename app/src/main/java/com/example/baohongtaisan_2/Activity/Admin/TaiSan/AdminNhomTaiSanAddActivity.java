package com.example.baohongtaisan_2.Activity.Admin.TaiSan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baohongtaisan_2.Activity.Admin.Phong.AdminLoaiPhongAddActivity;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminNhomTaiSanAddActivity extends AppCompatActivity {
    private Button btnAdd, btnBack;
    private EditText txtTen;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nhom_tai_san_add);

        btnAdd = findViewById(R.id.btnThemNTS);
        txtTen = findViewById(R.id.txtTenNTS_add);
        btnBack = findViewById(R.id.btnQuaylaiNTS_add);

        pd = new ProgressDialog(AdminNhomTaiSanAddActivity.this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tennts = txtTen.getText().toString().trim();
                if (tennts.isEmpty()) {
                    txtTen.setError("Bạn chưa nhập tên nhóm tài sản.");
                    return;
                }
                uploadDataNTS(tennts);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void uploadDataNTS(String name) {
        pd.setTitle("Đang thêm dữ liệu...");
        pd.show();

        ApiServices.apiServices.add_nhomtaisan(name).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectadd = response.body();
                if (objectadd.getCode() == 1) {
                    Toast.makeText(AdminNhomTaiSanAddActivity.this, "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminNhomTaiSanAddActivity.this, objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminNhomTaiSanAddActivity.this, "Thêm mới thất bại !", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
