package com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<CharSequence> {
    private final Context context;
    private final int resource;
    private final int textViewResourceId;
    private final List<CharSequence> items;
    private final int textSize;

    public CustomSpinnerAdapter(Context context, int resource, int textViewResourceId, List<CharSequence> items, int textSize) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        this.textSize = textSize;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView textView = view.findViewById(textViewResourceId);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        return view;
    }
}
