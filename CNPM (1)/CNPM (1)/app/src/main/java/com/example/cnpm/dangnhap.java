package com.example.cnpm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class dangnhap extends AppCompatActivity {
    Data_Shop db = new Data_Shop(dangnhap.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        //db.insertUser("Trang","01234567","trang04@gmail.com","123","Nu","27/06/2004","ql");
       //db.insertUser("Kien","01234567","kien04@gmail.com","123","Nam","22/10/2004","nv");

        // Initialize views
        EditText etUsername = findViewById(R.id.edtTenDangNhap);
        EditText etPassword = findViewById(R.id.edtMatKhau);
        Button btnLogin = findViewById(R.id.login_button);
        Button btnRegister = findViewById(R.id.btnDangKy2);

        // Login button action
        btnRegister.setOnClickListener(view -> {
            // Chuyển sang màn hình đăng ký
            Intent intent = new Intent(dangnhap.this, dangky.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(view -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();
            if(db.checkUser(user,pass) == -1)
            {
                Toast.makeText(this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(db.checkUser(user,pass) == 0)
            {
                Intent qly = new Intent(dangnhap.this,quanly.class);
                startActivity(qly);
            }
            else if(db.checkUser(user,pass) == 1)
            {
                Intent nvien = new Intent(dangnhap.this,nhanvien.class);
                nvien.putExtra("email",user);
                startActivity(nvien);
            }
    });
}
}
