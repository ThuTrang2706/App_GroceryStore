package com.example.cnpm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class pr_adapter extends BaseAdapter {
    Context context;
    int layout;
    List<pr_information> list;

    public pr_adapter(Context context, int layout, List<pr_information> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }
    @Override
    public Object getItem(int i) { return null; }
    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout, null);
        TextView txtmasanpham = view.findViewById(R.id.txtmasanpham);
        TextView txttensanpham = view.findViewById(R.id.txttensanpham);
        TextView txtgiaca = view.findViewById(R.id.txtgiaca);
        TextView txtmota = view.findViewById(R.id.txtmota);
        pr_information information = list.get(i);
        txtmasanpham.setText(information.getMasanpham());
        txttensanpham.setText(information.getTensanpham());
        txtgiaca.setText(String.valueOf(information.getGiaca()));
        txtmota.setText(information.getMota());
        return view;
    }
}
