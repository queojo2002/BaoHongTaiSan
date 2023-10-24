package com.example.baohongtaisan_2.Activity.Admin.TaiSan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoaiTaiSanEditActivity extends AppCompatActivity {

    private TextView malts;
    private EditText tenlts;
    private Button btnedit, btnback;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_loai_tai_san_edit);
        initializeViews();
        intent = getIntent();
        dataBindingLTS();

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tennts = tenlts.getText().toString().trim();
                if (Tennts.isEmpty()) {
                    tenlts.setError("Bạn chưa nhập tên nhóm tài sản.");
                    return;
                }
                editLoaiTaiSan();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initializeViews() {
        malts = findViewById(R.id.maLTS);
        tenlts = findViewById(R.id.txtTenLTS_edit);
        btnedit = findViewById(R.id.btnSuaLTS_edit);
        btnback = findViewById(R.id.btnQuaylaiLTS_edit);
    }

    private void dataBindingLTS() {
        Bundle myBundle = intent.getBundleExtra("datalts");
        int ID = myBundle.getInt("malts");
        String Ten = myBundle.getString("tenlts");
        malts.setText(ID + "");
        tenlts.setText(Ten);
    }

    private void editLoaiTaiSan() {
        ApiServices.apiServices.edit_data_loaitaisan_byMaLTS(Integer.parseInt(malts.getText().toString()),tenlts.getText().toString()).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectEdit = response.body();
                if (objectEdit.getCode() == 1) {
                    Toast.makeText(AdminLoaiTaiSanEditActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminLoaiTaiSanEditActivity.this, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminLoaiTaiSanEditActivity.this, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}