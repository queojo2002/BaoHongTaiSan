package com.example.baohongtaisan_2.Adapter.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Model.TaiSan;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminTaiSanAdapter extends RecyclerView.Adapter<AdminTaiSanAdapter.AdminTaiSanViewHolder> {

    private List<TaiSan> taiSanList;
    private View view;
    private Context context;

    public AdminTaiSanAdapter(List<TaiSan> taiSanList) {
        this.taiSanList = taiSanList;
    }


    @NonNull
    @Override
    public AdminTaiSanAdapter.AdminTaiSanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_taisan_admin, parent, false);
        context = parent.getContext();
        return new AdminTaiSanAdapter.AdminTaiSanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminTaiSanAdapter.AdminTaiSanViewHolder holder, int position) {
        int vitri = position;
        TaiSan taiSan = taiSanList.get(position);
        if (taiSan == null) {
            return;
        }
        holder.txtTenTS.setText(taiSan.getTenTS());
        holder.txtTenLTS.setText(taiSan.getTenLTS());
        holder.txtTenNTS.setText(taiSan.getTenNTS());
        holder.SLNV.setText("Số lượng nhập vào: " + taiSan.getSLNhapVao());
        holder.SLHC.setText("Số lượng hiện còn: " + taiSan.getSLHienCon());

    }

    @Override
    public int getItemCount() {
        if (taiSanList != null) {
            return taiSanList.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<TaiSan> searchlist) {
        taiSanList = searchlist;
        notifyDataSetChanged();
    }

    public class AdminTaiSanViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imgQLTS;
        private final TextView txtTenTS;
        private final TextView txtTenLTS;
        private final TextView txtTenNTS;
        private final TextView SLNV;
        private final TextView SLHC;

        public AdminTaiSanViewHolder(@NonNull View itemView) {
            super(itemView);
            imgQLTS = itemView.findViewById(R.id.imgQLTS);
            txtTenTS = itemView.findViewById(R.id.txtAdminQLTS_TenTS);
            txtTenLTS = itemView.findViewById(R.id.txtAdminQLTS_TenLTS);
            txtTenNTS = itemView.findViewById(R.id.txtAdminQLTS_TenNTS);
            SLNV = itemView.findViewById(R.id.txtAdminQLTS_SLNV);
            SLHC = itemView.findViewById(R.id.txtAdminQLTS_SLHC);
        }
    }

}
