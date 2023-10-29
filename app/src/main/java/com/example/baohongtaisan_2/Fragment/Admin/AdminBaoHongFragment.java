package com.example.baohongtaisan_2.Fragment.Admin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.Admin.AdminBaoHongAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBaoHongFragment extends Fragment {

    private SearchView svBH;
    private RecyclerView rvBH;
    private LinearLayoutManager linearLayoutManager;
    private AdminBaoHongAdapter adminBaoHongAdapter;
    private List<BaoHong> baoHongList;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_bao_hong, container, false);
        _AnhXa();
        _SuKien();

        return view;
    }


    public void _AnhXa() {
        rvBH = view.findViewById(R.id.rvBaohong);
        svBH = view.findViewById(R.id.txtSearchBH);

        linearLayoutManager = new LinearLayoutManager(requireContext());
        rvBH.setLayoutManager(linearLayoutManager);

        baoHongList = new ArrayList<>();

        GetListBaoHong();


    }


    private void _SuKien() {

        svBH.clearFocus();
        svBH.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<BaoHong> searchlist = new ArrayList<>();
                for (BaoHong baoHong : baoHongList) {
                    if (baoHong.getTenP().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(baoHong);
                    }
                }
                if (adminBaoHongAdapter != null) {
                    adminBaoHongAdapter.searchDataList(searchlist);
                }
                return false;
            }
        });


    }


    public void GetListBaoHong() {
        ApiServices.apiServices.get_list_baohong().enqueue(new Callback<List<BaoHong>>() {
            @Override
            public void onResponse(Call<List<BaoHong>> call, Response<List<BaoHong>> response) {
                baoHongList.clear();
                baoHongList = response.body();
                if (response.isSuccessful() && response.body() != null && baoHongList != null && getContext() != null) {
                    adminBaoHongAdapter = new AdminBaoHongAdapter(baoHongList, new RCVClickItem() {
                        @Override
                        public void onClickRCV(Object object, String CURD) {
                            BaoHong baoHong = (BaoHong) object;
                            if (CURD.equals("CHITIET")) {
                                Open_Dialog_ChiTiet(baoHong);
                            }
                        }
                    });
                    rvBH.setAdapter(adminBaoHongAdapter);
                } else {
                    Toast.makeText(getContext(), "Có lỗi xảy ra...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BaoHong>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @SuppressLint("SetTextI18n")
    public void Open_Dialog_ChiTiet(BaoHong baoHong) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_chitiet_baohong);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtt = window.getAttributes();
        windowAtt.gravity = Gravity.CENTER;
        window.setAttributes(windowAtt);
        dialog.setCancelable(true);

        TextView tents = dialog.findViewById(R.id.tvTenTS);
        TextView tenphong = dialog.findViewById(R.id.tvTenphong);
        TextView tinhtrang = dialog.findViewById(R.id.tvTinhtrang);
        TextView ngaycapnhat = dialog.findViewById(R.id.tvNgaycapnhat);
        TextView hoten = dialog.findViewById(R.id.tvHoten);
        TextView email = dialog.findViewById(R.id.tvEmail);

        tents.setText(baoHong.getTenTS());
        tenphong.setText(baoHong.getTenP());
        int tt = baoHong.getTinhTrang();
        if (tt == 1) {
            tinhtrang.setText("Hư hỏng nhẹ (Minor)");
        } else if (tt == 2) {
            tinhtrang.setText("Hư hỏng trung bình (Moderate)");
        } else if (tt == 3) {
            tinhtrang.setText("Hư hỏng nghiêm trọng (Severe)");
        } else if (tt == 4) {
            tinhtrang.setText("Hư hỏng hoàn toàn (Critical)");
        }
        ngaycapnhat.setText(baoHong.getNgayCapNhat());

        hoten.setText(baoHong.getHoVaTen());
        email.setText(baoHong.getEmail());

        Button btnhuybo = dialog.findViewById(R.id.btnHuyBo);

        btnhuybo.setOnClickListener(new View.OnClickListener() {
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
        GetListBaoHong();

    }


}