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

import com.example.baohongtaisan_2.Activity.Admin.Phong.AdminKhuVucPhongEditActivity;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminKhuVucPhongAdapter extends RecyclerView.Adapter<AdminKhuVucPhongAdapter.KhuvucphongViewHolder> {

    private List<KhuVucPhong> listKhuvucphong;
    private Context context;

    public AdminKhuVucPhongAdapter(List<KhuVucPhong> listKhuvucphong) {
        this.listKhuvucphong = listKhuvucphong;
    }

    @NonNull
    @Override
    public KhuvucphongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_khuvucphong_admin, parent, false);
        context = parent.getContext();
        return new KhuvucphongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhuvucphongViewHolder holder, int position) {
        int vitri = position;
        KhuVucPhong kv = listKhuvucphong.get(position);
        if (kv == null) {
            return;
        }
        holder.makhuvuc.setText(kv.getMaKVP() + "");
        holder.tenkhuvuc.setText(kv.getTenKVP());
        holder.editkhuvuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AdminKhuVucPhongEditActivity.class);
                Bundle bdKhuvucphong = new Bundle();
                bdKhuvucphong.putInt("makhuvuc", kv.getMaKVP());
                bdKhuvucphong.putString("tenkhuvuc", kv.getTenKVP());
                intent.putExtra("datakhuvucphong", bdKhuvucphong);
                context.startActivity(intent);
            }
        });
        holder.deletekhuvuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tenkhuvuc.getContext());
                builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
                builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiServices.apiServices.delete_khuvucphong(kv.getMaKVP()).enqueue(new Callback<ObjectReponse>() {
                            @Override
                            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                                ObjectReponse objectadd = response.body();
                                if (objectadd.getCode() == 1) {
                                    Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                                    listKhuvucphong.remove(vitri);
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
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listKhuvucphong != null) {
            return listKhuvucphong.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<KhuVucPhong> searchlist) {
        listKhuvucphong = searchlist;
        notifyDataSetChanged();
    }

    public KhuVucPhong getItem(int position) {
        return listKhuvucphong.get(position);
    }

    public class KhuvucphongViewHolder extends RecyclerView.ViewHolder {
        private TextView makhuvuc, tenkhuvuc;
        private ImageView editkhuvuc, deletekhuvuc;

        public KhuvucphongViewHolder(@NonNull View itemView) {
            super(itemView);
            makhuvuc = itemView.findViewById(R.id.Makv);
            tenkhuvuc = itemView.findViewById(R.id.txtTenkv);
            editkhuvuc = itemView.findViewById(R.id.Editkv);
            deletekhuvuc = itemView.findViewById(R.id.Deletekv);
        }
    }
}
