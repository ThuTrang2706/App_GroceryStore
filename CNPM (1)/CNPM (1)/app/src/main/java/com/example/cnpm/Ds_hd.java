package com.example.cnpm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Ds_hd extends AppCompatActivity {

    Data_Shop db = new Data_Shop(Ds_hd.this);
    TextView back;
    Button find;
    EditText find_edt;
    ListView lv;
    HoaDon_adapter adapter;
    List<HoaDon_class> ls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ds_hd);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        adapter = new HoaDon_adapter(Ds_hd.this,R.layout.adapter_hd,ls);
        get_db();
        lv.setAdapter(adapter);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(find_edt.getText().toString().isEmpty())
                {
                    Toast.makeText(Ds_hd.this, "Nhap ma hoa don can tim", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Cursor hd = db.getInvoiceById(find_edt.getText().toString());
                    ls.clear();
                    hd.moveToFirst();
                    do {
                        HoaDon_class add = new HoaDon_class(hd.getString(0),
                                hd.getString(1),
                                hd.getString(2),
                                hd.getInt(3));
                        ls.add(add);
                    }while (hd.moveToNext());
                    hd.close();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        find_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty())
                {
                    get_db();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(Ds_hd.this,InvoiceDetailsActivity.class);
                detail.putExtra("id",ls.get(position).getId());
                startActivity(detail);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(Ds_hd.this, quanly.class);
                startActivity(back);
                finish();
            }
        });
    }

    private void mapping()
    {
        back = findViewById(R.id.back_main);
        find = findViewById(R.id.btntimkiem_hd);
        find_edt = findViewById(R.id.edttimkiem_hd);
        lv = findViewById(R.id.lvHD);
    }

    private void get_db()
    {
        Cursor hd = db.getAllInvoices();
        ls.clear();
        hd.moveToFirst();
        do {
            HoaDon_class add = new HoaDon_class(hd.getString(0),
                                                hd.getString(1),
                                                hd.getString(2),
                                                hd.getInt(3));
            ls.add(add);
        }while (hd.moveToNext());
        hd.close();
        adapter.notifyDataSetChanged();
    }
}