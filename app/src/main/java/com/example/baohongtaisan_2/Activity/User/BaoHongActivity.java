package com.example.baohongtaisan_2.Activity.User;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.baohongtaisan_2.Adapter.Admin.AdminNguoiDungAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.Model.NotificationDataBaoHong;
import com.example.baohongtaisan_2.Model.NotificationReponse;
import com.example.baohongtaisan_2.Model.NotificationSendData;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.Object_Add;
import com.example.baohongtaisan_2.Model.UploadIMG;
import com.example.baohongtaisan_2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
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
    private Toolbar toolbar;
    private int MaPB;
    private String TenTS = null, TenP = null;
    private FirebaseDatabase database;
    private TextView tv_TenTS;
    private EditText txtMoTaHH;
    private RadioButton rdHH1, rdHH2, rdHH3, rdHH4;
    private int TrangThaiHH = -1;

    private Button btnBaoHong, btnChooseIMG;
    private Uri mImageUri;
    private StorageReference mStorageRef;

    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
    private List<NguoiDung> nguoiDungList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_hong);
        toolbar = findViewById(R.id.toolbar);
        tv_TenTS = findViewById(R.id.txt_TenTS_BH);
        txtMoTaHH = findViewById(R.id.txtMoTaHuHong_BH);
        mProgressBar = findViewById(R.id.progress_bar);
        database = FirebaseDatabase.getInstance();
        rdHH1 = findViewById(R.id.rdSTT1);
        rdHH2 = findViewById(R.id.rdSTT2);
        rdHH3 = findViewById(R.id.rdSTT3);
        rdHH4 = findViewById(R.id.rdSTT4);
        btnBaoHong = findViewById(R.id.btnGuiBaoHong);
        btnChooseIMG = findViewById(R.id.btnChooseIMG);
        intent = getIntent();
        MaPB = intent.getIntExtra("MaPB", -1);
        TenTS = intent.getStringExtra("TenTS");
        TenP = intent.getStringExtra("TenP");
        tv_TenTS.setText(TenTS);
        nguoiDungList = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        getSupportActionBar().setTitle("Báo hỏng: " + TenP + "");


        mStorageRef = FirebaseStorage.getInstance().getReference("BaoHong");


        btnChooseIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooserIMG();
            }
        });

        rdHH1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrangThaiHH = 1;
            }
        });

        rdHH2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrangThaiHH = 2;
            }
        });

        rdHH3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrangThaiHH = 3;
            }
        });

        rdHH4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrangThaiHH = 4;
            }
        });


        btnBaoHong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _BaoHong();
            }
        });


    }


    public void _BaoHong() {

        if (TrangThaiHH == -1) {
            Toast.makeText(BaoHongActivity.this, "Bạn chưa chọn trạng thái hư hỏng của thiết bị, tài sản!!!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(txtMoTaHH.getText().toString())) {
            Toast.makeText(BaoHongActivity.this, "Bạn chưa nhập mô tả hư hỏng của thiết bị, tài sản!!!", Toast.LENGTH_SHORT).show();
        } else {

            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(BaoHongActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                btnBaoHong.setEnabled(false);
                btnBaoHong.setText("Đang gửi... bạn vui lòng chờ trong giây lát!!!");
                btnBaoHong.setBackgroundColor(Color.parseColor("#AAB7B8"));
                _Gui_BaoHong();
            }

        }
    }


    private void _Gui_BaoHong() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UploadIMG upload = new UploadIMG(uri.toString());
                                    _Gui_BaoHong_1(upload.getmImageUrl());
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BaoHongActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


    public void _Gui_BaoHong_1(String HinhAnh) {
        ApiServices.apiServices.get_nguoidung_byEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                NguoiDung nguoiDung = response.body();
                if (nguoiDung != null) {
                    ApiServices.apiServices.add_new_baoloi(MaPB, nguoiDung.getMaND(), TrangThaiHH, txtMoTaHH.getText().toString(), HinhAnh).enqueue(new Callback<ObjectReponse>() {
                        @Override
                        public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                            ObjectReponse object_add = response.body();
                            if (object_add != null) {
                                sendNotiToAdmin(nguoiDung.getMaND(), TenTS, TenP, 0, TrangThaiHH, txtMoTaHH.getText().toString());
                                Toast.makeText(BaoHongActivity.this, object_add.getMessage(), Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                Toast.makeText(BaoHongActivity.this, "Load dữ liệu người dùng không thành công !!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void openFileChooserIMG() {
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
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void sendNotiToAdmin(int MaND, String TenTS, String TenP, int TrangThai, int TinhTrang, String MoTa)
    {
        ApiServices.apiServices.get_list_nguoidung().enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                nguoiDungList.clear();
                nguoiDungList = response.body();
                if (nguoiDungList != null) {
                    for (int i = 0 ; i < nguoiDungList.size(); i++)
                    {
                        NguoiDung nguoiDung = nguoiDungList.get(i);
                        if (nguoiDung.getTenPQ().equals("Admin") && nguoiDung.getToken() != null)
                        {
                            System.out.println(nguoiDung.getHoVaTen()+"");
                            sendNoti(MaND, TenTS, TenP, TrangThai, TinhTrang, MoTa, nguoiDung.getToken());
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

    private void sendNoti(int MaND, String TenTS, String TenP, int TrangThai, int TinhTrang, String MoTa, String Token)
    {
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

}