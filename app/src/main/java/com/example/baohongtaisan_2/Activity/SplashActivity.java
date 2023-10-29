package com.example.baohongtaisan_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baohongtaisan_2.Activity.Admin.AdminHomeActivity;
import com.example.baohongtaisan_2.Activity.User.HomeActivity;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.IsLogin;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    TextView txtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtCount = findViewById(R.id.txt_Time);


        new CountDownTimer(3000, 1000) {
            int count = 3;

            @Override
            public void onTick(long millisUntilFinished) {
                txtCount.setText(String.valueOf(count));
                count--;
            }

            @Override
            public void onFinish() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    System.out.println(user.getEmail() + "");
                    ApiServices.apiServices.get_nguoidung_byEmail(user.getEmail()).enqueue(new Callback<NguoiDung>() {
                        @Override
                        public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                            NguoiDung nguoiDung = response.body();
                            if (nguoiDung != null) {
                                IsLogin loginInstance = IsLogin.getInstance();
                                loginInstance.setLoggedInUser(
                                        nguoiDung.getMaND(),
                                        nguoiDung.getMaDV(),
                                        nguoiDung.getMaCD(),
                                        nguoiDung.getMaPQ(),
                                        nguoiDung.getTenDangNhap(),
                                        nguoiDung.getMatKhau(),
                                        nguoiDung.getHoVaTen(),
                                        nguoiDung.getEmail(),
                                        nguoiDung.getDiaChi(),
                                        nguoiDung.getSoDienThoai(),
                                        nguoiDung.getTenCD(),
                                        nguoiDung.getTenDV(),
                                        nguoiDung.getTenPQ(),
                                        nguoiDung.getNgayCapNhat(),
                                        nguoiDung.getNgayTao(),
                                        nguoiDung.getUid(),
                                        nguoiDung.getToken());
                                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                                    @Override
                                    public void onComplete(@NonNull Task<String> task) {
                                        ApiServices.apiServices.edit_token_uid_nguoidung(nguoiDung.getMaND(), FirebaseAuth.getInstance().getCurrentUser().getUid(), task.getResult()).enqueue(new Callback<ObjectReponse>() {
                                            @Override
                                            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                                                if (nguoiDung.getTenPQ().equals("Admin")) {
                                                    Intent intent = new Intent(SplashActivity.this, AdminHomeActivity.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                } else if (nguoiDung.getTenPQ().equals("User")) {
                                                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                } else {
                                                    finishAffinity();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                                            }
                                        });


                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<NguoiDung> call, Throwable t) {
                            Toast.makeText(SplashActivity.this, "Load dữ liệu người dùng không thành công !!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });


                }

            }
        }.start();
    }


}