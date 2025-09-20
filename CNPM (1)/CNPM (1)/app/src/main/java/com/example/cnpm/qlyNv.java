package com.example.cnpm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class qlyNv extends AppCompatActivity {
    Data_Shop db = new Data_Shop(qlyNv.this);
    Nv_adapter adapter ;
    List<Nv_class> ls = new ArrayList<>();
    ListView lv;
    Button find,add,fix,del;
    EditText find_edt,name,phone,email,birth;
    CheckBox  male,female;
    String gender;
    TextView back;
    int id_nv = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qly_nv);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        adapter = new Nv_adapter(qlyNv.this,R.layout.adapter_ls_nv,ls);
        get_nv_db("SELECT * from user where role = 'nv'");
        lv.setAdapter(adapter);

        find_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty())
                {
                    get_nv_db("SELECT * FROM user");
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(male.isChecked())
                {
                    female.setChecked(false);
                    gender = "Nam";
                }
                if(!male.isChecked())
                {
                    female.setChecked(true);
                    gender = "Nu";
                }
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(female.isChecked())
                {
                    male.setChecked(false);
                    gender = "Nu";
                }
                if(!female.isChecked())
                {
                    male.setChecked(true);
                    gender = "Nam";
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(qlyNv.this, quanly.class);
                startActivity(main);
                finish();
            }
        });
        //
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id_nv = ls.get(position).getId();
                name.setText(ls.get(position).getName());
                phone.setText(ls.get(position).getPhone());
                email.setText(ls.get(position).getEmail());
                birth.setText(ls.get(position).getBirth());
                if(ls.get(position).getGender().equals("Nam"))
                {
                    male.setChecked(true);
                    female.setChecked(false);
                }
                else
                {
                    female.setChecked(true);
                    male.setChecked(false);
                }
            }
        });
        //
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_nv();
            }
        });
        //
        fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fix_nv(id_nv);
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del_nv(id_nv);
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = find_edt.getText().toString();
                Cursor nv = db.find_nv(ten);
                nv.moveToFirst();
                ls.clear();
                do
                {
                    Nv_class add1 = new Nv_class(nv.getInt(0),
                            nv.getString(1),
                            nv.getString(2),
                            nv.getString(3),
                            nv.getString(6),
                            nv.getString(5));
                    ls.add(add1);
                }while(nv.moveToNext());
                nv.close();
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void mapping()
    {
        lv = findViewById(R.id.lvNv);
        find = findViewById(R.id.btntimkiem_nv);
        add = findViewById(R.id.btnthem);
        fix = findViewById(R.id.btnsua);
        del = findViewById(R.id.btnxoa);
        find_edt = findViewById(R.id.edttimkiem_nv);
        name = findViewById(R.id.edttennv);
        phone = findViewById(R.id.edtsdt);
        email = findViewById(R.id.edtmail);
        birth = findViewById(R.id.edtbrith);
        male = findViewById(R.id.cbMale);
        female = findViewById(R.id.cbFemale);
        back = findViewById(R.id.back_main);


    }
    private void get_nv_db(String sql)
    {
        ls.clear();
        Cursor nv = db.getUserData(sql);
        nv.moveToFirst();
        do
        {
            Nv_class add1 = new Nv_class(nv.getInt(0),
                    nv.getString(1),
                    nv.getString(2),
                    nv.getString(3),
                    nv.getString(6),
                    nv.getString(5));
            ls.add(add1);
        }while(nv.moveToNext());
        nv.close();
        adapter.notifyDataSetChanged();
    }

    public static boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        sdf.setLenient(false); // Không cho phép ngày tháng không hợp lệ (ví dụ: 32/13/2025)

        try {
            // Nếu chuỗi có thể phân tích thành một ngày hợp lệ, hàm sẽ trả về true
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            // Nếu xảy ra ngoại lệ, nghĩa là chuỗi không phải là định dạng hợp lệ
            return false;
        }
    }

    private void reset()
    {
        name.setText("");
        phone.setText("");
        email.setText("");
        birth.setText("");
        male.setChecked(false);
        female.setChecked(false);
        id_nv = -1;
    }
    private boolean check_info()
    {
        if(name.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || email.getText().toString().isEmpty()
                || birth.getText().toString().isEmpty() || !isValidDate(birth.getText().toString()) || (!male.isChecked() && !female.isChecked()))
        {
            return false;
        }
        return true;
    }
    private void add_nv()
    {
        if(!check_info())
        {
            Toast.makeText(this, "Dien day du thong tin di da", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
//            if(male.isChecked())
//            {
//                gender = "Nam";
//            }
//            if(female.isChecked())
//            {
//                gender = "Nu";
//            }
            db.insertUser(name.getText().toString(),phone.getText().toString(),email.getText().toString(),"1",gender,birth.getText().toString(),"nv");
            get_nv_db("SELECT * from user");
            adapter.notifyDataSetChanged();
            reset();
        } catch (Exception e) {
            Toast.makeText(this, "Loi them nhan vien", Toast.LENGTH_SHORT).show();
        }
    }

    private void fix_nv(int id_nv)
    {
        if(!check_info() || id_nv == -1)
        {
            Toast.makeText(this, "Hay chon doi tuong can sua", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            db.updateUserInfo(id_nv,name.getText().toString(),phone.getText().toString(),email.getText().toString(),"1",gender,birth.getText().toString(),"nv");
            get_nv_db("SELECT * from user");
            adapter.notifyDataSetChanged();
            reset();
        } catch (Exception e) {
            Toast.makeText(this, "Loi sua nhan vien", Toast.LENGTH_SHORT).show();
        }
    }
    private void del_nv(int id_nv)
    {
        if(id_nv == -1)
        {
            Toast.makeText(this, "Hay chon doi tuong can sua", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            db.deleteUser(id_nv);
            get_nv_db("SELECT * from user");
            adapter.notifyDataSetChanged();
            reset();
        } catch (Exception e) {
            Toast.makeText(this, "Loi sua nhan vien", Toast.LENGTH_SHORT).show();
        }
    }

}