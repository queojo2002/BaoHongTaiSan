package com.example.baohongtaisan_2.Adapter.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Model.NhomTaiSan;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

public class NhomTaiSanAdapter extends RecyclerView.Adapter<NhomTaiSanAdapter.NhomtaisanViewHolder> {

    private List<NhomTaiSan> listnhomts;
    private Context context;

    public NhomTaiSanAdapter(List<NhomTaiSan> listnhomts) {
        this.listnhomts = listnhomts;
    }

    @NonNull
    @Override
    public NhomTaiSanAdapter.NhomtaisanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_nhomtaisan_admin, parent, false);
        context = parent.getContext();
        return new NhomTaiSanAdapter.NhomtaisanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhomTaiSanAdapter.NhomtaisanViewHolder holder, int position) {
        int vitri = position;
        NhomTaiSan nhomTaiSan = listnhomts.get(position);
        if (nhomTaiSan == null) {
            return;
        }
        holder.manhomts.setText(nhomTaiSan.getMaNTS()+"");
        holder.tennhomts.setText(nhomTaiSan.getTenNTS());
        holder.editnhomts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Context context = view.getContext();

                Intent intent = new Intent(context, EditNhomtaisanActivity.class);
                Bundle bdNTS = new Bundle();
                bdNTS.putInt("mants", nhomTaiSan.getMaNTS());
                bdNTS.putString("tennts", nhomTaiSan.getTenNTS());
                intent.putExtra("datants", bdNTS);
                context.startActivity(intent);*/
            }
        });
        holder.deletenhomts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
                builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*APIService.apiServices.delete_nhomtaisan(nhomTaiSan.getMaNTS()).enqueue(new Callback<ObjectEdit>() {
                            @Override
                            public void onResponse(Call<ObjectEdit> call, Response<ObjectEdit> response) {
                                ObjectEdit objectadd = response.body();
                                if (objectadd.getCode() == 1){
                                    Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                                    listnhomts.remove(vitri);
                                    notifyItemRemoved(vitri);
                                }else {
                                    Toast.makeText(context, objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ObjectEdit> call, Throwable t) {
                                Toast.makeText(context, "Xóa thất bại !", Toast.LENGTH_SHORT).show();

                            }
                        });*/
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
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
