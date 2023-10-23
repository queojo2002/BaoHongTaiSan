package com.example.baohongtaisan_2.Activity.Admin.Phong;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminKhuVucPhongAddActivity extends AppCompatActivity {
    private Button btnAdd, btnBack;
    private EditText txtTen;
    private FirebaseDatabase db;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_khu_vuc_phong_add);

        btnAdd = findViewById(R.id.btnThemkv);
        txtTen = findViewById(R.id.txtTenkv);
        btnBack = findViewById(R.id.btnQuaylaikv);

        pd = new ProgressDialog(AdminKhuVucPhongAddActivity.this);

        db = FirebaseDatabase.getInstance();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenkv = txtTen.getText().toString().trim();
                if (tenkv.isEmpty()) {
                    txtTen.setError("Bạn chưa nhập tên khu vực phòng.");
                    return;
                }
                uploadDataKVP(tenkv);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void uploadDataKVP(String name) {
        pd.setTitle("Đang thêm dữ liệu...");
        pd.show();

        ApiServices.apiServices.add_khuvucphong(name).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectadd = response.body();
                if (objectadd.getCode() == 1){
                    Toast.makeText(AdminKhuVucPhongAddActivity.this, "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(AdminKhuVucPhongAddActivity.this, objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminKhuVucPhongAddActivity.this, "Thêm mới thất bại !", Toast.LENGTH_SHORT).show();

            }
        });
    }
}