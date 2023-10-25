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
import com.example.baohongtaisan_2.Interface.RCVClickItem;
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
    private RCVClickItem rcvClickItem;

    public AdminPhongAdapter(List<Phong> phongList, RCVClickItem rcvClickItem) {
        this.phongList = phongList;
        this.rcvClickItem = rcvClickItem;
    }


    @NonNull
    @Override
    public AdminPhongAdapter.AdminPhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_phong_admin, parent, false);
        return new AdminPhongAdapter.AdminPhongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPhongAdapter.AdminPhongViewHolder holder, int position) {
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
                rcvClickItem.onClickRCV(p, "EDIT");
            }
        });
        holder.deletephong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(p, "DELETE");
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
