package com.example.baohongtaisan_2.Adapter.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.PhanQuyen;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

public class AdminPhanQuyenAdapter extends RecyclerView.Adapter<AdminPhanQuyenAdapter.PhanquyenViewHolder> {

    private List<PhanQuyen> listPhanquyen;
    private RCVClickItem rcvClickItem;


    public AdminPhanQuyenAdapter(List<PhanQuyen> listPhanquyen, RCVClickItem rcvClickItem) {
        this.listPhanquyen = listPhanquyen;
        this.rcvClickItem = rcvClickItem;
    }

    @NonNull
    @Override
    public PhanquyenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_phanquyen_admin, parent, false);
        return new PhanquyenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhanquyenViewHolder holder, int position) {
        PhanQuyen pq = listPhanquyen.get(position);
        if (pq == null) {
            return;
        }
        holder.mapq.setText(pq.getMaPQ()+"");
        holder.tenpq.setText(pq.getTenPQ());
        holder.editpq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(pq, "EDIT");
            }
        });
        holder.deletepq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(pq, "DELETE");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listPhanquyen != null) {
            return listPhanquyen.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<PhanQuyen> searchlist) {
        listPhanquyen = searchlist;
        notifyDataSetChanged();
    }

    public PhanQuyen getItem(int position) {
        return listPhanquyen.get(position);
    }

    public class PhanquyenViewHolder extends RecyclerView.ViewHolder {
        private TextView mapq;
        private TextView tenpq;
        private ImageView editpq;
        private ImageView deletepq;

        public PhanquyenViewHolder(@NonNull View itemView) {
            super(itemView);
            mapq = itemView.findViewById(R.id.Mapq);
            tenpq = itemView.findViewById(R.id.txtTenpq);
            editpq = itemView.findViewById(R.id.Editpq);
            deletepq = itemView.findViewById(R.id.Deletepq);
        }
    }
}