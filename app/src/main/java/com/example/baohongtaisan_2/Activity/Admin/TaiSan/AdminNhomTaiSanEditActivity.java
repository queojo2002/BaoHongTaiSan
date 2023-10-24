package com.example.baohongtaisan_2.Activity.Admin.TaiSan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baohongtaisan_2.Activity.Admin.Phong.AdminLoaiPhongEditActivity;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminNhomTaiSanEditActivity extends AppCompatActivity {

    private TextView mants;
    private EditText tennts;
    private Button btnedit, btnback;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nhom_tai_san_edit);
        initializeViews();
        intent = getIntent();
        dataBindingLP();

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tennts = tennts.getText().toString().trim();
                if (Tennts.isEmpty()) {
                    tennts.setError("Bạn chưa nhập tên nhóm tài sản.");
                    return;
                }
                editNhomTaiSan();
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
        mants = findViewById(R.id.maNTS);
        tennts = findViewById(R.id.txtTenNTS_edit);
        btnedit = findViewById(R.id.btnSuaNTS_edit);
        btnback = findViewById(R.id.btnQuaylaiNTS_edit);
    }

    private void dataBindingLP() {
        Bundle myBundle = intent.getBundleExtra("datants");
        int IDNTS = myBundle.getInt("mants");
        String TenNTS = myBundle.getString("tennts");
        mants.setText(IDNTS + "");
        tennts.setText(TenNTS);
    }

    private void editNhomTaiSan() {
        ApiServices.apiServices.edit_nhomtaisan_byMaNTS(Integer.parseInt(mants.getText().toString()),tennts.getText().toString()).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectEdit = response.body();
                if (objectEdit.getCode() == 1) {
                    Toast.makeText(AdminNhomTaiSanEditActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminNhomTaiSanEditActivity.this, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminNhomTaiSanEditActivity.this, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}