package com.example.baohongtaisan_2.Adapter.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.TaiSan;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminPBTSTaiSanAdapter extends RecyclerView.Adapter<AdminPBTSTaiSanAdapter.AdminTaiSanViewHolder>{
    private List<TaiSan> taiSanList;
    private View view;
    private RCVClickItem rcvClickItem;


    public AdminPBTSTaiSanAdapter(List<TaiSan> taiSanList, RCVClickItem rcvClickItem) {
        this.taiSanList = taiSanList;
        this.rcvClickItem = rcvClickItem;
    }


    @NonNull
    @Override
    public AdminPBTSTaiSanAdapter.AdminTaiSanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_taisan_admin_2, parent, false);
        return new AdminTaiSanViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdminPBTSTaiSanAdapter.AdminTaiSanViewHolder holder, int position) {
        TaiSan taiSan = taiSanList.get(position);
        if (taiSan == null) {
            return;
        }
        holder.txtTenTS.setText(taiSan.getTenTS());
        holder.txtTenLTS.setText(taiSan.getTenLTS());
        holder.txtTenNTS.setText(taiSan.getTenNTS());
        holder.SLNV.setText("Số lượng hiện còn: " + taiSan.getSLHienCon());
        holder.btnThemVaoPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(taiSan, "ADD");
            }
        });

    }

    @Override
    public int getItemCount() {
        if (taiSanList != null) {
            return taiSanList.size();
        }
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchDataList(ArrayList<TaiSan> searchlist) {
        taiSanList = searchlist;
        notifyDataSetChanged();
    }

    public static class AdminTaiSanViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTenTS;
        private final TextView txtTenLTS;
        private final TextView txtTenNTS;
        private final TextView SLNV;
        private Button btnThemVaoPhong;
        public AdminTaiSanViewHolder(@NonNull View itemView) {
            super(itemView);
            CircleImageView imgQLTS = itemView.findViewById(R.id.imgQLTS_2);
            txtTenTS = itemView.findViewById(R.id.tvTenTS_2);
            txtTenLTS = itemView.findViewById(R.id.tvTenNTS_2);
            txtTenNTS = itemView.findViewById(R.id.tv_TenLTS_2);
            SLNV = itemView.findViewById(R.id.tv_SLNV);
            btnThemVaoPhong = itemView.findViewById(R.id.btnThemVaoPhong);
        }
    }
}
