package com.example.baohongtaisan_2.Adapter.Admin;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.DonVi;
import com.example.baohongtaisan_2.R;
import java.util.ArrayList;
import java.util.List;


public class AdminDonViAdapter extends RecyclerView.Adapter<AdminDonViAdapter.DonviViewHolder> {

    private List<DonVi> listDonvi;
    private RCVClickItem rcvClickItem;

    public AdminDonViAdapter(List<DonVi> listDonvi, RCVClickItem rcvClickItem) {
        this.listDonvi = listDonvi;
        this.rcvClickItem = rcvClickItem;
    }

    @NonNull
    @Override
    public DonviViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_donvi_admin, parent, false);
        return new DonviViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonviViewHolder holder, int position) {
        DonVi dv = listDonvi.get(position);
        if (dv == null) {
            return;
        }
        holder.madv.setText(dv.getMaDV() + "");
        holder.tendv.setText(dv.getTenDV());
        holder.editdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(dv, "EDIT");
                //Open_Dialog_Edit(dv,vitri);
            }
        });
        holder.deletedv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                rcvClickItem.onClickRCV(dv, "DELETE");
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
