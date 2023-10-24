package com.example.baohongtaisan_2.Adapter.Admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerKhuVucPhong_Adapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerLoaiPhong_Adapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ChucDanh;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.PhanQuyen;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPhongAdapter extends RecyclerView.Adapter<AdminPhongAdapter.AdminPhongViewHolder> {

    private List<Phong> phongList;
    private List<KhuVucPhong> khuVucPhongList;
    private List<LoaiPhong> loaiPhongList;
    private Context context;
    private View view;
    private int MaLP_Edit = -1, MaKVP_Edit = -1;
    private String TenLP_Edit = "", TenKVP_Edit= "";

    public AdminPhongAdapter(List<Phong> phongList) {
        this.phongList = phongList;
    }


    @NonNull
    @Override
    public AdminPhongAdapter.AdminPhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_phong_admin, parent, false);
        context = parent.getContext();
        return new AdminPhongAdapter.AdminPhongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPhongAdapter.AdminPhongViewHolder holder, int position) {
        int vitri = position;
        Phong p = phongList.get(position);
        if (p == null) {
            return;
        }
        holder.tenphong.setText(p.getTenP());
        holder.kvp.setText(p.getTenKVP());
        holder.lp.setText(p.getTenLP());
        holder.editphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Edit(p, vitri);
            }
        });
        holder.deletephong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tenphong.getContext());
                builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
                builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiServices.apiServices.delete_phong(p.getMaP()).enqueue(new Callback<ObjectReponse>() {
                            @Override
                            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                                ObjectReponse objectadd = response.body();
                                if (objectadd.getCode() == 1) {
                                    Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                                    phongList.remove(vitri);
                                    notifyItemRemoved(vitri);
                                } else {
                                    Toast.makeText(context, objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                                Toast.makeText(context, "Xóa thất bại !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        if (phongList != null) {
            return phongList.size();
        }
        return 0;
    }


    public void searchDataList(ArrayList<Phong> searchlist) {
        phongList = searchlist;
        notifyDataSetChanged();
    }

    public Phong getItem(int position) {
        return phongList.get(position);
    }

    public void Open_Dialog_Edit(Phong phong, int vitri) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_add);

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
        Spinner spnLP = dialog.findViewById(R.id.spnAdd_1);
        Spinner spnKVP = dialog.findViewById(R.id.spnAdd_2);
        btnthem.setText("Chỉnh sửa");
        tv.setText("Chỉnh sửa thông tin Phòng");
        Load_Data_LoaiPhong(spnLP, phong.getMaLP());
        Load_Data_KhuVucPhong(spnKVP, phong.getMaKVP());
        txtinput.setText(phong.getTenP());



        spnLP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<LoaiPhong> adapter = (ArrayAdapter<LoaiPhong>) spnLP.getAdapter();
                LoaiPhong selected = adapter.getItem(i);
                if (selected != null) {
                    MaLP_Edit = selected.getMaLP();
                    TenLP_Edit = selected.getTenLP();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spnKVP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<KhuVucPhong> adapter = (ArrayAdapter<KhuVucPhong>) spnKVP.getAdapter();
                KhuVucPhong selected = adapter.getItem(i);
                if (selected != null) {
                    MaKVP_Edit = selected.getMaKVP();
                    TenKVP_Edit = selected.getTenKVP();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });



        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MaLP_Edit == -1 || MaKVP_Edit == -1) return;
                ApiServices.apiServices.edit_phong(phong.getMaP(), txtinput.getText().toString(), MaKVP_Edit, MaLP_Edit).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(context, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            phongList.get(vitri).setTenP(txtinput.getText().toString());
                            phongList.get(vitri).setTenKVP(TenKVP_Edit);
                            phongList.get(vitri).setTenLP(TenLP_Edit);
                            phongList.get(vitri).setMaKVP(MaKVP_Edit);
                            phongList.get(vitri).setMaLP(MaLP_Edit);
                            notifyItemChanged(vitri);
                        } else {
                            Toast.makeText(context, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
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


    private void Load_Data_LoaiPhong(Spinner spinner, int MaLP) {
        ApiServices.apiServices.get_list_loaiphong().enqueue(new Callback<List<LoaiPhong>>() {
            @Override
            public void onResponse(@NonNull Call<List<LoaiPhong>> call, @NonNull Response<List<LoaiPhong>> response) {
                if (response.isSuccessful())
                {
                    loaiPhongList = response.body();
                    SpinnerLoaiPhong_Adapter spinnerLoaiPhongAdapter = new SpinnerLoaiPhong_Adapter(context, R.layout.custom_spinner_selected, loaiPhongList);
                    spinner.setAdapter(spinnerLoaiPhongAdapter);
                    for (int i = 0; i < loaiPhongList.size(); i++) {
                        if (loaiPhongList.get(i).getMaLP() == MaLP)
                        {
                            spinner.setSelection(i);
                            break;
                        }
                    }

                }
            }
            @Override
            public void onFailure(@NonNull Call<List<LoaiPhong>> call, @NonNull Throwable t) {

            }
        });
    }

    private void Load_Data_KhuVucPhong(Spinner spinner, int MaKVP) {
        ApiServices.apiServices.get_list_khuvucphong().enqueue(new Callback<List<KhuVucPhong>>() {
            @Override
            public void onResponse(@NonNull Call<List<KhuVucPhong>> call, @NonNull Response<List<KhuVucPhong>> response) {
                if (response.isSuccessful())
                {
                    khuVucPhongList = response.body();
                    SpinnerKhuVucPhong_Adapter spinner_2 = new SpinnerKhuVucPhong_Adapter(context, R.layout.custom_spinner_selected, khuVucPhongList);
                    spinner.setAdapter(spinner_2);
                    for (int i = 0; i < khuVucPhongList.size(); i++) {
                        if (khuVucPhongList.get(i).getMaKVP() == MaKVP)
                        {
                            spinner.setSelection(i);
                            break;
                        }
                    }

                }
            }
            @Override
            public void onFailure(@NonNull Call<List<KhuVucPhong>> call, @NonNull Throwable t) {

            }
        });
    }







    public class AdminPhongViewHolder extends RecyclerView.ViewHolder {
        private TextView tenphong, kvp, lp;
        private ImageView editphong, deletephong;

        public AdminPhongViewHolder(@NonNull View itemView) {
            super(itemView);
            tenphong = itemView.findViewById(R.id.txtTenPhong);
            kvp = itemView.findViewById(R.id.txtKVP);
            lp = itemView.findViewById(R.id.txtLP);
            editphong = itemView.findViewById(R.id.editPhong);
            deletephong = itemView.findViewById(R.id.deletePhong);
        }
    }

}
