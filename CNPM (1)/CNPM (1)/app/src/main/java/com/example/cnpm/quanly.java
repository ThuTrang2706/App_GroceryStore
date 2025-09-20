package com.example.cnpm;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class quanly extends AppCompatActivity {

    CardView qly_sp,qly_hd,qly_nv, qly_dt;
    private Button btnlogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly); // Thay đổi layout của màn hình Quản lý
        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show the back button
            getSupportActionBar().setTitle("Quản lý"); // Set the title
        }

        btnlogout = findViewById(R.id.btnlogout);
        qly_sp = findViewById(R.id.cardProduct);
        qly_hd = findViewById(R.id.cardInvoice);
        qly_nv = findViewById(R.id.cardEmployee);
        qly_dt = findViewById(R.id.cardRevenue);

        // Xử lý sự kiện khi nhấn nút Đăng xuất
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về màn hình đăng nhập
                Intent intent = new Intent(quanly.this, dangnhap.class);
                startActivity(intent);
                finish(); // Đóng màn hình quản lý khi đăng xuất
            }
        });

        qly_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sp = new Intent(quanly.this, Sanpham.class);
                startActivity(sp);
            }
        });
        qly_hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HD = new Intent(quanly.this, Ds_hd.class);
                startActivity(HD);
            }
        });
        qly_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NV = new Intent(quanly.this, qlyNv.class);
                startActivity(NV);
            }
        });

        qly_dt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent NV = new Intent(quanly.this, DoanhThu.class);
                startActivity(NV);
            }
        });
    }



    //Ham nut back tool bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Quay lại màn hình trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

