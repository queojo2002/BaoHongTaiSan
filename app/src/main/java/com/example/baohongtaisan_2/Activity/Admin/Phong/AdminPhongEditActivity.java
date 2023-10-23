package com.example.baohongtaisan_2.Activity.Admin.Phong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerKhuVucPhong_Adapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerLoaiPhong_Adapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPhongEditActivity extends AppCompatActivity {

    private TextView maP;
    private EditText txtTen;
    private Button btnedit, btnback;
    private Intent intent;
    private Spinner spinnerKVP, spinnerLP;
    private List<KhuVucPhong> listKVP;
    private List<LoaiPhong> listLP;

    private int MaLP = -1, MaKVP = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_phong_edit);
        initializeViews();
        intent = getIntent();
        dataBindingChucDanh();

        loadDataForSpinner();

        setSpinnerListeners();

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenphong = txtTen.getText().toString().trim();
                if (tenphong.isEmpty()) {
                    txtTen.setError("Bạn chưa nhập tên phòng.");
                    return;
                }
                if (MaKVP == -1) {
                    Toast.makeText(AdminPhongEditActivity.this, "Vui lòng chọn khu vực phòng.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (MaLP == -1) {
                    Toast.makeText(AdminPhongEditActivity.this, "Vui lòng chọn loại phòng.", Toast.LENGTH_SHORT).show();
                    return;
                }
                editPhong();
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
        maP = findViewById(R.id.maP);
        txtTen = findViewById(R.id.txtEdit_TenPhong);
        btnedit = findViewById(R.id.btnSuaphong);
        btnback = findViewById(R.id.btnQuaylaiphong);
        spinnerKVP = findViewById(R.id.spnEdit_KVP);
        spinnerLP = findViewById(R.id.spnEdit_LP);

        listKVP = new ArrayList<>();
        listLP = new ArrayList<>();
    }

    private void dataBindingChucDanh() {
        Bundle myBundle = intent.getBundleExtra("dataphong");
        int IdP = myBundle.getInt("maphong");
        String TenP = myBundle.getString("tenphong");
        MaKVP = myBundle.getInt("makvp");
        MaLP = myBundle.getInt("malp");
        maP.setText(IdP + "");
        txtTen.setText(TenP);
    }


    private void editPhong() {
        ApiServices.apiServices.edit_phong(Integer.parseInt(maP.getText().toString()), txtTen.getText().toString(), MaKVP, MaLP).enqueue(new Callback<ObjectReponse>() {
            @Override
            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                ObjectReponse objectEdit = response.body();
                if (objectEdit.getCode() == 1) {
                    Toast.makeText(AdminPhongEditActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminPhongEditActivity.this, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                Toast.makeText(AdminPhongEditActivity.this, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSpinnerListeners() {
        spinnerLP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                handleLoaiPhongItemSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerKVP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                handleKhuVucPhongItemSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void handleLoaiPhongItemSelected(int position) {
        ArrayAdapter<LoaiPhong> adapter = (ArrayAdapter<LoaiPhong>) spinnerLP.getAdapter();
        LoaiPhong selected = adapter.getItem(position);
        if (selected != null) {
            MaLP = selected.getMaLP();
        }
    }

    private void handleKhuVucPhongItemSelected(int position) {
        ArrayAdapter<KhuVucPhong> adapter = (ArrayAdapter<KhuVucPhong>) spinnerKVP.getAdapter();
        KhuVucPhong selected = adapter.getItem(position);
        if (selected != null) {
            MaKVP = selected.getMaKVP();
        }
    }

    private void loadDataForSpinner() {
        loadKVPData(MaKVP);
        loadLPData(MaLP);
    }

    private void loadKVPData(int makvp) {
        ApiServices.apiServices.get_list_khuvucphong().enqueue(new Callback<List<KhuVucPhong>>() {
            @Override
            public void onResponse(Call<List<KhuVucPhong>> call, Response<List<KhuVucPhong>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listKVP = response.body();

                    Collections.sort(listKVP, new Comparator<KhuVucPhong>() {
                        @Override
                        public int compare(KhuVucPhong kvp1, KhuVucPhong kvp2) {
                            return Integer.compare(kvp1.getMaKVP(), kvp2.getMaKVP());
                        }
                    });

                    KhuVucPhong selectedKVP = null;
                    for (KhuVucPhong kvp : listKVP) {
                        if (kvp.getMaKVP() == makvp) {
                            selectedKVP = kvp;
                            break;
                        }
                    }

                    if (selectedKVP != null) {
                        SpinnerKhuVucPhong_Adapter khuVucPhong_adapter = new SpinnerKhuVucPhong_Adapter(AdminPhongEditActivity.this, R.layout.custom_spinner_selected, listKVP);
                        spinnerKVP.setAdapter(khuVucPhong_adapter);
                        int selectedItemPosition = khuVucPhong_adapter.getPosition(selectedKVP);
                        spinnerKVP.setSelection(selectedItemPosition);
                    } else {
                        Toast.makeText(AdminPhongEditActivity.this, "Không tìm thấy khu vực phòng có mã " + makvp, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdminPhongEditActivity.this, "Có lỗi xảy ra khi load tên khu vực phòng ...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<KhuVucPhong>> call, Throwable t) {
                Toast.makeText(AdminPhongEditActivity.this, "ERROR...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadLPData(int malp) {
        ApiServices.apiServices.get_list_loaiphong().enqueue(new Callback<List<LoaiPhong>>() {
            @Override
            public void onResponse(Call<List<LoaiPhong>> call, Response<List<LoaiPhong>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listLP = response.body();

                    Collections.sort(listLP, new Comparator<LoaiPhong>() {
                        @Override
                        public int compare(LoaiPhong lp1, LoaiPhong lp2) {
                            return Integer.compare(lp1.getMaLP(), lp2.getMaLP());
                        }
                    });

                    LoaiPhong selectedLP = null;
                    for (LoaiPhong lp : listLP) {
                        if (lp.getMaLP() == malp) {
                            selectedLP = lp;
                            break;
                        }
                    }
                    if (selectedLP != null) {
                        SpinnerLoaiPhong_Adapter loaiPhong_adapter = new SpinnerLoaiPhong_Adapter(AdminPhongEditActivity.this, R.layout.custom_spinner_selected, listLP);
                        spinnerLP.setAdapter(loaiPhong_adapter);
                        int selectedItemPosition = loaiPhong_adapter.getPosition(selectedLP);
                        spinnerLP.setSelection(selectedItemPosition);
                    } else {
                        Toast.makeText(AdminPhongEditActivity.this, "Không tìm thấy loại phòng có mã " + malp, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdminPhongEditActivity.this, "Có lỗi xảy ra khi load tên loại phòng...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LoaiPhong>> call, Throwable t) {
                Toast.makeText(AdminPhongEditActivity.this, "ERROR...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}