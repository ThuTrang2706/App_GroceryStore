package com.example.cnpm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Vaitro extends AppCompatActivity {

    private CardView cardManager, cardEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaitro);

        // Ánh xạ các thành phần giao diện
        cardManager = findViewById(R.id.cardManager);
        cardEmployee = findViewById(R.id.cardEmployee);

        // Xử lý sự kiện chọn "Quản lý"
        cardManager.setOnClickListener(v -> {
            Log.d("Vaitro", "Card Manager clicked");
            Intent intent = new Intent(Vaitro.this, quanly.class); // Chuyển sang màn hình quản lý
            startActivity(intent);
            Toast.makeText(this, "Bạn đã chọn vai trò Quản lý", Toast.LENGTH_SHORT).show();
        });

        // Xử lý sự kiện chọn "Nhân viên"
       cardEmployee.setOnClickListener(v -> {
           Intent intent = new Intent(Vaitro.this, nhanvien.class); // Chuyển sang màn hình nhân viên
           startActivity(intent);
            Toast.makeText(this, "Bạn đã chọn vai trò Nhân viên", Toast.LENGTH_SHORT).show();
        });
    }
}

