package com.example.baohongtaisan_2.Adapter.Admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminKhuVucPhongAdapter extends RecyclerView.Adapter<AdminKhuVucPhongAdapter.KhuvucphongViewHolder> {

    private List<KhuVucPhong> listKhuvucphong;
    private RCVClickItem rcvClickItem;

    public AdminKhuVucPhongAdapter(List<KhuVucPhong> listKhuvucphong, RCVClickItem rcvClickItem) {
        this.listKhuvucphong = listKhuvucphong;
        this.rcvClickItem = rcvClickItem;
    }

    @NonNull
    @Override
    public KhuvucphongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_khuvucphong_admin, parent, false);
        return new KhuvucphongViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull KhuvucphongViewHolder holder, int position) {
        KhuVucPhong kv = listKhuvucphong.get(position);
        if (kv == null) {
            return;
        }
        holder.makhuvuc.setText(kv.getMaKVP() + "");
        holder.tenkhuvuc.setText(kv.getTenKVP());
        holder.editkhuvuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(kv, "EDIT");
            }
        });
        holder.deletekhuvuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(kv, "DELETE");
            }
        });
    }


    @Override
    public int getItemCount() {
        if (listKhuvucphong != null) {
            return listKhuvucphong.size();
        }
        return 0;
    }



    public void searchDataList(ArrayList<KhuVucPhong> searchlist) {
        listKhuvucphong = searchlist;
        notifyDataSetChanged();
    }



    public KhuVucPhong getItem(int position) {
        return listKhuvucphong.get(position);
    }




    public class KhuvucphongViewHolder extends RecyclerView.ViewHolder {
        private TextView makhuvuc, tenkhuvuc;
        private ImageView editkhuvuc, deletekhuvuc;

        public KhuvucphongViewHolder(@NonNull View itemView) {
            super(itemView);
            makhuvuc = itemView.findViewById(R.id.Makv);
            tenkhuvuc = itemView.findViewById(R.id.txtTenkv);
            editkhuvuc = itemView.findViewById(R.id.Editkv);
            deletekhuvuc = itemView.findViewById(R.id.Deletekv);
        }
    }
}
