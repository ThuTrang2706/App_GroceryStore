package com.example.cnpm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Sanpham extends AppCompatActivity {
    ListView lvproduct;
    pr_adapter adapter;
    List<pr_information> list;
    EditText edttimkiem, edtmasanpham, edttensanpham, edtgiaca, edtsoluong ;
    Button btntimkiem, btnthem, btnsua, btnxoa;
    Data_Shop dataShop;
    List<pr_information> listShop;
    TextView back_main;
    int selectPosition = -1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sanpham);

        dataShop = new Data_Shop(this);
        listShop = new ArrayList<>();

        edttimkiem = findViewById(R.id.edttimkiem);
        edtmasanpham = findViewById(R.id.edtmasanpham);
        edttensanpham = findViewById(R.id.edttensanpham);
        edtgiaca = findViewById(R.id.edtgiaca);
        edtsoluong = findViewById(R.id.edtsoluong);
        btntimkiem = findViewById(R.id.btntimkiem);
        btnthem = findViewById(R.id.btnthem);
        btnsua = findViewById(R.id.btnsua);
        btnxoa = findViewById(R.id.btnxoa);
        back_main = findViewById(R.id.back_main);

        back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sanpham.this, quanly.class);
                startActivity(intent);
            }
        });

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themProduct();
                refresh();
            }
        });

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaProduct();
            }
        });

        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaProduct();
            }
        });

        btntimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timkiemProduct();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvproduct = (ListView) findViewById(R.id.lvproduct);
        downloadData();
        adapter = new pr_adapter(Sanpham.this, R.layout.product_line, listShop);
        lvproduct.setAdapter(adapter);

        lvproduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectPosition = i;
                pr_information productDelete = listShop.get(i);
                {
                    edtmasanpham.setText(productDelete.getMasanpham());
                    edttensanpham.setText(productDelete.getTensanpham());
                    edtgiaca.setText(String.valueOf(productDelete.getGiaca()));
                    edtsoluong.setText(productDelete.getMota());
                }
                pr_information productUpdate = listShop.get(i);
            }
        });
    }

    private void refresh()
    {
        edtmasanpham.setText("");
        edttensanpham.setText("");
        edtgiaca.setText("");
        edtsoluong.setText("");
        selectPosition = -1;
    }

    private void themProduct()
    {
        if(edtmasanpham.getText().toString().isEmpty() || edttensanpham.getText().toString().isEmpty() || edtgiaca.getText().toString().isEmpty() || edtsoluong.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Nhap day du thong tin di da", Toast.LENGTH_SHORT).show();
            return;
        }
        String masanpham = edtmasanpham.getText().toString().trim();
        String tensanpham = edttensanpham.getText().toString().trim();
        int giaca = Integer.parseInt(edtgiaca.getText().toString().trim());
        String mota = edtsoluong.getText().toString().trim();

        boolean kq = dataShop.insertdata_product(masanpham, tensanpham, giaca, mota);
        if (kq)
        {
            Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Lỗi!", Toast.LENGTH_SHORT).show();
        listShop.clear();
        downloadData();
        lvproduct.setAdapter(adapter);
    }

    private void suaProduct()
    {
        if (selectPosition == -1)
        {
            Toast.makeText(this, "Vui lòng chọn sản phẩm để sửa!", Toast.LENGTH_SHORT).show();
            return;
        }
        String masanpham = edtmasanpham.getText().toString();
        String tensanpham = edttensanpham.getText().toString();
        int giaca = Integer.parseInt(edtgiaca.getText().toString());
        String mota = edtsoluong.getText().toString();

        boolean kq = dataShop.updatedata_product(masanpham, tensanpham, giaca, mota);
        if (kq)
        {
            pr_information productUpdate = listShop.get(selectPosition);
            productUpdate.setMasanpham(masanpham);
            productUpdate.setTensanpham(tensanpham);
            productUpdate.setGiaca(giaca);
            productUpdate.setMota(mota);
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        } else
        {
            Toast.makeText(this, "Lỗi!", Toast.LENGTH_SHORT).show();
        }
        refresh();
    }

    private void xoaProduct()
    {
        if (selectPosition == -1)
        {
            Toast.makeText(this, "Vui lòng chọn sản phẩm để xóa!", Toast.LENGTH_SHORT).show();
            return;
        }
//Chi lay masanpham tu dsach da chon
        pr_information productDelete = listShop.get(selectPosition);
        String masanpham = productDelete.getMasanpham();

        boolean kq = dataShop.deletedata_product(masanpham);
        if (kq)
        {
            Toast.makeText(this, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
            listShop.remove(selectPosition);
        } else
        {
            Toast.makeText(this, "Lỗi!", Toast.LENGTH_SHORT).show();
        }
        lvproduct.setAdapter(adapter);
        refresh();
    }

    public void timkiemProduct() {
        String keyword = edttimkiem.getText().toString().trim();
        if (keyword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập từ khóa tìm kiếm!", Toast.LENGTH_SHORT).show();
            return;
        }
        listShop.clear();
        Cursor cursor = dataShop.searchdata_product(keyword);
        if (cursor.moveToFirst()) {
            do {
                pr_information product = new pr_information(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3)
                );
                listShop.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void downloadData() {
        Cursor cursor = dataShop.getdata_product();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                pr_information fb_info = new pr_information(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3)
                );
                listShop.add(fb_info);
            } while (cursor.moveToNext());
        } else {
            Log.e("DatabaseError", "Cursor rỗng hoặc không có dữ liệu.");
        }
        if (cursor != null) {
            cursor.close(); // Đóng Cursor để tránh rò rỉ bộ nhớ
        }
    }
}