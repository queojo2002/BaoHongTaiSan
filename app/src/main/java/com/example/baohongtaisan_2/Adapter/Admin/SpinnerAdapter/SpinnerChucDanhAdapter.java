package com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baohongtaisan_2.Model.ChucDanh;
import com.example.baohongtaisan_2.R;

import java.util.List;

public class SpinnerChucDanhAdapter extends ArrayAdapter<ChucDanh> {

    public SpinnerChucDanhAdapter(@NonNull Context context, int resource, List<ChucDanh> chucDanhList) {
        super(context, resource, chucDanhList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_selected, parent, false);
        TextView tv_selected = convertView.findViewById(R.id.txtSelected);
        ChucDanh chucDanh = this.getItem(position);
        if (chucDanh != null) {
            tv_selected.setText(chucDanh.getTenCD());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner, parent, false);
        TextView tv_show = convertView.findViewById(R.id.txtShowSpinner);
        ChucDanh chucDanh = this.getItem(position);
        if (chucDanh != null) {
            tv_show.setText(chucDanh.getTenCD());
        }
        return convertView;
    }
}
