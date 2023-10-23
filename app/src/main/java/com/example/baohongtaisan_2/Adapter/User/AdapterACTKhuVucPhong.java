package com.example.baohongtaisan_2.Adapter.User;

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

public class AdapterACTKhuVucPhong extends ArrayAdapter<KhuVucPhong> {


    public AdapterACTKhuVucPhong(@NonNull Context context, int resource, List<KhuVucPhong> khuVucPhongList) {
        super(context, resource, khuVucPhongList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.autocompletetextview_custom, parent, false);
        }
        KhuVucPhong khuVucPhong = getItem(position);
        TextView txttenKVP = convertView.findViewById(R.id.txtTenLP_KVP);
        txttenKVP.setText(khuVucPhong.getTenKVP());


        return convertView;
    }
}
