package com.example.baohongtaisan_2.Adapter.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Activity.User.BaoHongActivity;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.Model.PhanBo;
import com.example.baohongtaisan_2.R;

import java.util.List;

public class AdapterPhanBo_TaiSan extends RecyclerView.Adapter<AdapterPhanBo_TaiSan.PhanBoViewHolder> {
    private final List<PhanBo> phanBoList;
    private final List<BaoHong> baoHongList;
    private Context context;

    public AdapterPhanBo_TaiSan(List<PhanBo> phanBoList, List<BaoHong> baoHongList) {
        this.phanBoList = phanBoList;
        this.baoHongList = baoHongList;
    }


    @NonNull
    @Override
    public PhanBoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tracuu_custom, parent, false);
        context = parent.getContext();
        return new PhanBoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PhanBoViewHolder holder, int position) {
        if (phanBoList == null) {
            return;
        }
        int Flag_Check_TT = 0;
        PhanBo phanBo = phanBoList.get(position);
        holder.TenTS.setText(phanBo.getTenTS());
        holder.TenNTS.setText(phanBo.getTenNTS());
        holder.SoLuong.setText("Số lượng: " + phanBo.getSoLuong());

        for (int i = 0; i < baoHongList.size(); i++) {
            BaoHong baoHong = baoHongList.get(i);
            if (baoHong.getMaPB() == phanBo.getMaPB()) {
                Flag_Check_TT = 1;
                if (baoHong.getTrangThai() == 1) {
                    holder.TrangThai.setBackgroundColor(Color.parseColor("#5792B9"));
                    holder.TrangThai.setText("Đã gửi báo hỏng tài sản");
                } else if (baoHong.getTrangThai() == 2) {
                    holder.TrangThai.setBackgroundColor(Color.parseColor("#A4D83B"));
                    holder.TrangThai.setText("Đã tiếp nhận báo hỏng");
                } else if (baoHong.getTrangThai() == 3) {
                    holder.TrangThai.setBackgroundColor(Color.parseColor("#FC4A32"));
                    holder.TrangThai.setText("Đang sữa chữa");
                } else if (baoHong.getTrangThai() == 4) {
                    holder.TrangThai.setText("Hoạt động tốt");
                } else if (baoHong.getTrangThai() == 5) {
                    holder.TrangThai.setBackgroundColor(Color.parseColor("#FF1E00"));
                    holder.TrangThai.setText("Đang bị hư hỏng");
                } else {
                    holder.TrangThai.setBackgroundColor(Color.parseColor("#AF6DCA"));
                    holder.TrangThai.setText("Trạng thái không thể xác định");
                }
            }
        }

        if (Flag_Check_TT == 0) {
            holder.TrangThai.setText("Hoạt động tốt");
        } else {
            holder.btnBaoHong.setBackgroundColor(Color.parseColor("#AAB7B8"));
            holder.btnBaoHong.setEnabled(false);
        }


        holder.btnXemChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context, TraCuu_BaoHongActivity.class);
                intent.putExtra("MaP", phong.getMaP());
                context.startActivity(intent);*/
            }
        });
        holder.btnBaoHong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BaoHongActivity.class);
                intent.putExtra("MaPB", phanBo.getMaPB());
                intent.putExtra("TenTS", phanBo.getTenTS());
                intent.putExtra("TenP", phanBo.getTenP());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (phanBoList == null) {
            return 0;
        }
        return phanBoList.size();
    }

    public class PhanBoViewHolder extends RecyclerView.ViewHolder {
        TextView TenTS, TenNTS, SoLuong, TrangThai;
        Button btnXemChiTiet, btnBaoHong;

        public PhanBoViewHolder(@NonNull View itemView) {
            super(itemView);
            TenTS = itemView.findViewById(R.id.txtTenTS_1);
            TenNTS = itemView.findViewById(R.id.txtTenNTS_1);
            SoLuong = itemView.findViewById(R.id.txtSoLuong_1);
            TrangThai = itemView.findViewById(R.id.txtTrangThai_1);
            btnXemChiTiet = itemView.findViewById(R.id.btnXemChiTiet);
            btnBaoHong = itemView.findViewById(R.id.btnBaoHong);
        }
    }
}
