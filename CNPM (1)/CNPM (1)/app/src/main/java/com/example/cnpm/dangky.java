package com.example.cnpm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class dangky extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtPhone, edtEmail;
    private Button btnRegister;
    private Data_Shop dataShop; // Lớp để tương tác với cơ sở dữ liệu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        // Ánh xạ các thành phần giao diện
        edtUsername = findViewById(R.id.etUsername);
        edtPassword = findViewById(R.id.etPassword);
        edtPhone = findViewById(R.id.phone);
        edtEmail = findViewById(R.id.etEmail);
        btnRegister = findViewById(R.id.btnRegister_button);

        // Khởi tạo đối tượng Data_Shop
        dataShop = new Data_Shop(this);

        // Thiết lập sự kiện nhấn nút Đăng ký
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegistration();
            }
        });
    }

    private void handleRegistration() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        // Kiểm tra các trường nhập liệu
        if (TextUtils.isEmpty(username)) {
            edtUsername.setError("Vui lòng nhập tên đăng nhập");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Vui lòng nhập số điện thoại");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Vui lòng nhập email");
            return;
        }

        // Lưu thông tin người dùng vào cơ sở dữ liệu
        boolean isInserted = dataShop.insertUser(username, phone, email, password, "", "", "user");

        if (isInserted) {
            // Hiển thị thông báo thành công
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

            // Chuyển sang màn hình đăng nhập
            Intent intent = new Intent(dangky.this, dangnhap.class);
            startActivity(intent);

            // Kết thúc Activity hiện tại để không quay lại màn hình đăng ký khi nhấn nút Back
            finish();
        } else {
            // Thông báo nếu không thể lưu vào cơ sở dữ liệu
            Toast.makeText(this, "Đăng ký thất bại. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
        }
    }
}
