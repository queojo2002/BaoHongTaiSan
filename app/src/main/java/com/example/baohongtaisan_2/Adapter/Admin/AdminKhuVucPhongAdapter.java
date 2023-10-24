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
    private Context context;

    public AdminKhuVucPhongAdapter(List<KhuVucPhong> listKhuvucphong) {
        this.listKhuvucphong = listKhuvucphong;
    }

    @NonNull
    @Override
    public KhuvucphongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_khuvucphong_admin, parent, false);
        context = parent.getContext();
        return new KhuvucphongViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull KhuvucphongViewHolder holder, int position) {
        int vitri = position;
        KhuVucPhong kv = listKhuvucphong.get(position);
        if (kv == null) {
            return;
        }
        holder.makhuvuc.setText(kv.getMaKVP() + "");
        holder.tenkhuvuc.setText(kv.getTenKVP());
        holder.editkhuvuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Edit(kv, vitri);
            }
        });
        holder.deletekhuvuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tenkhuvuc.getContext());
                builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
                builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiServices.apiServices.delete_khuvucphong(kv.getMaKVP()).enqueue(new Callback<ObjectReponse>() {
                            @Override
                            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                                ObjectReponse objectadd = response.body();
                                if (objectadd.getCode() == 1) {
                                    Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                                    listKhuvucphong.remove(vitri);
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
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
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



    public void Open_Dialog_Edit(KhuVucPhong khuVucPhong, int vitri) {
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
        txtinput.setText(khuVucPhong.getTenKVP());
        btnchinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.edit_khuvucphong(khuVucPhong.getMaKVP(), txtinput.getText().toString()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(context, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            listKhuvucphong.get(vitri).setTenKVP(txtinput.getText().toString());
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
