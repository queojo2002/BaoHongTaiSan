package com.example.baohongtaisan_2.Activity.Admin.NguoiDung;

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

public class AdminChucDanhEditActivity extends AppCompatActivity {

    private TextView macd;
    private EditText tencd, motacd;
    private Intent intent;
    private Button btnedit, btnback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chuc_danh_edit);
        initializeViews();
        intent = getIntent();
        dataBindingChucDanh();

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tencd = tencd.getText().toString().trim();
                String Motacd = motacd.getText().toString().trim();
                if (Tencd.isEmpty()) {
                    tencd.setError("Bạn chưa nhập tên đơn vị.");
                    return;
                }
                if (Motacd.isEmpty()) {
                    motacd.setError("Bạn chưa nhập mô tả đơn vị.");
                    return;
                }
                editChucDanh();
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
        macd = findViewById(R.id.macd);
        tencd = findViewById(R.id.txtTenCD_edit);
        motacd = findViewById(R.id.txtMotaCD_edit);
        btnedit = findViewById(R.id.btnSuacd);
        btnback = findViewById(R.id.btnQuaylaicd);
    }

    private void dataBindingChucDanh() {
        Bundle myBundle = intent.getBundleExtra("datachucdanh");
        int IDCD = myBundle.getInt("macd");
        String TenCD = myBundle.getString("tencd");
        String MotaCD = myBundle.getString("motacd");
        macd.setText(IDCD + "");
        tencd.setText(TenCD);
        motacd.setText(MotaCD);
    }

    private void editChucDanh() {
        ApiServices.apiServices.edit_chucdanh(Integer.parseInt(macd.getText().toString()),tencd.getText().toString(),motacd.getText().toString()).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectEdit = response.body();
                if (objectEdit.getCode() == 1){
                    Toast.makeText(AdminChucDanhEditActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(AdminChucDanhEditActivity.this, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminChucDanhEditActivity.this, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}