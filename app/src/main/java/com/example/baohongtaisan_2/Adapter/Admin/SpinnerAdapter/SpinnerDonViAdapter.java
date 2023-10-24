package com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baohongtaisan_2.Model.DonVi;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.R;

import java.util.List;

public class SpinnerDonViAdapter extends ArrayAdapter<DonVi> {

    public SpinnerDonViAdapter(@NonNull Context context, int resource, List<DonVi> donViList) {
        super(context, resource, donViList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_selected, parent, false);
        TextView tv_selected = convertView.findViewById(R.id.txtSelected);
        DonVi donVi = this.getItem(position);
        if (donVi != null) {
            tv_selected.setText(donVi.getTenDV());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner, parent, false);
        TextView tv_show = convertView.findViewById(R.id.txtShowSpinner);
        DonVi donVi = this.getItem(position);
        if (donVi != null) {
            tv_show.setText(donVi.getTenDV());
        }
        return convertView;
    }


}
