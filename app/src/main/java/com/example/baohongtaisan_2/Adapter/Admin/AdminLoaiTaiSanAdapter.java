package com.example.baohongtaisan_2.Adapter.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.LoaiTaiSan;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

public class AdminLoaiTaiSanAdapter extends RecyclerView.Adapter<AdminLoaiTaiSanAdapter.LoaitaisanViewHolder> {

    private List<LoaiTaiSan> listloaits;
    private final RCVClickItem rcvClickItem;

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
        holder.maloaits.setText(loaiTaiSan.getMaLTS() + "");
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
        private final TextView maloaits;
        private final TextView tenloaits;
        private final ImageView editloaits;
        private final ImageView deleteloaits;

        public LoaitaisanViewHolder(@NonNull View itemView) {
            super(itemView);
            maloaits = itemView.findViewById(R.id.MaLoaiTS);
            tenloaits = itemView.findViewById(R.id.txtTenLoaiTS);
            editloaits = itemView.findViewById(R.id.EditLoaiTS);
            deleteloaits = itemView.findViewById(R.id.DeleteLoaiTS);
        }
    }
}
