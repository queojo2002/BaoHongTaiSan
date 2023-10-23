package com.example.baohongtaisan_2.Fragment.Admin.NguoiDung;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.baohongtaisan_2.Adapter.Admin.AdminNguoiDungAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminDanhSachNguoiDungFragment extends Fragment {

    private SearchView SVnd;
    private RecyclerView rcvND;
    private AdminNguoiDungAdapter adminNguoiDung_adapter;
    private List<NguoiDung> listND;
    private Button btnaddNd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_danh_sach_nguoi_dung, container, false);
        initializeViews(rootView);
        setupRecyclerView();

        setupSearchViewListeners();
        setupButtonClickListeners();

        return rootView;
    }

    private void initializeViews(View rootView) {
        rcvND = rootView.findViewById(R.id.rvNguoidung);

        SVnd = rootView.findViewById(R.id.txtSearchND);

        btnaddNd = rootView.findViewById(R.id.btnnd_add);
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManagerND = new LinearLayoutManager(requireContext());

        rcvND.setLayoutManager(linearLayoutManagerND);

        listND = new ArrayList<>();
        adminNguoiDung_adapter = new AdminNguoiDungAdapter(listND);
        rcvND.setAdapter(adminNguoiDung_adapter);

        GetListNguoiDung();
    }

    private void setupSearchViewListeners() {
        SVnd.clearFocus();
        SVnd.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchNguoidung(s);
                return false;
            }
        });
    }

    private void setupButtonClickListeners() {
        btnaddNd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getContext(), AddNguoidungActivity.class);
                startActivity(intent);*/
            }
        });
    }

    public void GetListNguoiDung() {
        ApiServices.apiServices.get_list_nguoidung().enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                listND.clear();
                listND = response.body();
                if (listND != null && getContext() != null) {
                    adminNguoiDung_adapter = new AdminNguoiDungAdapter(listND);
                    rcvND.setAdapter(adminNguoiDung_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchNguoidung(String text) {
        ArrayList<NguoiDung> searchlist = new ArrayList<>();
        for (NguoiDung nguoiDung : listND) {
            if (nguoiDung.getHoVaTen().toLowerCase().contains(text.toLowerCase())) {
                searchlist.add(nguoiDung);
            }
        }
        adminNguoiDung_adapter.searchDataList(searchlist);
    }


    @Override
    public void onResume() {
        super.onResume();
        GetListNguoiDung();

    }
}