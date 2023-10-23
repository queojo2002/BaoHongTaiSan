package com.example.baohongtaisan_2.Activity.Admin.NguoiDung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDonViEditActivity extends AppCompatActivity {

    private TextView madv;
    private EditText tendv, motadv;
    private Intent intent;
    private Button btnedit, btnback;
    private int IDDV;
    private String TenDV, MotaDV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_don_vi_edit);
        initializeViews();
        intent = getIntent();
        dataBindingDonVi();


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tendv = tendv.getText().toString().trim();
                String Motadv = motadv.getText().toString().trim();
                if (Tendv.isEmpty()) {
                    tendv.setError("Bạn chưa nhập tên đơn vị.");
                    return;
                }
                if (Motadv.isEmpty()) {
                    motadv.setError("Bạn chưa nhập mô tả đơn vị.");
                    return;
                }
                editDonVi();
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
        madv = findViewById(R.id.madv);
        tendv = findViewById(R.id.txtTenDV_edit);
        motadv = findViewById(R.id.txtMotaDV_edit);
        btnedit = findViewById(R.id.btnSuadv);
        btnback = findViewById(R.id.btnQuaylaidv);
    }

    private void dataBindingDonVi() {
        Bundle myBundle = intent.getBundleExtra("datadonvi");
        IDDV = myBundle.getInt("madv");
        TenDV = myBundle.getString("tendv");
        MotaDV = myBundle.getString("motadv");
        madv.setText(IDDV + "");
        tendv.setText(TenDV);
        motadv.setText(MotaDV);
    }

    private void editDonVi() {
        ApiServices.apiServices.edit_donvi(Integer.parseInt(madv.getText().toString()), tendv.getText().toString(), motadv.getText().toString()).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectEdit = response.body();
                if (objectEdit.getCode() == 1) {
                    Toast.makeText(AdminDonViEditActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminDonViEditActivity.this, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminDonViEditActivity.this, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}