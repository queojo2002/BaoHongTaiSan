package com.example.baohongtaisan_2.Activity.Admin.Phong;

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

public class AdminLoaiPhongEditActivity extends AppCompatActivity {

    private TextView malp;
    private EditText tenlp;
    private Button btnedit, btnback;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_loai_phong_edit);
        initializeViews();
        intent = getIntent();
        dataBindingLP();

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tenlp = tenlp.getText().toString().trim();
                if (Tenlp.isEmpty()) {
                    tenlp.setError("Bạn chưa nhập tên loại phòng.");
                    return;
                }
                editLoaiPhong();
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
        malp = findViewById(R.id.maLP);
        tenlp = findViewById(R.id.txtTenLP_edit);
        btnedit = findViewById(R.id.btnSualp);
        btnback = findViewById(R.id.btnQuaylailp);
    }

    private void dataBindingLP() {
        Bundle myBundle = intent.getBundleExtra("datalp");
        int IDLP = myBundle.getInt("malp");
        String TenLP = myBundle.getString("tenlp");
        malp.setText(IDLP+"");
        tenlp.setText(TenLP);
    }

    private void editLoaiPhong() {
        ApiServices.apiServices.edit_loaiphong(Integer.parseInt(malp.getText().toString()),tenlp.getText().toString()).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectEdit = response.body();
                if (objectEdit.getCode() == 1){
                    Toast.makeText(AdminLoaiPhongEditActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(AdminLoaiPhongEditActivity.this, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminLoaiPhongEditActivity.this,"Cập nhật thất bại !" , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}