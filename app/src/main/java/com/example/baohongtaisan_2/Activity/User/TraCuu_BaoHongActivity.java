package com.example.baohongtaisan_2.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.User.AdapterPhanBo_TaiSan;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.Model.PhanBo;
import com.example.baohongtaisan_2.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraCuu_BaoHongActivity extends AppCompatActivity {


    private SearchView sv;
    private int MaP = -1;
    private List<PhanBo> phanBoList;
    private List<BaoHong> baoHongList;

    private RecyclerView recycler;

    private AdapterPhanBo_TaiSan adapterPhanBo_taiSan;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_cuu);

        sv = findViewById(R.id.svTaiSan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recycler = findViewById(R.id.recycler_tracuu);
        phanBoList = new ArrayList<>();
        baoHongList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TraCuu_BaoHongActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(itemDecoration);

        Intent intent = getIntent();
        MaP = intent.getIntExtra("MaP", -1);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        Data_Show_ListTaiSan_InPhong();

        _SuKien();
    }




    public void _SuKien(){
        sv.clearFocus();
        sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<PhanBo> searchlist = new ArrayList<>();
                for (PhanBo phanBo : phanBoList) {
                    if (phanBo.getTenTS().toLowerCase().contains(s.toLowerCase()) ||
                            phanBo.getTenNTS().toLowerCase().contains(s.toLowerCase()) ||
                            phanBo.getTenLTS().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(phanBo);
                    }
                }
                if (adapterPhanBo_taiSan != null) {
                    adapterPhanBo_taiSan.searchDataList(searchlist);
                }
                return false;
            }
        });
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

                    ApiServices.apiServices.get_list_baohong("Admin").enqueue(new Callback<List<BaoHong>>() {
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

    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Data_Show_ListTaiSan_InPhong();
    }
}