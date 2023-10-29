package com.example.baohongtaisan_2.Fragment.Admin.TaiSan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.example.baohongtaisan_2.Adapter.Admin.AdminLoaiTaiSanAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.LoaiTaiSan;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminQLLoaiTaiSanFragment extends Fragment {

    private SearchView SVlts;
    private RecyclerView rcvLTS;
    private AdminLoaiTaiSanAdapter lts_adapter;
    private List<LoaiTaiSan> listLTS;
    private Button btnaddlts;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin_q_l_loai_tai_san, container, false);
        _AnhXa();
        _SuKien();
        return rootView;
    }


    private void _AnhXa() {
        rcvLTS = rootView.findViewById(R.id.rvLoaiTS);

        SVlts = rootView.findViewById(R.id.txtSearchLoaiTS);

        btnaddlts = rootView.findViewById(R.id.btnloaits_add);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

        rcvLTS.setLayoutManager(linearLayoutManager);

        listLTS = new ArrayList<>();


        GetListLTS();

    }

    private void _SuKien() {

        btnaddlts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Add();
            }
        });

        SVlts.clearFocus();
        SVlts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<LoaiTaiSan> searchlist = new ArrayList<>();
                for (LoaiTaiSan loaiTaiSan : listLTS) {
                    if (loaiTaiSan.getTenLTS().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(loaiTaiSan);
                    }
                }
                if (lts_adapter != null) {
                    lts_adapter.searchDataList(searchlist);
                }
                return false;
            }
        });

    }


    public void GetListLTS() {
        ApiServices.apiServices.get_list_loaitaisan().enqueue(new Callback<List<LoaiTaiSan>>() {
            @Override
            public void onResponse(Call<List<LoaiTaiSan>> call, Response<List<LoaiTaiSan>> response) {
                listLTS.clear();
                listLTS = response.body();
                if (listLTS != null && getContext() != null) {
                    lts_adapter = new AdminLoaiTaiSanAdapter(listLTS, new RCVClickItem() {
                        @Override
                        public void onClickRCV(Object object, String CURD) {
                            LoaiTaiSan loaiTaiSan = (LoaiTaiSan) object;
                            if (CURD.equals("EDIT")) {
                                Open_Dialog_Edit(loaiTaiSan);
                            } else if (CURD.equals("DELETE")) {
                                Open_Dialog_Delete(loaiTaiSan);
                            }
                        }
                    });
                    rcvLTS.setAdapter(lts_adapter);
                }
            }

            @Override
            public void onFailure(Call<List<LoaiTaiSan>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void Open_Dialog_Add() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_edit);

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

        TextView tv = dialog.findViewById(R.id.tvTenChucNangEdit);
        EditText txtinput = dialog.findViewById(R.id.txtInput);
        Button btnhuybo = dialog.findViewById(R.id.btnHuyBo);
        Button btnthemmoi = dialog.findViewById(R.id.btnEdit);

        tv.setText("Thêm mới loại tài sản");
        txtinput.setHint("Nhập tên loại tài sản");
        btnthemmoi.setText("Thêm mới");

        btnthemmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.add_data_loaitaisan(txtinput.getText().toString()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(getContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            GetListLTS();
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


    public void Open_Dialog_Edit(LoaiTaiSan lts) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_edit);

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

        EditText txtinput = dialog.findViewById(R.id.txtInput);
        Button btnhuybo = dialog.findViewById(R.id.btnHuyBo);
        Button btnchinhsua = dialog.findViewById(R.id.btnEdit);

        txtinput.setText(lts.getTenLTS());
        txtinput.setHint("Nhập tên loại tài sản");
        btnchinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.edit_data_loaitaisan_byMaLTS(lts.getMaLTS(), txtinput.getText().toString()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            onResume();
                            Toast.makeText(requireContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
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

        btnhuybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public void Open_Dialog_Delete(LoaiTaiSan loaiTaiSan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
        builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiServices.apiServices.delete_data_loaitaisan(loaiTaiSan.getMaLTS()).enqueue(new retrofit2.Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                        ObjectReponse objectadd = response.body();
                        if (objectadd.getCode() == 1) {
                            onResume();
                            Toast.makeText(requireContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectReponse> call, Throwable t) {
                        Toast.makeText(requireContext(), "Xóa thất bại !", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(requireContext(), "Đã hủy", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        GetListLTS();

    }
}