package com.example.baohongtaisan_2.Fragment.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.User.AdapterBaoLoi;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.Model.ThongKe;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrangChuFragment extends Fragment {

    private View view;
    private TextView txtSL_ND, txtSL_P, txtSL_TS;
    private RecyclerView rvHome;
    private List<BaoHong> baoHongList;
    private LinearLayoutManager linearLayoutManager;


    public TrangChuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baoHongList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        _AnhXa();
        _Get_ThongKe();
        _Show_BaoHong();
        return view;
    }


    public void _AnhXa() {
        txtSL_ND = view.findViewById(R.id.txtSoLieu_NguoiDung);
        txtSL_P = view.findViewById(R.id.txtSoLieu_Phong);
        txtSL_TS = view.findViewById(R.id.txtSoLieu_TaiSan);
        rvHome = view.findViewById(R.id.rvHome);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvHome.setLayoutManager(linearLayoutManager);
    }

    public void _Get_ThongKe() {
        ApiServices.apiServices.get_thongke().enqueue(new Callback<ThongKe>() {
            @Override
            public void onResponse(Call<ThongKe> call, Response<ThongKe> response) {
                ThongKe thongKe = response.body();
                if (thongKe != null && getContext() != null) {
                    txtSL_ND.setText(thongKe.getTongSo_NguoiDung() + "");
                    txtSL_P.setText(thongKe.getTongSo_PhongHoc() + "");
                    txtSL_TS.setText(thongKe.getTongSo_TaiSan() + "");
                }
            }

            @Override
            public void onFailure(Call<ThongKe> call, Throwable t) {

            }
        });
    }

    public void _Show_BaoHong() {
        ApiServices.apiServices.get_list_baohong().enqueue(new Callback<List<BaoHong>>() {
            @Override
            public void onResponse(Call<List<BaoHong>> call, Response<List<BaoHong>> response) {
                baoHongList = response.body();
                if (getContext() != null && baoHongList != null) {
                    System.out.println(baoHongList.size() + "");
                    AdapterBaoLoi adapterBaoLoi = new AdapterBaoLoi(baoHongList);
                    rvHome.setAdapter(adapterBaoLoi);
                }
            }

            @Override
            public void onFailure(Call<List<BaoHong>> call, Throwable t) {

            }
        });
    }

}