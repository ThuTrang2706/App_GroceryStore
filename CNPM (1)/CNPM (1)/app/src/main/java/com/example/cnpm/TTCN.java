package com.example.cnpm;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TTCN extends AppCompatActivity {
    Data_Shop db = new Data_Shop(TTCN.this);

    EditText name,phone,mail,birth,mk;
    TextView change_mk,back;
    Button fix,save;
    String key;
    int id;
    boolean status_change = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ttcn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        Intent get = getIntent();
        key = get.getStringExtra("email");
        get_infor();
        fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlock();
            }
        });
        change_mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!status_change)
                {
                    showChangePasswordDialog();
                }
                else{
                    db.updatePasswordByEmail(key,mk.getText().toString());
                    change_mk.setText("Change");
                    mk.setEnabled(false);
                    mk.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    status_change = false;
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = db.get_id_user(key);
                db.updateUserInfoById(id,name.getText().toString(),phone.getText().toString(),mail.getText().toString(),birth.getText().toString());
                lock();
                get_infor();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(TTCN.this,nhanvien.class);
                back.putExtra("email",key);
                startActivity(back);
                finish();
            }
        });
    }
    private void showChangePasswordDialog() {
        // Tạo AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(TTCN.this);
        builder.setTitle("Đổi mật khẩu");

        // Tạo view từ layout tùy chỉnh
        final EditText newPasswordEditText = new EditText(TTCN.this);
        newPasswordEditText.setHint("Nhập mật cũ");
        builder.setView(newPasswordEditText);
        // Thiết lập các nút hành động
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pass_ols = newPasswordEditText.getText().toString().trim();
                if(db.checkUser(key,pass_ols) == -1)
                {
                    Toast.makeText(TTCN.this, "Mat khau khong dung", Toast.LENGTH_SHORT).show();
                }
                else {
                    status_change = true;
                    change_mk.setText("Save");
                    mk.setText(null);
                    mk.setEnabled(true);
                    mk.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                change_mk.setText("Change");
            }
        });

        // Hiển thị dialog
        builder.create().show();
    }

    private void mapping()
    {
        name = findViewById(R.id.edtten_nv);
        phone = findViewById(R.id.edt_phone_nv);
        mail = findViewById(R.id.edt_mail_nv);
        birth = findViewById(R.id.edt_birth_nv);
        mk =findViewById(R.id.edt_pass);
        change_mk = findViewById(R.id.tv_doimatkhau);
        fix = findViewById(R.id.btn_fix);
        save = findViewById(R.id.btn_save);
        back = findViewById(R.id.back_main);
    }

    private void unlock()
    {
        name.setEnabled(true);
        phone.setEnabled(true);
        mail.setEnabled(true);
        birth.setEnabled(true);
        save.setEnabled(true);
        fix.setEnabled(false);
    }

    private void lock()
    {
        name.setEnabled(false);
        phone.setEnabled(false);
        mail.setEnabled(false);
        birth.setEnabled(false);
        save.setEnabled(false);
        fix.setEnabled(true);
    }

    private void get_infor()
    {
        Cursor nv = db.getUserData("SELECT * from user where email = '" + key + "'");
        nv.moveToFirst();
        do {
            name.setText( nv.getString(1));
            phone.setText( nv.getString(2));
            mail.setText( nv.getString(3));
            birth.setText( nv.getString(6));
            mk.setText( nv.getString(4));

        }while (nv.moveToNext());
    }
}