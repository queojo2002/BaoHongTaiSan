package com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.R;

import java.util.List;

public class SpinnerKhuVucPhong_Adapter extends ArrayAdapter<KhuVucPhong> {

    public SpinnerKhuVucPhong_Adapter(@NonNull Context context, int resource, List<KhuVucPhong> khuVucPhongList) {
        super(context, resource, khuVucPhongList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_selected, parent, false);
        TextView tv_selected = convertView.findViewById(R.id.txtSelected);
        KhuVucPhong khuVucPhong = this.getItem(position);
        if (khuVucPhong != null) {
            tv_selected.setText(khuVucPhong.getTenKVP());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner, parent, false);
        TextView tv_show = convertView.findViewById(R.id.txtShowSpinner);
        KhuVucPhong khuVucPhong = this.getItem(position);
        if (khuVucPhong != null) {
            tv_show.setText(khuVucPhong.getTenKVP());
        }
        return convertView;
    }

}
