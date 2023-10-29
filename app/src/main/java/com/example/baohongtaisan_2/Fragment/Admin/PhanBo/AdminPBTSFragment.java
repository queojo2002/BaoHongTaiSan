package com.example.baohongtaisan_2.Fragment.Admin.PhanBo;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baohongtaisan_2.Adapter.Admin.AdminPBTSTaiSanAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.IsLogin;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.PhanBo;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.Model.TaiSan;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPBTSFragment extends Fragment {

    private int MaP;
    private String TenP;
    private View view;
    private RecyclerView rv;
    private SearchView sv;
    private List<TaiSan> taiSanList;
    private AdminPBTSTaiSanAdapter taiSanAdapter;

    public AdminPBTSFragment(int MaP, String TenP) {
        this.MaP = MaP;
        this.TenP = TenP;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_d_s_phan_bo, container, false);
        _AnhXa();
        _SuKien();

        return view;
    }

    private void _AnhXa() {
        rv = view.findViewById(R.id.rvPBTS_TaiSan);
        sv = view.findViewById(R.id.svTaiSan_1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rv.setLayoutManager(linearLayoutManager);
        taiSanList = new ArrayList<>();

        GetListTaiSan();

    }

    public void _SuKien() {
        sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<TaiSan> searchlist = new ArrayList<>();
                for (TaiSan taiSan : taiSanList) {
                    if (taiSan.getTenTS().toLowerCase().contains(s.toLowerCase()) || taiSan.getTenLTS().toLowerCase().contains(s.toLowerCase()) || taiSan.getTenNTS().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(taiSan);
                    }
                }
                taiSanAdapter.searchDataList(searchlist);
                return false;
            }
        });


    }

    public void GetListTaiSan() {
        ApiServices.apiServices.get_list_taisan().enqueue(new Callback<List<TaiSan>>() {
            @Override
            public void onResponse(Call<List<TaiSan>> call, Response<List<TaiSan>> response) {
                taiSanList.clear();
                taiSanList = response.body();
                if (taiSanList != null && getContext() != null) {
                    taiSanAdapter = new AdminPBTSTaiSanAdapter(taiSanList, new RCVClickItem() {
                        @Override
                        public void onClickRCV(Object object, String CURD) {
                            TaiSan taiSan = (TaiSan) object;
                            if (CURD.equals("ADD"))
                            {
                                Open_Dialog_Them(taiSan);
                            }
                        }
                    });
                    rv.setAdapter(taiSanAdapter);
                }

            }
            @Override
            public void onFailure(Call<List<TaiSan>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Open_Dialog_Them(TaiSan taiSan) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_add_phanbo);

        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtt = window.getAttributes();
        windowAtt.gravity = Gravity.CENTER;
        window.setAttributes(windowAtt);
        dialog.setCancelable(true);

        EditText txtSoLuong = dialog.findViewById(R.id.txtSoLuongThemPhanBo);
        EditText txtGhiChu = dialog.findViewById(R.id.txtGhiChuPhanBo);
        TextView tvShowInfo = dialog.findViewById(R.id.tvShowInfoAdd);
        Button btnHuyBo = dialog.findViewById(R.id.btnHuyBoPhanBo);
        Button btnThem = dialog.findViewById(R.id.btnAddPhanBo);

        tvShowInfo.setText("Bạn đang thêm \n" + taiSan.getTenTS() + " \n vào \n" + TenP);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.add_new_phanbo(taiSan.getMaTS(), IsLogin.getInstance().getMaND(), MaP, Integer.parseInt(txtSoLuong.getText().toString()), txtGhiChu.getText().toString()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            onResume();
                            Toast.makeText(requireContext(), "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(requireContext(), objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                        Toast.makeText(requireContext(), "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnHuyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        GetListTaiSan();
    }
}