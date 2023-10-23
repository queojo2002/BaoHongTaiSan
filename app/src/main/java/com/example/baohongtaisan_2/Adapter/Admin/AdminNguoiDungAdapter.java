package com.example.baohongtaisan_2.Adapter.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

public class AdminNguoiDungAdapter extends RecyclerView.Adapter<AdminNguoiDungAdapter.NguoidungViewHolder> {

    private List<NguoiDung> listNguoidung;
    private Context context;

    public AdminNguoiDungAdapter(List<NguoiDung> listNguoidung) {
        this.listNguoidung = listNguoidung;
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
       /* holder.editnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent intent = new Intent(context, EditPhanquyenActivity.class);
                Bundle bdNguoidung = new Bundle();
                bdNguoidung.putString("tenpq", nd.getHoVaTen());
                intent.putExtra("datanguoidung", bdNguoidung);
                context.startActivity(intent);
            }
        });
        holder.deletend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tennd.getContext());
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
                        Toast.makeText(holder.tennd.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        if (listNguoidung != null) {
            return listNguoidung.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<NguoiDung> searchlist)
    {
        listNguoidung = searchlist;
        notifyDataSetChanged();
    }

    public NguoiDung getItem(int position) {
        return listNguoidung.get(position);
    }

    public class NguoidungViewHolder extends RecyclerView.ViewHolder {
        private TextView tennd;
        private ImageView editnd, deletend;

        public NguoidungViewHolder(@NonNull View itemView) {
            super(itemView);
            tennd = itemView.findViewById(R.id.txtTenND);
            editnd = itemView.findViewById(R.id.editND);
            deletend = itemView.findViewById(R.id.deleteND);
        }
    }
}
