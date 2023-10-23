package com.example.baohongtaisan_2.Adapter.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Activity.Admin.Phong.AdminPhongEditActivity;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPhongAdapter extends RecyclerView.Adapter<AdminPhongAdapter.AdminPhongViewHolder> {

    private List<Phong> phongList;
    private Context context;
    private View view;

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
                Intent intent = new Intent(context, AdminPhongEditActivity.class);
                Bundle bdPhong = new Bundle();
                bdPhong.putInt("maphong", p.getMaP());
                bdPhong.putString("tenphong", p.getTenP());
                bdPhong.putInt("makvp", p.getMaKVP());
                bdPhong.putInt("malp", p.getMaLP());
                intent.putExtra("dataphong", bdPhong);
                context.startActivity(intent);
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
