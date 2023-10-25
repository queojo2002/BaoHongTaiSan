package com.example.baohongtaisan_2.Adapter.Admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class AdminLoaiTaiSanAdapter extends RecyclerView.Adapter<AdminLoaiTaiSanAdapter.LoaitaisanViewHolder> {

    private List<LoaiTaiSan> listloaits;
    private RCVClickItem rcvClickItem;

    public AdminLoaiTaiSanAdapter(List<LoaiTaiSan> listloaits, RCVClickItem rcvClickItem) {
        this.listloaits = listloaits;
        this.rcvClickItem = rcvClickItem;
    }

    @NonNull
    @Override
    public AdminLoaiTaiSanAdapter.LoaitaisanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_loaitaisan_admin, parent, false);
        return new AdminLoaiTaiSanAdapter.LoaitaisanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminLoaiTaiSanAdapter.LoaitaisanViewHolder holder, int position) {
        LoaiTaiSan loaiTaiSan = listloaits.get(position);
        if (loaiTaiSan == null) {
            return;
        }
        holder.maloaits.setText(loaiTaiSan.getMaLTS()+"");
        holder.tenloaits.setText(loaiTaiSan.getTenLTS());
        holder.editloaits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(loaiTaiSan, "EDIT");
            }
        });
        holder.deleteloaits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(loaiTaiSan, "DELETE");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listloaits != null) {
            return listloaits.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<LoaiTaiSan> searchlist) {
        listloaits = searchlist;
        notifyDataSetChanged();
    }

    public LoaiTaiSan getItem(int position) {
        return listloaits.get(position);
    }


    public class LoaitaisanViewHolder extends RecyclerView.ViewHolder {
        private TextView maloaits, tenloaits;
        private ImageView editloaits, deleteloaits;

        public LoaitaisanViewHolder(@NonNull View itemView) {
            super(itemView);
            maloaits = itemView.findViewById(R.id.MaLoaiTS);
            tenloaits = itemView.findViewById(R.id.txtTenLoaiTS);
            editloaits = itemView.findViewById(R.id.EditLoaiTS);
            deleteloaits = itemView.findViewById(R.id.DeleteLoaiTS);
        }
    }
}
