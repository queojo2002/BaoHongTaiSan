package com.example.baohongtaisan_2.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.User.AdapterPhanBo_TaiSan;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.Model.PhanBo;
import com.example.baohongtaisan_2.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraCuu_BaoHongActivity extends AppCompatActivity {

    private Intent intent;
    private Toolbar toolbar;
    private int MaP = -1;
    private List<PhanBo> phanBoList;
    private List<BaoHong> baoHongList;

    private RecyclerView recycler;
    private LinearLayoutManager linearLayoutManager;

    private AdapterPhanBo_TaiSan adapterPhanBo_taiSan;

    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_cuu);

        toolbar = findViewById(R.id.toolbar);
        recycler = findViewById(R.id.recycler_tracuu);
        phanBoList = new ArrayList<>();
        baoHongList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(TraCuu_BaoHongActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(itemDecoration);

        intent = getIntent();
        db = FirebaseDatabase.getInstance();
        MaP = intent.getIntExtra("MaP", -1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        Data_Show_ListTaiSan_InPhong();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Data_Show_ListTaiSan_InPhong();
    }

    private void Data_Show_ListTaiSan_InPhong() {

        ApiServices.apiServices.get_list_phanbo_byMaP(MaP).enqueue(new Callback<List<PhanBo>>() {
            @Override
            public void onResponse(Call<List<PhanBo>> call, Response<List<PhanBo>> response) {
                phanBoList.clear();
                phanBoList = response.body();
                if (phanBoList.size() == 0) {
                    Toast.makeText(TraCuu_BaoHongActivity.this, "Phòng này hiện chưa có tài sản!!!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    ApiServices.apiServices.get_list_baohong().enqueue(new Callback<List<BaoHong>>() {
                        @Override
                        public void onResponse(Call<List<BaoHong>> call, Response<List<BaoHong>> response) {
                            baoHongList.clear();
                            if (response.isSuccessful()) {
                                baoHongList = response.body();
                                adapterPhanBo_taiSan = new AdapterPhanBo_TaiSan(phanBoList, baoHongList);
                                recycler.setAdapter(adapterPhanBo_taiSan);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<BaoHong>> call, Throwable t) {
                            Toast.makeText(TraCuu_BaoHongActivity.this, "Có lỗi khi lấy dữ liệu báo hỏng cho phòng này !!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });


                }

            }

            @Override
            public void onFailure(Call<List<PhanBo>> call, Throwable t) {
                Toast.makeText(TraCuu_BaoHongActivity.this, "Có lỗi khi lấy dữ liệu tài sản cho phòng này !!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        /*DatabaseReference phongRef = db.getReference("PhanBo");
        phongRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int Flag = 0;
                for (DataSnapshot pbSnapshot : dataSnapshot.getChildren()) {
                    PhanBo phanBo = pbSnapshot.getValue(PhanBo.class);
                    if (phanBo.getPhong().getMaP().equals(MaP)) {
                        Flag = 1;
                        phanBoList.add(phanBo);
                    }
                }
                if (Flag == 1) {
                    adapterPhanBo_taiSan = new AdapterPhanBo_TaiSan(phanBoList);
                    recycler.setAdapter(adapterPhanBo_taiSan);
                    adapterPhanBo_taiSan.notifyDataSetChanged();
                } else {
                    Toast.makeText(TraCuu_BaoHongActivity.this, "Phòng này hiện chưa có tài sản!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });*/

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}