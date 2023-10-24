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
import com.example.baohongtaisan_2.Model.DonVi;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDonViAdapter extends RecyclerView.Adapter<AdminDonViAdapter.DonviViewHolder> {

    private List<DonVi> listDonvi;
    private Context context;
    private Button btnhuybo, btnchinhsua;
    private EditText txtinput;

    public AdminDonViAdapter(List<DonVi> listDonvi) {
        this.listDonvi = listDonvi;
    }

    @NonNull
    @Override
    public DonviViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_donvi_admin, parent, false);
        context = parent.getContext();
        return new DonviViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonviViewHolder holder, int position) {
        int vitri = position;
        DonVi dv = listDonvi.get(position);
        if (dv == null) {
            return;
        }
        holder.madv.setText(dv.getMaDV() + "");
        holder.tendv.setText(dv.getTenDV());
        holder.editdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Dialog_Edit(dv,vitri);
            }
        });
        holder.deletedv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tendv.getContext());
                builder.setTitle("Bạn có chắc chắn muốn xóa không ?");
                builder.setMessage("Dữ liệu đã xóa không thể khôi phục ! ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiServices.apiServices.delete_donvi(dv.getMaDV()).enqueue(new Callback<ObjectReponse>() {
                            @Override
                            public void onResponse(Call<ObjectReponse> call, Response<ObjectReponse> response) {
                                ObjectReponse objectadd = response.body();
                                if (objectadd.getCode() == 1) {
                                    Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                                    listDonvi.remove(vitri);
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
        if (listDonvi != null) {
            return listDonvi.size();
        }
        return 0;
    }

    public void searchDataList(ArrayList<DonVi> searchlist) {
        listDonvi = searchlist;
        notifyDataSetChanged();
    }


    public DonVi getItem(int position) {
        return listDonvi.get(position);
    }


    public void Open_Dialog_Edit(DonVi dv, int vitri) {
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

        txtinput = dialog.findViewById(R.id.txtInput);
        btnhuybo = dialog.findViewById(R.id.btnHuyBo);
        btnchinhsua = dialog.findViewById(R.id.btnEdit);

        txtinput.setText(dv.getTenDV());
        btnchinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServices.apiServices.edit_donvi(dv.getMaDV(), txtinput.getText().toString(), dv.getMoTaDV()).enqueue(new Callback<ObjectReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                        ObjectReponse objectEdit = response.body();
                        if (objectEdit == null) return;
                        if (objectEdit.getCode() == 1) {
                            Toast.makeText(context, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            listDonvi.get(vitri).setTenDV(txtinput.getText().toString());
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



    public class DonviViewHolder extends RecyclerView.ViewHolder {
        private TextView madv, tendv;
        private ImageView editdv, deletedv;

        public DonviViewHolder(@NonNull View itemView) {
            super(itemView);
            madv = itemView.findViewById(R.id.Madv);
            tendv = itemView.findViewById(R.id.txtTendv);
            editdv = itemView.findViewById(R.id.Editdv);
            deletedv = itemView.findViewById(R.id.Deletedv);
        }
    }
}
