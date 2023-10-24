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
    private Context context;

    public AdminLoaiPhongAdapter(List<LoaiPhong> listLoaiphong) {
        this.listLoaiphong = listLoaiphong;
    }

    @NonNull
    @Override
    public LoaiphongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_loaiphong_admin, parent, false);
        context = parent.getContext();
        return new LoaiphongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiphongViewHolder holder, int position) {
        int vitri = position;
        LoaiPhong lp = listLoaiphong.get(position);
        if (lp == null) {
            return;
        }
        holder.maloaiphong.setText(lp.getMaLP() + "");
        holder.tenloaiphong.setText(lp.getTenLP());
        holder.editloaiphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Edit(lp, vitri);
            }
        });

        holder.deleteloaiphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tenloaiphong.getContext());
                builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
                builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiServices.apiServices.delete_loaiphong(lp.getMaLP()).enqueue(new Callback<ObjectReponse>() {
                            @Override
                            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                                ObjectReponse objectadd = response.body();
                                if (objectadd.getCode() == 1) {
                                    Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                                    listLoaiphong.remove(vitri);
                                    notifyItemRemoved(vitri);
                                } else {
                                    Toast.makeText(context, objectadd.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ObjectReponse> call, Throwable t) {
                                Toast.makeText(context, "Xóa thất bại !", Toast.LENGTH_SHORT).show();

                            }
                        });
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


    public void Open_Dialog_Edit(LoaiPhong loaiPhong, int vitri) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_edit);

        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtt = window.getAttributes();
        windowAtt.gravity = Gravity.CENTER;
        window.setAttributes(windowAtt);
        dialog.setCancelable(true);

        EditText txtinput = dialog.findViewById(R.id.txtInput);
        Button btnhuybo = dialog.findViewById(R.id.btnHuyBo);
        Button btnchinhsua = dialog.findViewById(R.id.btnEdit);
        txtinput.setText(loaiPhong.getTenLP());
        btnchinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.edit_khuvucphong(loaiPhong.getMaLP(), txtinput.getText().toString()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(context, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            listLoaiphong.get(vitri).setTenLP(txtinput.getText().toString());
                            notifyItemChanged(vitri);
                        } else {
                            Toast.makeText(context, objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnhuybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

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
