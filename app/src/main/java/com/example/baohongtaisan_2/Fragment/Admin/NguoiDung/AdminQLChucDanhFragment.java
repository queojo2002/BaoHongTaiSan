package com.example.baohongtaisan_2.Fragment.Admin.NguoiDung;

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
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.Admin.AdminChucDanhAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ChucDanh;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminQLChucDanhFragment extends Fragment {

    private SearchView SVcd;
    private RecyclerView rcvCD;
    private AdminChucDanhAdapter adminChucDanh_adapter;
    private List<ChucDanh> listCD;
    private Button btnaddCd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_q_l_chuc_danh, container, false);
        initializeViews(rootView);
        setupRecyclerView();

        setupSearchViewListeners();
        setupButtonClickListeners();

        return rootView;
    }

    private void initializeViews(View rootView) {
        rcvCD = rootView.findViewById(R.id.rvChucdanh);

        SVcd = rootView.findViewById(R.id.txtSearchCD);

        btnaddCd = rootView.findViewById(R.id.btncd_add);
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManagerCD = new LinearLayoutManager(requireContext());

        rcvCD.setLayoutManager(linearLayoutManagerCD);

        listCD = new ArrayList<>();
        adminChucDanh_adapter = new AdminChucDanhAdapter(listCD);
        rcvCD.setAdapter(adminChucDanh_adapter);

        GetListChucDanh();
    }

    private void setupSearchViewListeners() {
        SVcd.clearFocus();
        SVcd.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchChucDanh(s);
                return false;
            }
        });
    }

    private void setupButtonClickListeners() {

        btnaddCd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Add();
            }
        });
    }

    public void GetListChucDanh() {
        ApiServices.apiServices.get_list_chucdanh().enqueue(new Callback<List<ChucDanh>>() {
            @Override
            public void onResponse(Call<List<ChucDanh>> call, Response<List<ChucDanh>> response) {
                listCD.clear();
                listCD = response.body();
                if (listCD != null && getContext() != null) {
                    adminChucDanh_adapter = new AdminChucDanhAdapter(listCD);
                    rcvCD.setAdapter(adminChucDanh_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<ChucDanh>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchChucDanh(String text) {
        ArrayList<ChucDanh> searchlist = new ArrayList<>();
        for (ChucDanh chucDanh : listCD) {
            if (chucDanh.getTenCD().toLowerCase().contains(text.toLowerCase())) {
                searchlist.add(chucDanh);
            }
        }
        adminChucDanh_adapter.searchDataList(searchlist);
    }

    public void Open_Dialog_Add()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_edit);

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

        TextView tv = dialog.findViewById(R.id.tvTenChucNangEdit);
        EditText txtinput = dialog.findViewById(R.id.txtInput);
        Button btnhuybo =dialog.findViewById(R.id.btnHuyBo);
        Button btnthemmoi = dialog.findViewById(R.id.btnEdit);

        tv.setText("Thêm mới chức danh");
        txtinput.setHint("Nhập tên chức danh");
        btnthemmoi.setText("Thêm mới");

        btnthemmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.add_chucdanh(txtinput.getText().toString(), null).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(getContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            GetListChucDanh();
                        } else {
                            Toast.makeText(getContext(), objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

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
        GetListChucDanh();
    }
}