package com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baohongtaisan_2.Model.LoaiTaiSan;
import com.example.baohongtaisan_2.Model.NhomTaiSan;
import com.example.baohongtaisan_2.R;

import java.util.List;

public class SpinnerLoaiTaiSan_Adapter extends ArrayAdapter<LoaiTaiSan> {

    public SpinnerLoaiTaiSan_Adapter(@NonNull Context context, int resource, List<LoaiTaiSan> loaiTaiSanList) {
        super(context, resource, loaiTaiSanList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_selected, parent, false);
        TextView tv_selected = convertView.findViewById(R.id.txtSelected);
        LoaiTaiSan loaiTaiSan = this.getItem(position);
        if (loaiTaiSan != null) {
            tv_selected.setText(loaiTaiSan.getTenLTS());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner, parent, false);
        TextView tv_show = convertView.findViewById(R.id.txtShowSpinner);
        LoaiTaiSan loaiTaiSan = this.getItem(position);
        if (loaiTaiSan != null) {
            tv_show.setText(loaiTaiSan.getTenLTS());
        }
        return convertView;
    }
}
