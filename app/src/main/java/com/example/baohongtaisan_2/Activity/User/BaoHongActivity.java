package com.example.baohongtaisan_2.Activity.User;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.IsLogin;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.Model.NotificationDataBaoHong;
import com.example.baohongtaisan_2.Model.NotificationReponse;
import com.example.baohongtaisan_2.Model.NotificationSendData;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.UploadIMG;
import com.example.baohongtaisan_2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaoHongActivity extends AppCompatActivity {
    private Intent intent;
    private List<NguoiDung> nguoiDungList;
    private Uri mImageUri;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;


    private int MaPB = -1, TinhTrang = -1;
    private String TenTS, TenLTS, TenNTS, TenP;

    private TextView tvTenTS, tvTenNTS, tvTenLTS, tvTenP, Title;
    private EditText edtTinhTrang, edtMoTaHuHong;
    private Button btnQuayLai, btnChonHinhAnh, btnGuiBaoHong;
    private ProgressBar mProgressBar;
    private ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_hong);
        _AnhXa();
        _SuKien();


    }





    @SuppressLint("SetTextI18n")
    public void _AnhXa(){
        intent = getIntent();
        mStorageRef = FirebaseStorage.getInstance().getReference("BaoHong");
        Title = findViewById(R.id.Title);
        nguoiDungList = new ArrayList<>();
        tvTenTS = findViewById(R.id.BH_tv_TenTS);
        tvTenNTS = findViewById(R.id.BH_tv_TenNTS);
        tvTenLTS = findViewById(R.id.BH_tv_TenLTS);
        tvTenP = findViewById(R.id.BH_tv_TenPhong);
        imgview = findViewById(R.id.BaoHong_imgView);
        edtMoTaHuHong = findViewById(R.id.BaoHong_txtMoTaHuHong);
        edtTinhTrang = findViewById(R.id.BaoHong_txtTinhTrang);
        btnQuayLai = findViewById(R.id.BaoHong_btnQuayLai);
        btnChonHinhAnh = findViewById(R.id.BaoHong_btnChonHinhAnh);
        btnGuiBaoHong = findViewById(R.id.BaoHong_btnGuiBaoHong);
        mProgressBar = findViewById(R.id.progress_bar);
        MaPB = intent.getIntExtra("MaPB", -1);
        TenTS = intent.getStringExtra("TenTS");
        TenLTS = intent.getStringExtra("TenLTS");
        TenNTS = intent.getStringExtra("TenNTS");
        TenP = intent.getStringExtra("TenP");
        tvTenTS.setText("Tên tài sản: " + TenTS);
        tvTenNTS.setText("Thuộc nhóm tài sản: " + TenLTS);
        tvTenLTS.setText("Loại tài sản: " + TenNTS);
        tvTenP.setText("Tên phòng: " + TenP);
        Title.setText("Báo hỏng: " + TenP);
    }


    public void _SuKien(){
        edtTinhTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _Show_Bottom_Sheet_TinhTrang();
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnChonHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _OpenFileChooserIMG();
            }
        });

        btnGuiBaoHong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _Check_Input();
            }
        });
    }


    public void _Check_Input(){
        if (TextUtils.isEmpty(edtMoTaHuHong.getText().toString())) {
            Toast.makeText(BaoHongActivity.this, "Bạn chưa nhập mô tả hư hỏng của thiết bị tài sản!!!", Toast.LENGTH_SHORT).show();
        }else if (mImageUri == null){
            Toast.makeText(BaoHongActivity.this, "Bạn chưa chọn hình ảnh mô tả hư hỏng của thiết bị tài sản!!!", Toast.LENGTH_SHORT).show();
        }else if (TinhTrang == -1) {
            Toast.makeText(BaoHongActivity.this, "Bạn chưa chọn tình trạng hư hỏng của thiết bị tài sản!!!", Toast.LENGTH_SHORT).show();
        }else {
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(BaoHongActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                btnGuiBaoHong.setEnabled(false);
                btnGuiBaoHong.setText("Đang gửi... bạn vui lòng chờ trong giây lát!!!");
                btnGuiBaoHong.setBackgroundColor(Color.parseColor("#AAB7B8"));
                _Send_BaoHong();
            }
        }
    }


    private void _Send_BaoHong() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> mProgressBar.setProgress(0), 500);
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                UploadIMG upload = new UploadIMG(uri.toString());
                                _Send_BaoHong_To_API(upload.getmImageUrl());
                            }
                        });
                    })
                    .addOnFailureListener(e -> Toast.makeText(BaoHongActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show())
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "Chưa có hình ảnh nào được chọn!!!", Toast.LENGTH_SHORT).show();
        }
    }


    public void _Send_BaoHong_To_API(String HinhAnh) {
        ApiServices.apiServices.add_new_baoloi(MaPB, IsLogin.getInstance().getMaND(), TinhTrang, edtMoTaHuHong.getText().toString(), HinhAnh).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                ObjectReponse objectReponse = response.body();
                if (objectReponse != null) {
                    _Send_Notication_BaoHong_UserToAdmin(IsLogin.getInstance().getMaND(), TenTS, TenP, TinhTrang, edtMoTaHuHong.getText().toString());
                    Toast.makeText(BaoHongActivity.this, objectReponse.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(BaoHongActivity.this, "Gửi báo hỏng thất bại !!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


    private void _Send_Notication_BaoHong_UserToAdmin(int MaND, String TenTS, String TenP, int TinhTrang, String MoTa) {
        ApiServices.apiServices.get_list_nguoidung().enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                nguoiDungList.clear();
                nguoiDungList = response.body();
                if (nguoiDungList != null) {
                    for (int i = 0; i < nguoiDungList.size(); i++) {
                        NguoiDung nguoiDung = nguoiDungList.get(i);
                        if (nguoiDung.getTenPQ().equals("Admin") && nguoiDung.getToken() != null) {
                            System.out.println(nguoiDung.getHoVaTen() + "");
                            _Send_Notication_BaoHong_To_Firebase(MaND, TenTS, TenP, 0, TinhTrang, MoTa, nguoiDung.getToken());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                Toast.makeText(BaoHongActivity.this, "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void _Send_Notication_BaoHong_To_Firebase(int MaND, String TenTS, String TenP, int TrangThai, int TinhTrang, String MoTa, String Token) {
        NotificationDataBaoHong notificationDataBaoHong = new NotificationDataBaoHong(MaND, TenTS, TenP, TrangThai, TinhTrang, MoTa, "UserToAdmin");
        NotificationSendData notificationSendData = new NotificationSendData(notificationDataBaoHong, Token);
        ApiServices.apiServices_Noti.sendNoti(notificationSendData).enqueue(new Callback<NotificationReponse>() {
            @Override
            public void onResponse(Call<NotificationReponse> call, Response<NotificationReponse> response) {

            }

            @Override
            public void onFailure(Call<NotificationReponse> call, Throwable t) {

            }
        });
    }




    public void _Show_Bottom_Sheet_TinhTrang(){
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_baohong_tinhtrang, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(true);

        RadioGroup rg = (RadioGroup) view.findViewById(R.id.rdBaoHong);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rdSTT1) {
                    edtTinhTrang.setText("Hư hỏng nhẹ (Minor)");
                    TinhTrang = 1;
                    bottomSheetDialog.dismiss();
                }else if (i == R.id.rdSTT2){
                    edtTinhTrang.setText("Hư hỏng trung bình (Moderate)");
                    TinhTrang = 2;
                    bottomSheetDialog.dismiss();
                }else if (i == R.id.rdSTT3){
                    edtTinhTrang.setText("Hư hỏng nghiêm trọng (Severe)");
                    TinhTrang = 3;
                    bottomSheetDialog.dismiss();
                }else if (i == R.id.rdSTT4){
                    edtTinhTrang.setText("Hư hỏng hoàn toàn (Critical)");
                    TinhTrang = 4;
                    bottomSheetDialog.dismiss();
                }


            }
        });
    }



    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void _OpenFileChooserIMG() {
        Intent intent1 = new Intent();
        intent1.setType("image/*");
        intent1.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent1, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            imgview.setVisibility(View.VISIBLE);
            Glide.with(this).load(mImageUri).into(imgview);
        }
    }



}