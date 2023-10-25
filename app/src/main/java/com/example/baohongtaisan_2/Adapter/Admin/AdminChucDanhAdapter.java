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
import com.example.baohongtaisan_2.Model.ChucDanh;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminChucDanhAdapter extends RecyclerView.Adapter<AdminChucDanhAdapter.ChucdanhViewHolder> {

    private List<ChucDanh> listChucdanh;
    private RCVClickItem rcvClickItem;

    public AdminChucDanhAdapter(List<ChucDanh> listChucdanh, RCVClickItem rcvClickItem) {
        this.listChucdanh = listChucdanh;
        this.rcvClickItem = rcvClickItem;
    }

    @NonNull
    @Override
    public ChucdanhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_chucdanh_admin, parent, false);
        return new ChucdanhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChucdanhViewHolder holder, int position) {
        ChucDanh cd = listChucdanh.get(position);
        if (cd == null) {
            return;
        }
        holder.macd.setText(cd.getMaCD() + "");
        holder.tencd.setText(cd.getTenCD());
        holder.editcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(cd, "EDIT");
            }
        });
        holder.deletecd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(cd, "DELETE");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listChucdanh != null) {
            return listChucdanh.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<ChucDanh> searchlist) {
        listChucdanh = searchlist;
        notifyDataSetChanged();
    }

    public ChucDanh getItem(int position) {
        return listChucdanh.get(position);
    }




    public class ChucdanhViewHolder extends RecyclerView.ViewHolder {
        private final TextView macd;
        private final TextView tencd;
        private final ImageView editcd;
        private final ImageView deletecd;

        public ChucdanhViewHolder(@NonNull View itemView) {
            super(itemView);
            macd = itemView.findViewById(R.id.Macd);
            tencd = itemView.findViewById(R.id.txtTencd);
            editcd = itemView.findViewById(R.id.Editcd);
            deletecd = itemView.findViewById(R.id.Deletecd);
        }
    }
}
