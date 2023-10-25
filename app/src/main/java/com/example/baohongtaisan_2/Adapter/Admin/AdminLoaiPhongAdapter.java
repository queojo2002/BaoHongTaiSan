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
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoaiPhongAdapter extends RecyclerView.Adapter<AdminLoaiPhongAdapter.LoaiphongViewHolder> {

    private List<LoaiPhong> listLoaiphong;
    private RCVClickItem rcvClickItem;

    public AdminLoaiPhongAdapter(List<LoaiPhong> listLoaiphong, RCVClickItem rcvClickItem) {
        this.listLoaiphong = listLoaiphong;
        this.rcvClickItem = rcvClickItem;
    }

    @NonNull
    @Override
    public LoaiphongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_loaiphong_admin, parent, false);
        return new LoaiphongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiphongViewHolder holder, int position) {
        LoaiPhong lp = listLoaiphong.get(position);
        if (lp == null) {
            return;
        }
        holder.maloaiphong.setText(lp.getMaLP() + "");
        holder.tenloaiphong.setText(lp.getTenLP());
        holder.editloaiphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(lp, "EDIT");
            }
        });

        holder.deleteloaiphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(lp, "DELETE");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listLoaiphong != null) {
            return listLoaiphong.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<LoaiPhong> searchlist) {
        listLoaiphong = searchlist;
        notifyDataSetChanged();
    }

    public LoaiPhong getItem(int position) {
        return listLoaiphong.get(position);
    }





    public class LoaiphongViewHolder extends RecyclerView.ViewHolder {
        private TextView maloaiphong, tenloaiphong;
        private ImageView editloaiphong, deleteloaiphong;

        public LoaiphongViewHolder(@NonNull View itemView) {
            super(itemView);
            maloaiphong = itemView.findViewById(R.id.Malp);
            tenloaiphong = itemView.findViewById(R.id.txtTenlp);
            editloaiphong = itemView.findViewById(R.id.Editlp);
            deleteloaiphong = itemView.findViewById(R.id.Deletelp);
        }
    }
}
