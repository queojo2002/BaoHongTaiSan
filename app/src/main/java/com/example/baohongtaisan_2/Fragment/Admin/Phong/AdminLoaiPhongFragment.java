package com.example.baohongtaisan_2.Fragment.Admin.Phong;

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

import com.example.baohongtaisan_2.Adapter.Admin.AdminLoaiPhongAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminLoaiPhongFragment extends Fragment {

    private androidx.appcompat.widget.SearchView SVlp;
    private RecyclerView rcvLP;
    private AdminLoaiPhongAdapter lp_adapter;
    private List<LoaiPhong> listLP;
    private FloatingActionButton btnaddLp;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_admin_loai_phong, container, false);
        _AnhXa();
        _SuKien();

        return rootView;

    }

    private void _AnhXa() {
        rcvLP = rootView.findViewById(R.id.rvLoaiphong);

        SVlp = rootView.findViewById(R.id.txtSearchLoaiphong);

        btnaddLp = rootView.findViewById(R.id.btnloaiphong_add);

        LinearLayoutManager linearLayoutManagerLP = new LinearLayoutManager(requireContext());

        rcvLP.setLayoutManager(linearLayoutManagerLP);

        listLP = new ArrayList<>();

        GetListLP();
    }

    private void _SuKien() {

        btnaddLp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Add();
            }
        });

        SVlp.clearFocus();
        SVlp.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<LoaiPhong> searchlist = new ArrayList<>();
                for (LoaiPhong loaiPhong : listLP) {
                    if (loaiPhong.getTenLP().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(loaiPhong);
                    }
                }
                if (lp_adapter != null) {
                    lp_adapter.searchDataList(searchlist);
                }
                return false;
            }
        });


    }


    public void GetListLP() {
        ApiServices.apiServices.get_list_loaiphong().enqueue(new Callback<List<LoaiPhong>>() {
            @Override
            public void onResponse(Call<List<LoaiPhong>> call, Response<List<LoaiPhong>> response) {
                listLP.clear();
                listLP = response.body();
                if (listLP != null && getContext() != null) {
                    lp_adapter = new AdminLoaiPhongAdapter(listLP, new RCVClickItem() {
                        @Override
                        public void onClickRCV(Object object, String CURD) {
                            LoaiPhong loaiPhong = (LoaiPhong) object;
                            if (CURD.equals("EDIT")) {
                                Open_Dialog_Edit(loaiPhong);
                            } else if (CURD.equals("DELETE")) {
                                Open_Dialog_Delete(loaiPhong);
                            }
                        }
                    });
                    rcvLP.setAdapter(lp_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<LoaiPhong>> call, Throwable t) {
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

        tv.setText("Thêm mới loại phòng");
        btnthemmoi.setText("Thêm mới");

        btnthemmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.add_loaiphong(txtinput.getText().toString()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(getContext(), "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            GetListLP();
                        } else {
                            Toast.makeText(getContext(), objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Thêm mới thất bại !", Toast.LENGTH_SHORT).show();
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

    public void Open_Dialog_Edit(LoaiPhong loaiPhong) {
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
        txtinput.setText(loaiPhong.getTenLP());
        btnchinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.edit_loaiphong(loaiPhong.getMaLP(), txtinput.getText().toString()).enqueue(new Callback<ObjectReponse>() {
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

    public void Open_Dialog_Delete(LoaiPhong lp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
        builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiServices.apiServices.delete_loaiphong(lp.getMaLP()).enqueue(new Callback<ObjectReponse>() {
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
        GetListLP();
    }
}