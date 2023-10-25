package com.example.baohongtaisan_2.Adapter.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;


public class AdminNguoiDungAdapter extends RecyclerView.Adapter<AdminNguoiDungAdapter.NguoidungViewHolder> {

    private List<NguoiDung> listNguoidung;

    private RCVClickItem rcvClickItem;

    public AdminNguoiDungAdapter(List<NguoiDung> listNguoidung, RCVClickItem rcvClickItem) {
        this.listNguoidung = listNguoidung;
        this.rcvClickItem = rcvClickItem;
    }

    @NonNull
    @Override
    public NguoidungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_nguoidung_admin, parent, false);
        return new NguoidungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoidungViewHolder holder, int position) {
        NguoiDung nd = listNguoidung.get(position);
        if (nd == null) {
            return;
        }
        holder.tennd.setText(nd.getHoVaTen());
        holder.tendv.setText(nd.getTenDV());
        holder.tencd.setText(nd.getTenCD());
        holder.tenpq.setText(nd.getTenPQ());

        holder.editnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(nd, "EDIT");
            }
        });

        holder.deletend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (listNguoidung != null) {
            return listNguoidung.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<NguoiDung> searchlist) {
        listNguoidung = searchlist;
        notifyDataSetChanged();
    }

    public NguoiDung getItem(int position) {
        return listNguoidung.get(position);
    }



    public class NguoidungViewHolder extends RecyclerView.ViewHolder {
        private TextView tennd, tendv, tencd,tenpq;
        private ImageView editnd, deletend;

        public NguoidungViewHolder(@NonNull View itemView) {
            super(itemView);
            tennd = itemView.findViewById(R.id.txtTenND);
            tendv = itemView.findViewById(R.id.txtTenDV);
            tencd = itemView.findViewById(R.id.txtTenCD);
            tenpq = itemView.findViewById(R.id.txtTenPQ);
            editnd = itemView.findViewById(R.id.editND);
            deletend = itemView.findViewById(R.id.deleteND);
        }
    }
}
