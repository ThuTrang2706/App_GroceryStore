package com.example.cnpm;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class nhanvien extends AppCompatActivity {

    private CardView cardPersonalInfo;
    private Button btnlogout;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhanvien); // Thay đổi layout của màn hình Nhân viên

        Intent get = getIntent();
        key = get.getStringExtra("email");
        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show the back button
            getSupportActionBar().setTitle("Nhân viên"); // Set the title
        }
        btnlogout = findViewById(R.id.btnlogout);
        LinearLayout taoHD = findViewById(R.id.taoHD); // Lấy LinearLayout bằng ID taoHD

        // Xử lý sự kiện khi nhấn nút Đăng xuất
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về màn hình đăng nhập
                Intent intent = new Intent(nhanvien.this, dangnhap.class);
                startActivity(intent);
                finish(); // Đóng màn hình quản lý khi đăng xuất
            }
        });

        // Thiết lập sự kiện click
        taoHD.setOnClickListener(v -> {
            // Chuyển sang Activity Hoadon
            Intent intent = new Intent(nhanvien.this, Hoadon.class);
            startActivity(intent);
        });
        cardPersonalInfo = findViewById(R.id.cardEmployee_detail);
        cardPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(nhanvien.this, TTCN.class);
                detail.putExtra("email",key);
                startActivity(detail);
                finish();
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
