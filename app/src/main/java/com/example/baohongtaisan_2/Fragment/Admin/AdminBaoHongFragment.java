package com.example.baohongtaisan_2.Fragment.Admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baohongtaisan_2.Adapter.Admin.AdminBaoHongAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.CustomSpinnerAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.Arrays;
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
    private List<BaoHong> originalBaoHongList;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_bao_hong, container, false);
        _AnhXa();
        GetListBaoHong();
        return view;
    }


    public void _AnhXa() {
        rvBH = view.findViewById(R.id.rvBaohong);
        svBH = view.findViewById(R.id.txtSearchBH);

        linearLayoutManager = new LinearLayoutManager(requireContext());
        rvBH.setLayoutManager(linearLayoutManager);

        baoHongList = new ArrayList<>();
        adminBaoHongAdapter = new AdminBaoHongAdapter(baoHongList);
        rvBH.setAdapter(adminBaoHongAdapter);

        Spinner spinner1 = view.findViewById(R.id.spntinhtrang);
        ArrayAdapter<CharSequence> adapter1 = new CustomSpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1, Arrays.asList(getResources().getStringArray(R.array.tinh_trang_array)), 10);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        Spinner spinner2 = view.findViewById(R.id.spntrangthai);
        ArrayAdapter<CharSequence> adapter2 = new CustomSpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1, Arrays.asList(getResources().getStringArray(R.array.trang_thai_array)), 10);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedTinhTrang = spinner1.getSelectedItem().toString();
                int tinhTrangToSearch = getTinhTrangValue(selectedTinhTrang);
                String selectedTrangThai = spinner2.getSelectedItem().toString();
                int trangThaiToSearch = getTrangThaiValue(selectedTrangThai);
                ((TextView) parentView.getChildAt(0)).setTextSize(10);
                filterData(tinhTrangToSearch, trangThaiToSearch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedTinhTrang = spinner1.getSelectedItem().toString();
                int tinhTrangToSearch = getTinhTrangValue(selectedTinhTrang);
                String selectedTrangThai = spinner2.getSelectedItem().toString();
                int trangThaiToSearch = getTrangThaiValue(selectedTrangThai);
                ((TextView) parentView.getChildAt(0)).setTextSize(10);

                filterData(tinhTrangToSearch, trangThaiToSearch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

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
                    if (baoHongMatchesSearch(baoHong, s)) {
                        searchlist.add(baoHong);
                    }
                }
                adminBaoHongAdapter.searchDataList(searchlist);
                return false;
            }

            private boolean baoHongMatchesSearch(BaoHong baoHong, String query) {
                // Thay đổi logic tìm kiếm.
                String queryLower = query.toLowerCase();
                String tenP = baoHong.getTenP().toLowerCase();
                String tenTS = baoHong.getTenTS().toLowerCase();
                String ngay = baoHong.getNgayCapNhat().toLowerCase();

                // Sử dụng nhiều thuộc tính cho tìm kiếm phù hợp.
                if (tenP.contains(queryLower) || tenTS.contains(queryLower) || ngay.contains(queryLower)) {
                    return true;
                }
                return false;
            }
        });

    }



    public void GetListBaoHong() {
        ApiServices.apiServices.get_list_baohong().enqueue(new Callback<List<BaoHong>>() {
            @Override
            public void onResponse(Call<List<BaoHong>> call, Response<List<BaoHong>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    originalBaoHongList = response.body();
                    baoHongList.clear();
                    baoHongList.addAll(originalBaoHongList);
                    if (baoHongList != null && getContext() != null) {
                        adminBaoHongAdapter = new AdminBaoHongAdapter(baoHongList);
                        rvBH.setAdapter(adminBaoHongAdapter);
                    }
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


    @Override
    public void onResume() {
        super.onResume();
        GetListBaoHong();

    }
    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }
    private int getTinhTrangValue(String tinhTrang) {
        if (tinhTrang.equals("Hư hỏng nhẹ (Minor)")) {
            return 1;
        } else if (tinhTrang.equals("Hư hỏng trung bình (Moderate)")) {
            return 2;
        } else if (tinhTrang.equals("Hư hỏng nghiêm trọng (Severe)")) {
            return 3;
        } else if (tinhTrang.equals("Hư hỏng hoàn toàn (Critical)")) {
            return 4;
        } else if (tinhTrang.equals("--Lọc theo tình trạng--")) {
            return 0;
        }
        return -1;
    }


    private int getTrangThaiValue(String tinhTrang) {
        if (tinhTrang.equals("Đã gửi báo hỏng")) {
            return 1;
        } else if (tinhTrang.equals("Đã tiếp nhận báo hỏng")) {
            return 2;
        } else if (tinhTrang.equals("Đang sửa chữa")) {
            return 3;
        } else if (tinhTrang.equals("Sửa thành công")) {
            return 4;
        } else if (tinhTrang.equals("Sửa không thành công")) {
            return 5;
        } else if (tinhTrang.equals("--Lọc theo trạng thái--")) {
            return 0;
        }
        return -1;
    }
    private void filterData(int tinhTrangToSearch, int trangThaiToSearch) {
        if (tinhTrangToSearch == 0 && trangThaiToSearch == 0) {
            // Khi chọn cả "--Lọc theo tình trạng--" và "--Lọc theo trạng thái--"
            baoHongList.clear();
            baoHongList.addAll(originalBaoHongList);
        } else {
            if (originalBaoHongList != null) {
                baoHongList.clear();
                for (BaoHong baoHong : originalBaoHongList) {
                    if ((tinhTrangToSearch == 0 || baoHong.getTinhTrang() == tinhTrangToSearch) &&
                            (trangThaiToSearch == 0 || baoHong.getTrangThai() == trangThaiToSearch)) {
                        baoHongList.add(baoHong);
                    }
                }
            }
        }
        adminBaoHongAdapter.notifyDataSetChanged();
    }
}