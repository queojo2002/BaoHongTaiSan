package com.example.baohongtaisan_2.Adapter.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Activity.User.TraCuu_BaoHongActivity;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterPhong extends RecyclerView.Adapter<AdapterPhong.PhongViewHolder> {

    private List<Phong> phongList;
    private Context context;

    public AdapterPhong(List<Phong> phongList) {
        this.phongList = phongList;
    }

    @NonNull
    @Override
    public PhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_custom, parent, false);
        context = parent.getContext();
        return new PhongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongViewHolder holder, int position) {
        if (phongList == null) {
            return;
        }
        Phong phong = phongList.get(position);
        holder.TenP.setText(phong.getTenP());
        holder.TenKVP.setText(phong.getTenKVP());
        holder.TenLoaiPhong.setText(phong.getTenLP());
        holder.btnTraCuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TraCuu_BaoHongActivity.class);
                intent.putExtra("MaP", phong.getMaP());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (phongList == null) {
            return 0;
        }
        return phongList.size();
    }

    public void searchDataList(ArrayList<Phong> searchlist) {
        phongList = searchlist;
        notifyDataSetChanged();
    }

    public class PhongViewHolder extends RecyclerView.ViewHolder {
        TextView TenP, TenKVP, TenLoaiPhong;
        Button btnTraCuu;

        public PhongViewHolder(@NonNull View itemView) {
            super(itemView);
            TenP = itemView.findViewById(R.id.txtTenPhong);
            TenKVP = itemView.findViewById(R.id.txtTenKhuVucPhong);
            TenLoaiPhong = itemView.findViewById(R.id.txtTenLoaiPhong);
            btnTraCuu = itemView.findViewById(R.id.btnTraCuu);
        }
    }


}
