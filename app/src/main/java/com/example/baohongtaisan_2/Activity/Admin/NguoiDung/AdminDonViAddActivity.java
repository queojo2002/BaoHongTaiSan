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

public class AdminDonViAddActivity extends AppCompatActivity {

    private EditText txtTen, txtMoTa;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_don_vi_add);
        Button btnAdd = findViewById(R.id.btnThemdv);
        txtTen = findViewById(R.id.txtTenDV);
        txtMoTa = findViewById(R.id.txtMotaDV);
        Button btnBack = findViewById(R.id.btnQuaylaidv);

        pd = new ProgressDialog(AdminDonViAddActivity.this);


        btnAdd.setOnClickListener(v -> {
            String tendv = txtTen.getText().toString().trim();
            String motadv = txtMoTa.getText().toString().trim();
            if (tendv.isEmpty()) {
                txtTen.setError("Bạn chưa nhập tên đơn vị.");
                return;
            }
            if (motadv.isEmpty()) {
                txtMoTa.setError("Bạn chưa nhập mô tả đơn vị.");
                return;
            }
            uploadDataDV(tendv, motadv);
        });
        btnBack.setOnClickListener(view -> finish());

    }

    private void uploadDataDV(String tendv, String motadv) {
        pd.setTitle("Đang thêm dữ liệu...");
        pd.show();
        ApiServices.apiServices.add_donvi(tendv, motadv).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                ObjectReponse objectadd = response.body();
                if (objectadd == null) return;
                if (objectadd.getCode() == 1) {
                    Toast.makeText(AdminDonViAddActivity.this, "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminDonViAddActivity.this, objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                Toast.makeText(AdminDonViAddActivity.this, "Thêm mới thất bại !", Toast.LENGTH_SHORT).show();

            }
        });
    }
}