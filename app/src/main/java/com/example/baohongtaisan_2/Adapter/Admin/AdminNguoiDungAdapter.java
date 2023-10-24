package com.example.baohongtaisan_2.Adapter.Admin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Activity.Admin.NguoiDung.AdminNguoiDungEditActivity;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerChucDanhAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerDonViAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerPhanQuyenAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ChucDanh;
import com.example.baohongtaisan_2.Model.DonVi;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.PhanQuyen;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminNguoiDungAdapter extends RecyclerView.Adapter<AdminNguoiDungAdapter.NguoidungViewHolder> {

    private List<NguoiDung> listNguoidung;
    private List<DonVi> donViList;
    private List<ChucDanh> chucDanhList;
    private List<PhanQuyen> phanQuyenList;
    private int MaDV = -1, MaCD = -1, MaPQ = -1;
    private String TenDV_Edit = "", TenCD_Edit= "", TenPQ_Edit= "";
    private Context context;

    public AdminNguoiDungAdapter(List<NguoiDung> listNguoidung) {
        this.listNguoidung = listNguoidung;
    }

    @NonNull
    @Override
    public NguoidungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_nguoidung_admin, parent, false);
        context = parent.getContext();
        return new NguoidungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoidungViewHolder holder, int position) {
        int vitri = position;
        NguoiDung nd = listNguoidung.get(position);
        if (nd == null) {
            return;
        }
        holder.tennd.setText(nd.getHoVaTen());
        holder.editnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Edit(nd,vitri);

            }
        });
       /*
        holder.deletend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tennd.getContext());
                builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
                builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        *//*Xử lý*//*
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.tennd.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        if (listNguoidung != null) {
            return listNguoidung.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<NguoiDung> searchlist) {
        listNguoidung = searchlist;
        notifyDataSetChanged();
    }

    public NguoiDung getItem(int position) {
        return listNguoidung.get(position);
    }

    public void Open_Dialog_Edit(NguoiDung nd, int vitri) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_nguoidung_add);

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

        TextView tv = dialog.findViewById(R.id.tvAdd_TenChucNangEdit);
        EditText txtinput = dialog.findViewById(R.id.txtAdd_Input);
        Button btnhuybo = dialog.findViewById(R.id.btnAdd_HuyBo);
        Button btnthem = dialog.findViewById(R.id.btnAdd);

        Spinner spnDV = dialog.findViewById(R.id.spnAdd_1);
        Spinner spnCD = dialog.findViewById(R.id.spnAdd_2);
        Spinner spnPQ = dialog.findViewById(R.id.spnAdd_3);

        btnthem.setText("Chỉnh sửa");
        tv.setText("Chỉnh sửa thông tin người dùng");

        Load_Data_DonVi(spnDV, nd.getMaDV());
        Load_Data_ChucDanh(spnCD, nd.getMaCD());
        Load_Data_PhanQuyen(spnPQ, nd.getMaPQ());
        txtinput.setText(nd.getHoVaTen());



        spnDV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<DonVi> adapter = (ArrayAdapter<DonVi>) spnDV.getAdapter();
                DonVi selected = adapter.getItem(i);
                if (selected != null) {
                    MaDV = selected.getMaDV();
                    TenDV_Edit = selected.getTenDV();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spnCD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<ChucDanh> adapter = (ArrayAdapter<ChucDanh>) spnCD.getAdapter();
                ChucDanh selected = adapter.getItem(i);
                if (selected != null) {
                    MaCD = selected.getMaCD();
                    TenCD_Edit = selected.getTenCD();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spnPQ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<PhanQuyen> adapter = (ArrayAdapter<PhanQuyen>) spnPQ.getAdapter();
                PhanQuyen selected = adapter.getItem(i);
                if (selected != null) {
                    MaPQ = selected.getMaPQ();
                    TenPQ_Edit = selected.getTenPQ();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MaCD == -1 || MaDV == -1 || MaPQ == -1 || TextUtils.isEmpty(txtinput.getText().toString()))
                {
                    Toast.makeText(context, "Dữ liệu sửa người dùng của bạn không đúng !!!", Toast.LENGTH_SHORT).show();
                }else
                {

                    ApiServices.apiServices.edit_data_nguoidung(nd.getMaND(), txtinput.getText().toString(), MaDV, MaCD, MaPQ).enqueue(new Callback<ObjectReponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                            ObjectReponse objectEdit = response.body();
                            if (objectEdit == null) return;
                            if (objectEdit.getCode() == 1) {
                                Toast.makeText(context, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                listNguoidung.get(vitri).setHoVaTen(txtinput.getText().toString());
                                listNguoidung.get(vitri).setTenDV(TenDV_Edit);
                                listNguoidung.get(vitri).setTenCD(TenCD_Edit);
                                listNguoidung.get(vitri).setTenPQ(TenPQ_Edit);

                                listNguoidung.get(vitri).setMaDV(MaDV);
                                listNguoidung.get(vitri).setMaCD(MaCD);
                                listNguoidung.get(vitri).setMaPQ(MaPQ);
                                notifyItemChanged(vitri);
                            } else {
                                Toast.makeText(context, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                            Toast.makeText(context, "Có lỗi khi cập nhật người dùng!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
    private void Load_Data_DonVi(Spinner spinner, int MaDV) {
        ApiServices.apiServices.get_list_donvi().enqueue(new Callback<List<DonVi>>() {
            @Override
            public void onResponse(@NonNull Call<List<DonVi>> call, @NonNull Response<List<DonVi>> response) {
                if (response.isSuccessful())
                {
                    donViList = response.body();
                    SpinnerDonViAdapter spinnerDonViAdapter = new SpinnerDonViAdapter(context, R.layout.custom_spinner_selected, donViList);
                    spinner.setAdapter(spinnerDonViAdapter);
                    for (int i = 0; i < donViList.size(); i++) {
                        if (donViList.get(i).getMaDV() == MaDV)
                        {
                            spinner.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DonVi>> call, @NonNull Throwable t) {

            }
        });
    }

    private void Load_Data_ChucDanh(Spinner spinner, int MaCD) {
        ApiServices.apiServices.get_list_chucdanh().enqueue(new Callback<List<ChucDanh>>() {
            @Override
            public void onResponse(@NonNull Call<List<ChucDanh>> call, @NonNull Response<List<ChucDanh>> response) {
                if (response.isSuccessful())
                {
                    chucDanhList = response.body();
                    SpinnerChucDanhAdapter spinnerChucDanhAdapter = new SpinnerChucDanhAdapter(context, R.layout.custom_spinner_selected, chucDanhList);
                    spinner.setAdapter(spinnerChucDanhAdapter);
                    for (int i = 0; i < chucDanhList.size(); i++) {
                        if (chucDanhList.get(i).getMaCD() == MaCD)
                        {
                            spinner.setSelection(i);
                            break;
                        }
                    }

                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ChucDanh>> call, @NonNull Throwable t) {

            }
        });
    }


    private void Load_Data_PhanQuyen(Spinner spinner, int MaPQ) {
        ApiServices.apiServices.get_list_phanquyen().enqueue(new Callback<List<PhanQuyen>>() {
            @Override
            public void onResponse(@NonNull Call<List<PhanQuyen>> call, @NonNull Response<List<PhanQuyen>> response) {
                if (response.isSuccessful())
                {
                    phanQuyenList = response.body();
                    SpinnerPhanQuyenAdapter spinnerPhanQuyenAdapter = new SpinnerPhanQuyenAdapter(context, R.layout.custom_spinner_selected, phanQuyenList);
                    spinner.setAdapter(spinnerPhanQuyenAdapter);
                    for (int i = 0; i < phanQuyenList.size(); i++) {
                        if (phanQuyenList.get(i).getMaPQ() == MaPQ)
                        {
                            spinner.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PhanQuyen>> call, @NonNull Throwable t) {

            }
        });
    }

    public class NguoidungViewHolder extends RecyclerView.ViewHolder {
        private TextView tennd;
        private ImageView editnd, deletend;

        public NguoidungViewHolder(@NonNull View itemView) {
            super(itemView);
            tennd = itemView.findViewById(R.id.txtTenND);
            editnd = itemView.findViewById(R.id.editND);
            deletend = itemView.findViewById(R.id.deleteND);
        }
    }
}
