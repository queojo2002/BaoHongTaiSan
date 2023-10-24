package com.example.baohongtaisan_2.Activity.Admin.NguoiDung;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_don_vi_edit);
        initializeViews();
        intent = getIntent();
        dataBindingDonVi();


        btnedit.setOnClickListener(view -> {
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
        });

        btnback.setOnClickListener(view -> finish());
    }

    private void initializeViews() {
        madv = findViewById(R.id.madv);
        tendv = findViewById(R.id.txtTenDV_edit);
        motadv = findViewById(R.id.txtMotaDV_edit);
        btnedit = findViewById(R.id.btnSuadv);
        btnback = findViewById(R.id.btnQuaylaidv);
    }

    @SuppressLint("SetTextI18n")
    private void dataBindingDonVi() {
        Bundle myBundle = intent.getBundleExtra("datadonvi");
        int IDDV = myBundle.getInt("madv");
        String tenDV = myBundle.getString("tendv");
        String motaDV = myBundle.getString("motadv");
        madv.setText(IDDV + "");
        tendv.setText(tenDV);
        motadv.setText(motaDV);
    }

    private void editDonVi() {
        ApiServices.apiServices.edit_donvi(Integer.parseInt(madv.getText().toString()), tendv.getText().toString(), motadv.getText().toString()).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                ObjectReponse objectEdit = response.body();
                if (objectEdit == null) return;
                if (objectEdit.getCode() == 1) {
                    Toast.makeText(AdminDonViEditActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminDonViEditActivity.this, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                Toast.makeText(AdminDonViEditActivity.this, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}