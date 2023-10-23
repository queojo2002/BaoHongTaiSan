package com.example.baohongtaisan_2.Adapter.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Model.PhanQuyen;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

public class AdminPhanQuyenAdapter extends RecyclerView.Adapter<AdminPhanQuyenAdapter.PhanquyenViewHolder> {

    private List<PhanQuyen> listPhanquyen;
    private Context context;

    public AdminPhanQuyenAdapter(List<PhanQuyen> listPhanquyen) {
        this.listPhanquyen = listPhanquyen;
    }

    @NonNull
    @Override
    public PhanquyenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_phanquyen_admin, parent, false);
        return new PhanquyenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhanquyenViewHolder holder, int position) {
        PhanQuyen pq = listPhanquyen.get(position);
        if (pq == null) {
            return;
        }
        holder.mapq.setText(pq.getMaPQ());
        holder.tenpq.setText(pq.getTenPQ());
        /*holder.editpq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent intent = new Intent(context, EditPhanquyenActivity.class);
                Bundle bdPhanquyen = new Bundle();
                bdPhanquyen.putString("mapq", pq.getMaPQ());
                bdPhanquyen.putString("tenpq", pq.getTenPQ());
                bdPhanquyen.putString("motapq", pq.getMoTaPQ());
                intent.putExtra("dataphanquyen", bdPhanquyen);
                context.startActivity(intent);
            }
        });
        holder.deletepq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tenpq.getContext());
                builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
                builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        *//*Xử lý*//*
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.tenpq.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        if (listPhanquyen != null) {
            return listPhanquyen.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<PhanQuyen> searchlist) {
        listPhanquyen = searchlist;
        notifyDataSetChanged();
    }

    public PhanQuyen getItem(int position) {
        return listPhanquyen.get(position);
    }

    public class PhanquyenViewHolder extends RecyclerView.ViewHolder {
        private TextView mapq, tenpq;
        private ImageView editpq, deletepq;

        public PhanquyenViewHolder(@NonNull View itemView) {
            super(itemView);
            mapq = itemView.findViewById(R.id.Mapq);
            tenpq = itemView.findViewById(R.id.txtTenpq);
            editpq = itemView.findViewById(R.id.Editpq);
            deletepq = itemView.findViewById(R.id.Deletepq);
        }
    }
}