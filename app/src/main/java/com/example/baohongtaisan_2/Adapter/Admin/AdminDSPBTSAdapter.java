package com.example.baohongtaisan_2.Adapter.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Model.PhanBo;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminDSPBTSAdapter extends RecyclerView.Adapter<AdminDSPBTSAdapter.AdminTaiSanViewHolder>{

    private List<PhanBo> phanBoList;
    private View view;

    public AdminDSPBTSAdapter(List<PhanBo> phanBoList) {
        this.phanBoList = phanBoList;
    }


    @NonNull
    @Override
    public AdminDSPBTSAdapter.AdminTaiSanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_taisan_admin, parent, false);
        return new AdminDSPBTSAdapter.AdminTaiSanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminDSPBTSAdapter.AdminTaiSanViewHolder holder, int position) {
        PhanBo phanBo = phanBoList.get(position);
        if (phanBo == null) {
            return;
        }
        holder.txtTenTS.setText(phanBo.getTenTS());
        holder.txtTenLTS.setText(phanBo.getTenLTS());
        holder.txtTenNTS.setText(phanBo.getTenNTS());
        holder.SLNV.setText("Số lượng hiện có trong phòng: " + phanBo.getSoLuong());
        holder.SLHC.setText("");

    }

    @Override
    public int getItemCount() {
        if (phanBoList != null) {
            return phanBoList.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<PhanBo> searchlist) {
        phanBoList = searchlist;
        notifyDataSetChanged();
    }

    public class AdminTaiSanViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgQLTS;
        private TextView txtTenTS, txtTenLTS, txtTenNTS, SLNV, SLHC;
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
