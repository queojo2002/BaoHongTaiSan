package com.example.baohongtaisan_2.Adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.R;

import java.util.List;

public class AdapterACTLoaiPhong extends ArrayAdapter<LoaiPhong> {

    public AdapterACTLoaiPhong(@NonNull Context context, int resource, List<LoaiPhong> loaiPhongList) {
        super(context, resource, loaiPhongList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.autocompletetextview_custom, parent, false);
        }
        LoaiPhong loaiPhong = getItem(position);
        TextView txttenKVP = convertView.findViewById(R.id.txtTenLP_KVP);
        txttenKVP.setText(loaiPhong.getTenLP());


        return convertView;
    }
}
