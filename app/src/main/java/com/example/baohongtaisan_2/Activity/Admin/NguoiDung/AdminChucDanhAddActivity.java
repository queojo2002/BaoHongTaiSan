package com.example.baohongtaisan_2.Activity.Admin.NguoiDung;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminChucDanhAddActivity extends AppCompatActivity {

    private EditText txtTen, txtMoTa;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chuc_danh_add);

        Button btnAdd = findViewById(R.id.btnThemcd);
        txtTen = findViewById(R.id.txtTenCD);
        txtMoTa = findViewById(R.id.txtMotaCD);
        Button btnBack = findViewById(R.id.btnQuaylaicd);

        pd = new ProgressDialog(AdminChucDanhAddActivity.this);

        btnAdd.setOnClickListener(v -> {
            String tencd = txtTen.getText().toString().trim();
            String motacd = txtMoTa.getText().toString().trim();
            if (tencd.isEmpty()) {
                txtTen.setError("Bạn chưa nhập tên đơn vị.");
                return;
            }
            if (motacd.isEmpty()) {
                txtMoTa.setError("Bạn chưa nhập mô tả đơn vị.");
                return;
            }
            uploadDataCD(tencd, motacd);
        });


        btnBack.setOnClickListener(view -> finish());


    }

    private void uploadDataCD(String tencd, String motacd) {
        pd.setTitle("Đang thêm dữ liệu...");
        pd.show();

        ApiServices.apiServices.add_chucdanh(tencd, motacd).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                ObjectReponse objectadd = response.body();
                if (objectadd == null) return;
                if (objectadd.getCode() == 1) {
                    Toast.makeText(AdminChucDanhAddActivity.this, "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminChucDanhAddActivity.this, objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                Toast.makeText(AdminChucDanhAddActivity.this, "Thêm mới thất bại !", Toast.LENGTH_SHORT).show();

            }
        });
    }
}