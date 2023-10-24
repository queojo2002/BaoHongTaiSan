package com.example.baohongtaisan_2.Adapter.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baohongtaisan_2.Activity.Admin.Phong.AdminPhongEditActivity;
import com.example.baohongtaisan_2.Adapter.User.AdapterBaoLoi;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBaoHongAdapter extends RecyclerView.Adapter<AdminBaoHongAdapter.AdminBaoHongViewHolder> {

    private List<BaoHong> baoHongList;
    private Context context;
    private View view;

    public AdminBaoHongAdapter(List<BaoHong> baoHongList) {
        this.baoHongList = baoHongList;
    }


    @NonNull
    @Override
    public AdminBaoHongAdapter.AdminBaoHongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_baohong_admin, parent, false);
        context = parent.getContext();
        return new AdminBaoHongAdapter.AdminBaoHongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminBaoHongAdapter.AdminBaoHongViewHolder holder, int position) {
        if (baoHongList == null) {
            return;
        }
        AdminBaoHongAdapter.AdminBaoHongViewHolder holder_1 = holder;
        BaoHong baoHong = baoHongList.get(position);
        Glide.with(context).load(baoHong.getHinhAnh()).error(R.drawable.baseline_inventory_24).into(holder.imgQLBH_BaoHong);
        holder.txtQLBH_TenTS.setText(baoHong.getTenTS());
        holder.txtQLBH_TenP.setText(baoHong.getTenP());
        holder.txtQLBH_Time.setText(baoHong.getNgayCapNhat());
        holder.btnTT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.edit_data_trangthai_baoloi_byMaBL(baoHong.getMaBL(),baoHong.getTrangThai()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                        ObjectReponse objectReponse = response.body();
                        if(objectReponse.getCode() == 1){
                            if (baoHong.getTrangThai() != 1){
                                Toast.makeText(context, "Không thể thay đổi .", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                baoHong.setTrangThai(2);
                                Toast.makeText(context, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                                System.out.println("trag thai "+baoHong.getTrangThai());
                                notifyDataSetChanged();

                            }
                        }
                        else {
                            Toast.makeText(context, objectReponse.getMessage() , Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ObjectReponse> call, Throwable t) {
                        Toast.makeText(context, "Cập nhật thất bại ...", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        holder.btnTT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.edit_data_trangthai_baoloi_byMaBL(baoHong.getMaBL(),baoHong.getTrangThai()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                        ObjectReponse objectReponse = response.body();
                        if(objectReponse.getCode() == 1){
                            if (baoHong.getTrangThai() != 2){
                                Toast.makeText(context, "Không thể thay đổi .", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                baoHong.setTrangThai(3);
                                Toast.makeText(context, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();

                            }
                        }
                        else {
                            Toast.makeText(context, objectReponse.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectReponse> call, Throwable t) {
                        Toast.makeText(context, "Cập nhật thất bại ...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.btnTT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.edit_data_trangthai_baoloi_byMaBL(baoHong.getMaBL(),baoHong.getTrangThai()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                        ObjectReponse objectReponse = response.body();
                        if(objectReponse.getCode() == 1){
                            if (baoHong.getTrangThai() != 3){
                                Toast.makeText(context, "Không thể thay đổi .", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                baoHong.setTrangThai(4);
                                Toast.makeText(context, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();

                            }
                        }
                        else {
                            Toast.makeText(context, objectReponse.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectReponse> call, Throwable t) {
                        Toast.makeText(context, "Cập nhật thất bại ...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.btnTT5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.edit_data_trangthai_baoloi_byMaBL(baoHong.getMaBL(),baoHong.getTrangThai()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                        ObjectReponse objectReponse = response.body();
                        if(objectReponse.getCode() == 1){
                            if (baoHong.getTrangThai() != 4){
                                Toast.makeText(context, "Không thể thay đổi .", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                baoHong.setTrangThai(5);
                                Toast.makeText(context, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();

                            }
                        }
                        else {
                            Toast.makeText(context, objectReponse.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectReponse> call, Throwable t) {
                        Toast.makeText(context, "Cập nhật thất bại ...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        if (baoHong.getTinhTrang() == 1) {
            holder.txtQLBH_TinhTrang.setText("Hư hỏng nhẹ (Minor)");
        } else if (baoHong.getTinhTrang() == 2) {
            holder.txtQLBH_TinhTrang.setText("Hư hỏng trung bình (Moderate)");
        } else if (baoHong.getTinhTrang() == 3) {
            holder.txtQLBH_TinhTrang.setText("Hư hỏng nghiêm trọng (Severe)");
        } else if (baoHong.getTinhTrang() == 4) {
            holder.txtQLBH_TinhTrang.setText("Hư hỏng hoàn toàn (Critical)");
        }

        if (baoHong.getTrangThai() == 1) {
            holder.txtQLBH_TrangThai.setText("Đã gửi báo hỏng");
        } else if (baoHong.getTrangThai() == 2) {
            holder.txtQLBH_TrangThai.setText("Đã tiếp nhận báo hỏng");
        } else if (baoHong.getTrangThai() == 3) {
            holder.txtQLBH_TrangThai.setText("Đang sửa chữa");
        } else if (baoHong.getTrangThai() == 4) {
            holder.txtQLBH_TrangThai.setText("Sửa thành công");
        } else if (baoHong.getTrangThai() == 5) {
            holder.txtQLBH_TrangThai.setText("Sửa không thành công");
        }
    }

    @Override
    public int getItemCount() {
        if (baoHongList != null) {
            return baoHongList.size();
        }
        return 0;
    }


    public void searchDataList(ArrayList<BaoHong> searchlist) {
        baoHongList = searchlist;
        notifyDataSetChanged();
    }

    public BaoHong getItem(int position) {
        return baoHongList.get(position);
    }


    public class AdminBaoHongViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgQLBH_BaoHong;
        TextView txtQLBH_TenTS, txtQLBH_TenP, txtQLBH_Time, txtQLBH_TrangThai, txtQLBH_TinhTrang;
        Button btnTT2, btnTT3, btnTT4, btnTT5;

        public AdminBaoHongViewHolder(@NonNull View itemView) {
            super(itemView);
            imgQLBH_BaoHong = itemView.findViewById(R.id.imgQL_BaoHong);
            txtQLBH_TenTS = itemView.findViewById(R.id.txtQLBH_TenTS);
            txtQLBH_TenP = itemView.findViewById(R.id.txtQLBH_TenP);
            txtQLBH_Time = itemView.findViewById(R.id.txtQLBH_Time);
            txtQLBH_TrangThai = itemView.findViewById(R.id.txtQLBH_TrangThai);
            txtQLBH_TinhTrang = itemView.findViewById(R.id.txtQLBH_TinhTrang);
            btnTT2 = itemView.findViewById(R.id.btnTiepnhan);
            btnTT3 = itemView.findViewById(R.id.btnDangsua);
            btnTT4 = itemView.findViewById(R.id.btnThanhcong);
            btnTT5 = itemView.findViewById(R.id.btnKhongthanhcong);

        }
    }

}

