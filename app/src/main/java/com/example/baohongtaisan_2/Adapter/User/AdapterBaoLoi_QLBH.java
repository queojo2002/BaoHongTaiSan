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
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.R;
import com.ramotion.foldingcell.FoldingCell;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBaoLoi_QLBH extends RecyclerView.Adapter<AdapterBaoLoi_QLBH.BaoHongViewHolder> {

    private List<BaoHong> baoHongList;
    private Context context;

    public AdapterBaoLoi_QLBH(List<BaoHong> baoHongList) {
        this.baoHongList = baoHongList;
    }


    @NonNull
    @Override
    public AdapterBaoLoi_QLBH.BaoHongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_custom_quanlybaohong, parent, false);
        context = parent.getContext();
        return new AdapterBaoLoi_QLBH.BaoHongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBaoLoi_QLBH.BaoHongViewHolder holder, int position) {
        if (baoHongList == null) {
            return;
        }
        BaoHong baoHong = baoHongList.get(position);
        Glide.with(context).load(baoHong.getHinhAnh()).error(R.drawable.baseline_inventory_24).into(holder.imgQLBH_BaoHong);
        holder.txtQLBH_TenTS.setText(baoHong.getTenTS());
        holder.txtQLBH_TenP.setText(baoHong.getTenP());
        holder.txtQLBH_Time.setText(baoHong.getNgayCapNhat());
        holder.txtQLBH_MoTa.setText(baoHong.getMota());
        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.foldingCell.toggle(false);
            }
        });
        holder.tvMaBH.setText("Mã báo lỗi: " + baoHong.getMaBL());
        holder.tvTenTS.setText(baoHong.getTenTS());
        holder.tvTime.setText(baoHong.getNgayTao());
        Glide.with(context).load("https://ui-avatars.com/api/?name="+baoHong.getMaBL()+"&background=EB0895&bold=true&font-size=0.25").error(R.drawable.baseline_account_circle_24).into(holder.QLBH_imgView);


        if (baoHong.getTinhTrang() == 1) {
            holder.txtQLBH_TinhTrang.setText("Hư hỏng nhẹ (Minor)");
        } else if (baoHong.getTinhTrang() == 2) {
            holder.txtQLBH_TinhTrang.setText("Hư hỏng trung bình (Moderate)");
        } else if (baoHong.getTinhTrang() == 3) {
            holder.txtQLBH_TinhTrang.setText("Hư hỏng nghiêm trọng (Severe)");
        } else if (baoHong.getTinhTrang() == 4) {
            holder.txtQLBH_TinhTrang.setText("Hư hỏng hoàn toàn (Critical)");
        }

        if (baoHong.getTrangThai() == 1) {
            holder.txtQLBH_TrangThai.setText("Đã gửi báo hỏng");
        } else if (baoHong.getTrangThai() == 2) {
            holder.txtQLBH_TrangThai.setText("Đã tiếp nhận báo hỏng");
        } else if (baoHong.getTrangThai() == 3) {
            holder.txtQLBH_TrangThai.setText("Đang sửa chữa");
        } else if (baoHong.getTrangThai() == 4) {
            holder.txtQLBH_TrangThai.setText("Sửa thành công");
        } else if (baoHong.getTrangThai() == 5) {
            holder.txtQLBH_TrangThai.setText("Sửa không thành công");
        }


    }

    public void searchDataList(ArrayList<BaoHong> searchlist) {
        baoHongList = searchlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (baoHongList == null) {
            return 0;
        }
        return baoHongList.size();
    }


    public class BaoHongViewHolder extends RecyclerView.ViewHolder {
        private FoldingCell foldingCell;
        CircleImageView imgQLBH_BaoHong, QLBH_imgView;
        TextView txtQLBH_TenTS, txtQLBH_TenP, txtQLBH_Time, txtQLBH_TrangThai, txtQLBH_TinhTrang, txtQLBH_MoTa;
        TextView tvMaBH, tvTenTS, tvTime;

        public BaoHongViewHolder(@NonNull View itemView) {
            super(itemView);
            imgQLBH_BaoHong = itemView.findViewById(R.id.imgQLBH);
            txtQLBH_TenTS = itemView.findViewById(R.id.txtQLBG_TenTS);
            txtQLBH_TenP = itemView.findViewById(R.id.txtQLBG_TenP);
            txtQLBH_Time = itemView.findViewById(R.id.txtQLBH_Time);
            txtQLBH_TrangThai = itemView.findViewById(R.id.txtQLBG_TrangThai);
            txtQLBH_TinhTrang = itemView.findViewById(R.id.txtQLBG_TinhTrang);
            txtQLBH_MoTa = itemView.findViewById(R.id.txtQLBG_MoTa);
            foldingCell = itemView.findViewById(R.id.folding_cell);
            tvMaBH = itemView.findViewById(R.id.QLBH_tvMaBH);
            tvTenTS = itemView.findViewById(R.id.QLBH_tvTenTS);
            tvTime = itemView.findViewById(R.id.QLBH_tvTime);
            QLBH_imgView = itemView.findViewById(R.id.QLBH_imgView);
        }
    }
}
