package com.example.cnpm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HoaDon_adapter extends BaseAdapter {
    Context context;
    int layout;
    List<HoaDon_class> list;

    public HoaDon_adapter(Context context, int layout, List<HoaDon_class> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout, null);
        TextView ma = convertView.findViewById(R.id.txtmahd),
                tong = convertView.findViewById(R.id.txttotal),
                date = convertView.findViewById(R.id.txtdate),
                time = convertView.findViewById(R.id.txttime);
        HoaDon_class hd = list.get(position);
        ma.setText(hd.getId());
        tong.setText(String.valueOf(hd.getTotal()));
        date.setText(hd.getDate());
        time.setText(hd.getTime());
        return convertView;
    }
}
