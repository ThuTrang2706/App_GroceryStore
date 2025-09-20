package com.example.cnpm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Nv_adapter extends BaseAdapter {
    Context context;
    int layout;
    List<Nv_class> list;

    public Nv_adapter(Context context, int layout, List<Nv_class> list) {
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
        TextView name = convertView.findViewById(R.id.txttennv),
                gt = convertView.findViewById(R.id.txtgioitinh),
                birth = convertView.findViewById(R.id.txtngaysinh),
                sdt = convertView.findViewById(R.id.txtsdt),
                email = convertView.findViewById(R.id.txtemail);
        Nv_class nv = list.get(position);
        name.setText(nv.getName());
        gt.setText(nv.getGender());
        birth.setText(nv.getBirth());
        sdt.setText(nv.getPhone());
        email.setText(nv.getEmail());
        return convertView;
    }
}
