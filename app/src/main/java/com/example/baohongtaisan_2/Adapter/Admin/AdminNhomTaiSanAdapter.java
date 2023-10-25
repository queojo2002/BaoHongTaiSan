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
import com.example.baohongtaisan_2.Model.NhomTaiSan;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminNhomTaiSanAdapter extends RecyclerView.Adapter<AdminNhomTaiSanAdapter.NhomtaisanViewHolder> {

    private List<NhomTaiSan> listnhomts;
    private RCVClickItem rcvClickItem;
    public AdminNhomTaiSanAdapter(List<NhomTaiSan> listnhomts, RCVClickItem rcvClickItem) {
        this.listnhomts = listnhomts;
        this.rcvClickItem = rcvClickItem;
    }

    @NonNull
    @Override
    public AdminNhomTaiSanAdapter.NhomtaisanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_nhomtaisan_admin, parent, false);
        return new AdminNhomTaiSanAdapter.NhomtaisanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNhomTaiSanAdapter.NhomtaisanViewHolder holder, int position) {
        NhomTaiSan nhomTaiSan = listnhomts.get(position);
        if (nhomTaiSan == null) {
            return;
        }
        holder.manhomts.setText(nhomTaiSan.getMaNTS()+"");
        holder.tennhomts.setText(nhomTaiSan.getTenNTS());

        holder.editnhomts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(nhomTaiSan, "EDIT");
            }
        });

        holder.deletenhomts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(nhomTaiSan, "DELETE");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listnhomts != null) {
            return listnhomts.size();
        }
        return 0;
    }



    public void searchDataList(ArrayList<NhomTaiSan> searchlist) {
        listnhomts = searchlist;
        notifyDataSetChanged();
    }

    public NhomTaiSan getItem(int position) {
        return listnhomts.get(position);
    }

    public class NhomtaisanViewHolder extends RecyclerView.ViewHolder {
        private TextView manhomts, tennhomts;
        private ImageView editnhomts, deletenhomts;

        public NhomtaisanViewHolder(@NonNull View itemView) {
            super(itemView);
            manhomts = itemView.findViewById(R.id.MaNhomTS);
            tennhomts = itemView.findViewById(R.id.txtTenNhomTS);
            editnhomts = itemView.findViewById(R.id.EditNhomTS);
            deletenhomts = itemView.findViewById(R.id.DeleteNhomTS);
        }
    }
}
