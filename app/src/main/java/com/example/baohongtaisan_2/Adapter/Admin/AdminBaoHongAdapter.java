package com.example.baohongtaisan_2.Adapter.Admin;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.Model.NotificationDataBaoHong;
import com.example.baohongtaisan_2.Model.NotificationReponse;
import com.example.baohongtaisan_2.Model.NotificationSendData;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBaoHongAdapter extends RecyclerView.Adapter<AdminBaoHongAdapter.AdminBaoHongViewHolder> {

    private List<BaoHong> baoHongList;
    private Context context;

    public AdminBaoHongAdapter(List<BaoHong> baoHongList) {
        this.baoHongList = baoHongList;
    }


    @NonNull
    @Override
    public AdminBaoHongAdapter.AdminBaoHongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_baohong_admin, parent, false);
        context = parent.getContext();
        return new AdminBaoHongViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdminBaoHongAdapter.AdminBaoHongViewHolder holder, int position) {
        if (baoHongList == null) {
            return;
        }
        BaoHong baoHong = baoHongList.get(position);
        Glide.with(context).load(baoHong.getHinhAnh()).error(R.drawable.baseline_inventory_24).into(holder.imgQLBH_BaoHong);
        holder.txtQLBH_TenTS.setText(baoHong.getTenTS());
        holder.txtQLBH_TenP.setText(baoHong.getTenP());
        holder.txtQLBH_Time.setText(baoHong.getNgayCapNhat());
        holder.btnTT2.setOnClickListener(view -> Set_TrangThai(baoHong, 2));
        holder.btnTT3.setOnClickListener(view -> Set_TrangThai(baoHong, 3));
        holder.btnTT4.setOnClickListener(view -> Set_TrangThai(baoHong, 4));
        holder.btnTT5.setOnClickListener(view -> Set_TrangThai(baoHong, 5));


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
            holder.btnTT2.setEnabled(true);
            holder.btnTT3.setEnabled(true);
            holder.btnTT4.setEnabled(true);
            holder.btnTT5.setEnabled(true);
        } else if (baoHong.getTrangThai() == 2) {
            holder.txtQLBH_TrangThai.setText("Đã tiếp nhận báo hỏng");
            holder.btnTT2.setEnabled(false);
            holder.btnTT3.setEnabled(true);
            holder.btnTT4.setEnabled(true);
            holder.btnTT5.setEnabled(true);
        } else if (baoHong.getTrangThai() == 3) {
            holder.txtQLBH_TrangThai.setText("Đang sửa chữa");
            holder.btnTT2.setEnabled(false);
            holder.btnTT3.setEnabled(false);
            holder.btnTT4.setEnabled(true);
            holder.btnTT5.setEnabled(true);
        } else if (baoHong.getTrangThai() == 4) {
            holder.txtQLBH_TrangThai.setText("Sửa thành công");
            holder.btnTT2.setEnabled(false);
            holder.btnTT3.setEnabled(false);
            holder.btnTT4.setEnabled(false);
            holder.btnTT5.setEnabled(false);
        } else if (baoHong.getTrangThai() == 5) {
            holder.txtQLBH_TrangThai.setText("Sửa không thành công");
            holder.btnTT2.setEnabled(false);
            holder.btnTT3.setEnabled(false);
            holder.btnTT4.setEnabled(false);
            holder.btnTT5.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        if (baoHongList != null) {
            return baoHongList.size();
        }
        return 0;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void searchDataList(ArrayList<BaoHong> searchlist) {
        baoHongList = searchlist;
        notifyDataSetChanged();
    }

    public BaoHong getItem(int position) {
        return baoHongList.get(position);
    }


    public void Set_TrangThai(BaoHong baoHong, int TrangThai) {
        ApiServices.apiServices.edit_data_trangthai_baoloi_byMaBL(baoHong.getMaBL(),TrangThai).enqueue(new Callback<ObjectReponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                ObjectReponse objectReponse = response.body();
                if (objectReponse == null) return;
                if(objectReponse.getCode() == 1){
                    baoHong.setTrangThai(TrangThai);
                    sendNotiToUser(baoHong.getMaND(), baoHong.getTenTS(), baoHong.getTenP(), TrangThai, baoHong.getTinhTrang(), baoHong.getMota(), baoHong.getToken());
                    Toast.makeText(context, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context, objectReponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                Toast.makeText(context, "Cập nhật thất bại ...", Toast.LENGTH_SHORT).show();
            }
        });
    }





    private void sendNotiToUser(int MaND, String TenTS, String TenP, int TrangThai, int TinhTrang, String MoTa, String Token)
    {
        NotificationDataBaoHong notificationDataBaoHong = new NotificationDataBaoHong(MaND, TenTS, TenP, TrangThai, TinhTrang, MoTa, "AdminToUser");
        NotificationSendData notificationSendData = new NotificationSendData(notificationDataBaoHong, Token);
        ApiServices.apiServices_Noti.sendNoti(notificationSendData).enqueue(new Callback<NotificationReponse>() {
            @Override
            public void onResponse(@NonNull Call<NotificationReponse> call, @NonNull Response<NotificationReponse> response) {
                // ok send noti ok
            }

            @Override
            public void onFailure(@NonNull Call<NotificationReponse> call, @NonNull Throwable t) {

            }
        });
    }

    public static class AdminBaoHongViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgQLBH_BaoHong;
        TextView txtQLBH_TenTS, txtQLBH_TenP, txtQLBH_Time, txtQLBH_TrangThai, txtQLBH_TinhTrang;
        Button btnTT2, btnTT3, btnTT4, btnTT5;

        public AdminBaoHongViewHolder(@NonNull View itemView) {
            super(itemView);
            imgQLBH_BaoHong = itemView.findViewById(R.id.imgQL_BaoHong);
            txtQLBH_TenTS = itemView.findViewById(R.id.txtQLBH_TenTS);
            txtQLBH_TenP = itemView.findViewById(R.id.txtQLBH_TenP);
            txtQLBH_Time = itemView.findViewById(R.id.txtQLBH_Time);
            txtQLBH_TrangThai = itemView.findViewById(R.id.txtQLBH_TrangThai);
            txtQLBH_TinhTrang = itemView.findViewById(R.id.txtQLBH_TinhTrang);
            btnTT2 = itemView.findViewById(R.id.btnTiepnhan);
            btnTT3 = itemView.findViewById(R.id.btnDangsua);
            btnTT4 = itemView.findViewById(R.id.btnThanhcong);
            btnTT5 = itemView.findViewById(R.id.btnKhongthanhcong);

        }
    }

}

