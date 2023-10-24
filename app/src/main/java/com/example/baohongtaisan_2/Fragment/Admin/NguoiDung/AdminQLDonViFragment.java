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

import com.example.baohongtaisan_2.Adapter.Admin.AdminDonViAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.DonVi;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminQLDonViFragment extends Fragment {

    private SearchView SVdv;
    private RecyclerView rcvDV;
    private AdminDonViAdapter adminDonVi_adapter;
    private List<DonVi> listDV;
    private Button btnaddDv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_q_l_don_vi, container, false);
        initializeViews(rootView);
        setupRecyclerView();

        setupSearchViewListeners();
        setupButtonClickListeners();

        return rootView;
    }

    private void initializeViews(View rootView) {
        rcvDV = rootView.findViewById(R.id.rvDonvi);

        SVdv = rootView.findViewById(R.id.txtSearchDV);

        btnaddDv = rootView.findViewById(R.id.btndv_add);
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManagerDV = new LinearLayoutManager(requireContext());

        rcvDV.setLayoutManager(linearLayoutManagerDV);

        listDV = new ArrayList<>();
        adminDonVi_adapter = new AdminDonViAdapter(listDV);
        rcvDV.setAdapter(adminDonVi_adapter);
        GetListDonVi();
    }

    private void setupSearchViewListeners() {
        SVdv.clearFocus();
        SVdv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchDonVi(s);
                return false;
            }
        });
    }

    private void setupButtonClickListeners() {
        btnaddDv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Add();
            }
        });
    }

    public void GetListDonVi() {
        ApiServices.apiServices.get_list_donvi().enqueue(new Callback<List<DonVi>>() {
            @Override
            public void onResponse(Call<List<DonVi>> call, Response<List<DonVi>> response) {
                listDV.clear();
                listDV = response.body();
                if (listDV != null && getContext() != null) {
                    adminDonVi_adapter = new AdminDonViAdapter(listDV);
                    rcvDV.setAdapter(adminDonVi_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<DonVi>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchDonVi(String text) {
        ArrayList<DonVi> searchlist = new ArrayList<>();
        for (DonVi donVi : listDV) {
            if (donVi.getTenDV().toLowerCase().contains(text.toLowerCase())) {
                searchlist.add(donVi);
            }
        }
        adminDonVi_adapter.searchDataList(searchlist);
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

        tv.setText("Thêm mới đơn vị");
        btnthemmoi.setText("Thêm mới");

        btnthemmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.add_donvi(txtinput.getText().toString(), null).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(getContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            GetListDonVi();
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
        GetListDonVi();

    }
}