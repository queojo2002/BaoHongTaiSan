package com.example.baohongtaisan_2.Adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBaoLoi extends RecyclerView.Adapter<AdapterBaoLoi.BaoHongViewHolder> {

    private final List<BaoHong> baoHongList;
    private Context context;

    public AdapterBaoLoi(List<BaoHong> baoHongList) {
        this.baoHongList = baoHongList;
    }

    @NonNull
    @Override
    public BaoHongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_custom_home, parent, false);
        context = parent.getContext();
        return new AdapterBaoLoi.BaoHongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaoHongViewHolder holder, int position) {
        if (baoHongList == null) {
            return;
        }
        BaoHongViewHolder holder_1 = holder;
        BaoHong baoHong = baoHongList.get(position);
        Glide.with(context).load(baoHong.getHinhAnh()).error(R.drawable.baseline_inventory_24).into(holder.imgHome_BaoHong);
        holder.txtHome_TenTS.setText(baoHong.getTenTS());
        holder.txtHome_TenP.setText(baoHong.getTenP());
        holder.txtHome_Time.setText(baoHong.getNgayCapNhat());

        if (baoHong.getTinhTrang() == 1) {
            holder.txtHome_TinhTrang.setText("Hư hỏng nhẹ (Minor)");
        } else if (baoHong.getTinhTrang() == 2) {
            holder.txtHome_TinhTrang.setText("Hư hỏng trung bình (Moderate)");
        } else if (baoHong.getTinhTrang() == 3) {
            holder.txtHome_TinhTrang.setText("Hư hỏng nghiêm trọng (Severe)");
        } else if (baoHong.getTinhTrang() == 4) {
            holder.txtHome_TinhTrang.setText("Hư hỏng hoàn toàn (Critical)");
        }

        if (baoHong.getTrangThai() == 1) {
            holder.txtHome_TrangThai.setText("Đã gửi báo hỏng");
        } else if (baoHong.getTrangThai() == 2) {
            holder.txtHome_TrangThai.setText("Đã tiếp nhận báo hỏng");
        } else if (baoHong.getTrangThai() == 3) {
            holder.txtHome_TrangThai.setText("Đang sửa chữa");
        } else if (baoHong.getTrangThai() == 4) {
            holder.txtHome_TrangThai.setText("Sửa thành công");
        } else if (baoHong.getTrangThai() == 5) {
            holder.txtHome_TrangThai.setText("Sửa không thành công");
        }


    }

    @Override
    public int getItemCount() {
        if (baoHongList == null) {
            return 0;
        }
        return baoHongList.size();
    }


    public class BaoHongViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgHome_BaoHong;
        TextView txtHome_TenTS, txtHome_TenP, txtHome_Time, txtHome_TrangThai, txtHome_TinhTrang;

        public BaoHongViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHome_BaoHong = itemView.findViewById(R.id.imgHome_BaoHong);
            txtHome_TenTS = itemView.findViewById(R.id.txtHome_TenTS);
            txtHome_TenP = itemView.findViewById(R.id.txtHome_TenP);
            txtHome_Time = itemView.findViewById(R.id.txtHome_Time);
            txtHome_TrangThai = itemView.findViewById(R.id.txtHome_TrangThai);
            txtHome_TinhTrang = itemView.findViewById(R.id.txtHome_TinhTrang);
        }
    }
}
