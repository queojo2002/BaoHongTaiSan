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
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminKhuVucPhongEditActivity extends AppCompatActivity {

    private TextView makv;
    private EditText tenkv;
    private Button btnedit, btnback;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_khu_vuc_phong_edit);

        initializeViews();
        intent = getIntent();
        dataBindingKVP();

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tenkv = tenkv.getText().toString().trim();
                if (Tenkv.isEmpty()) {
                    tenkv.setError("Bạn chưa nhập tên khu vực.");
                    return;
                }
                editKhuVucPhong();
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
        makv = findViewById(R.id.maKV);
        tenkv = findViewById(R.id.txtTenKV_edit);
        btnedit = findViewById(R.id.btnSuakv);
        btnback = findViewById(R.id.btnQuaylaikv);
    }

    private void dataBindingKVP() {
        Bundle myBundle = intent.getBundleExtra("datakhuvucphong");
        int IDKVP = myBundle.getInt("makhuvuc");
        String TenKVP = myBundle.getString("tenkhuvuc");
        makv.setText(IDKVP+"");
        tenkv.setText(TenKVP);
    }

    private void editKhuVucPhong() {
        ApiServices.apiServices.edit_khuvucphong(Integer.parseInt(makv.getText().toString()),tenkv.getText().toString()).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectEdit = response.body();
                if (objectEdit.getCode() == 1){
                    Toast.makeText(AdminKhuVucPhongEditActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(AdminKhuVucPhongEditActivity.this, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminKhuVucPhongEditActivity.this,"Cập nhật thất bại !" , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}