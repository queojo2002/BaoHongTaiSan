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

import com.example.baohongtaisan_2.Activity.Admin.NguoiDung.AdminChucDanhEditActivity;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ChucDanh;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminChucDanhAdapter extends RecyclerView.Adapter<AdminChucDanhAdapter.ChucdanhViewHolder> {

    private List<ChucDanh> listChucdanh;
    private Context context;

    public AdminChucDanhAdapter(List<ChucDanh> listChucdanh) {
        this.listChucdanh = listChucdanh;
    }

    @NonNull
    @Override
    public ChucdanhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_chucdanh_admin, parent, false);
        context = parent.getContext();
        return new ChucdanhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChucdanhViewHolder holder, int position) {
        int vitri = position;
        ChucDanh cd = listChucdanh.get(position);
        if (cd == null) {
            return;
        }
        holder.macd.setText(cd.getMaCD() + "");
        holder.tencd.setText(cd.getTenCD());
        holder.editcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent intent = new Intent(context, AdminChucDanhEditActivity.class);
                Bundle bdChucdanh = new Bundle();
                bdChucdanh.putInt("macd", cd.getMaCD());
                bdChucdanh.putString("tencd", cd.getTenCD());
                bdChucdanh.putString("motacd", cd.getMoTaCD());
                intent.putExtra("datachucdanh", bdChucdanh);
                context.startActivity(intent);
            }
        });
        holder.deletecd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tencd.getContext());
                builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
                builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiServices.apiServices.delete_chucdanh(cd.getMaCD()).enqueue(new Callback<ObjectReponse>() {
                            @Override
                            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                                ObjectReponse objectadd = response.body();
                                if (objectadd.getCode() == 1) {
                                    Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                                    listChucdanh.remove(vitri);
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
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.tencd.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listChucdanh != null) {
            return listChucdanh.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<ChucDanh> searchlist) {
        listChucdanh = searchlist;
        notifyDataSetChanged();
    }

    public ChucDanh getItem(int position) {
        return listChucdanh.get(position);
    }

    public class ChucdanhViewHolder extends RecyclerView.ViewHolder {
        private final TextView macd;
        private final TextView tencd;
        private final ImageView editcd;
        private final ImageView deletecd;

        public ChucdanhViewHolder(@NonNull View itemView) {
            super(itemView);
            macd = itemView.findViewById(R.id.Macd);
            tencd = itemView.findViewById(R.id.txtTencd);
            editcd = itemView.findViewById(R.id.Editcd);
            deletecd = itemView.findViewById(R.id.Deletecd);
        }
    }
}
