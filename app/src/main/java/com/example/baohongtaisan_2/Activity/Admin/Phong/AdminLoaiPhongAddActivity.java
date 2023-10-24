package com.example.baohongtaisan_2.Activity.Admin.Phong;

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

public class AdminLoaiPhongAddActivity extends AppCompatActivity {

    private EditText txtTen;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_loai_phong_add);

        Button btnAdd = findViewById(R.id.btnThemlp);
        txtTen = findViewById(R.id.txtTenlp);
        Button btnBack = findViewById(R.id.btnQuaylailp);

        pd = new ProgressDialog(AdminLoaiPhongAddActivity.this);


        btnAdd.setOnClickListener(v -> {
            String tenlp = txtTen.getText().toString().trim();
            if (tenlp.isEmpty()) {
                txtTen.setError("Bạn chưa nhập tên loại phòng.");
                return;
            }
            uploadDataLP(tenlp);
        });


        btnBack.setOnClickListener(view -> finish());


    }

    private void uploadDataLP(String name) {
        pd.setTitle("Đang thêm dữ liệu...");
        pd.show();

        ApiServices.apiServices.add_loaiphong(name).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                ObjectReponse objectadd = response.body();
                if (objectadd == null) return;
                if (objectadd.getCode() == 1) {
                    Toast.makeText(AdminLoaiPhongAddActivity.this, "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminLoaiPhongAddActivity.this, objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                Toast.makeText(AdminLoaiPhongAddActivity.this, "Thêm mới thất bại !", Toast.LENGTH_SHORT).show();

            }
        });

    }
}