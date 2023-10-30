package com.example.baohongtaisan_2.Adapter.Admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Activity.Admin.AdminPhanBoTaiSanActivity;
import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminPhongAdapter extends RecyclerView.Adapter<AdminPhongAdapter.AdminPhongViewHolder> {

    private List<Phong> phongList;
    private final RCVClickItem rcvClickItem;
    private Context context;

    public AdminPhongAdapter(List<Phong> phongList, RCVClickItem rcvClickItem) {
        this.phongList = phongList;
        this.rcvClickItem = rcvClickItem;
    }


    @NonNull
    @Override
    public AdminPhongAdapter.AdminPhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_phong_admin, parent, false);
        context = parent.getContext();
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
        holder.KiTuPhong.setText(p.getTenP().substring(p.getTenP().indexOf(" "), p.getTenP().indexOf("-")).trim());

        holder.imageview.setColorFilter(Color.parseColor(getRandomColorForString(p.getTenP().substring(p.getTenP().indexOf(" "), p.getTenP().indexOf("-")).trim())));

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

        holder.phanboTaiSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminPhanBoTaiSanActivity.class);
                intent.putExtra("MaP", p.getMaP());
                intent.putExtra("TenP", p.getTenP());
                context.startActivity(intent);
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



    private static String getRandomColorForString(String str) {
        int hash = str.hashCode();
        int red = (hash & 0xFF0000) >> 16;
        int green = (hash & 0x00FF00) >> 8;
        int blue = hash & 0x0000FF;
        return String.format("#%02X%02X%02X", red, green, blue);
    }


    public Phong getItem(int position) {
        return phongList.get(position);
    }


    public class AdminPhongViewHolder extends RecyclerView.ViewHolder {
        private final TextView tenphong;
        private final TextView kvp;
        private final TextView lp, KiTuPhong;
        private final ImageView editphong;
        private final ImageView deletephong;
        private final ImageView phanboTaiSan;
        private CircleImageView imageview;

        public AdminPhongViewHolder(@NonNull View itemView) {
            super(itemView);
            tenphong = itemView.findViewById(R.id.txtTenPhong);
            kvp = itemView.findViewById(R.id.txtKVP);
            lp = itemView.findViewById(R.id.txtLP);
            editphong = itemView.findViewById(R.id.editPhong);
            deletephong = itemView.findViewById(R.id.deletePhong);
            phanboTaiSan = itemView.findViewById(R.id.phanboTaiSan);
            KiTuPhong = itemView.findViewById(R.id.tvKiTuPhong);
            imageview = itemView.findViewById(R.id.imageview);
        }
    }

}
