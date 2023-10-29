package com.example.baohongtaisan_2.Adapter.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.NhomTaiSan;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

public class AdminNhomTaiSanAdapter extends RecyclerView.Adapter<AdminNhomTaiSanAdapter.NhomtaisanViewHolder> {

    private List<NhomTaiSan> listnhomts;
    private final RCVClickItem rcvClickItem;

    public AdminNhomTaiSanAdapter(List<NhomTaiSan> listnhomts, RCVClickItem rcvClickItem) {
        this.listnhomts = listnhomts;
        this.rcvClickItem = rcvClickItem;
    }

    @NonNull
    @Override
    public AdminNhomTaiSanAdapter.NhomtaisanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_nhomtaisan_admin, parent, false);
        return new AdminNhomTaiSanAdapter.NhomtaisanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNhomTaiSanAdapter.NhomtaisanViewHolder holder, int position) {
        NhomTaiSan nhomTaiSan = listnhomts.get(position);
        if (nhomTaiSan == null) {
            return;
        }
        holder.manhomts.setText(nhomTaiSan.getMaNTS() + "");
        holder.tennhomts.setText(nhomTaiSan.getTenNTS());

        holder.editnhomts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(nhomTaiSan, "EDIT");
            }
        });

        holder.deletenhomts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(nhomTaiSan, "DELETE");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listnhomts != null) {
            return listnhomts.size();
        }
        return 0;
    }


    public void searchDataList(ArrayList<NhomTaiSan> searchlist) {
        listnhomts = searchlist;
        notifyDataSetChanged();
    }

    public NhomTaiSan getItem(int position) {
        return listnhomts.get(position);
    }

    public class NhomtaisanViewHolder extends RecyclerView.ViewHolder {
        private final TextView manhomts;
        private final TextView tennhomts;
        private final ImageView editnhomts;
        private final ImageView deletenhomts;

        public NhomtaisanViewHolder(@NonNull View itemView) {
            super(itemView);
            manhomts = itemView.findViewById(R.id.MaNhomTS);
            tennhomts = itemView.findViewById(R.id.txtTenNhomTS);
            editnhomts = itemView.findViewById(R.id.EditNhomTS);
            deletenhomts = itemView.findViewById(R.id.DeleteNhomTS);
        }
    }
}
