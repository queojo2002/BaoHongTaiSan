package com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.NhomTaiSan;
import com.example.baohongtaisan_2.R;

import java.util.List;

public class SpinnerNhomTaiSan_Adapter extends ArrayAdapter<NhomTaiSan> {

    public SpinnerNhomTaiSan_Adapter(@NonNull Context context, int resource, List<NhomTaiSan> nhomTaiSanList) {
        super(context, resource, nhomTaiSanList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_selected, parent, false);
        TextView tv_selected = convertView.findViewById(R.id.txtSelected);
        NhomTaiSan nhomTaiSan = this.getItem(position);
        if (nhomTaiSan != null) {
            tv_selected.setText(nhomTaiSan.getTenNTS());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner, parent, false);
        TextView tv_show = convertView.findViewById(R.id.txtShowSpinner);
        NhomTaiSan nhomTaiSan = this.getItem(position);
        if (nhomTaiSan != null) {
            tv_show.setText(nhomTaiSan.getTenNTS());
        }
        return convertView;
    }
}
