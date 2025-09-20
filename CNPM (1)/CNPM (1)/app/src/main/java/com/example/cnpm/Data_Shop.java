package com.example.cnpm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Data_Shop extends SQLiteOpenHelper {

    private static final String DB_NAME = "Shop.db";
    private static final int VERSION = 4;

    // User Table
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "id_user";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PHONE = "phone";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_GENDER = "gender";
    private static final String COLUMN_USER_DOB = "date_of_birth";
    private static final String COLUMN_USER_ROLE = "role";

    // Product Table
    private static final String TABLE_PRODUCT = "product";
    private static final String COLUMN_PRODUCT_ID = "id_product";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "description";

    // Invoice Table
    private static final String TABLE_INVOICE = "invoice";
    private static final String COLUMN_INVOICE_ID = "id_invoice";
    private static final String COLUMN_INVOICE_DATE = "date";
    private static final String COLUMN_INVOICE_TIME = "time";
    private static final String COLUMN_INVOICE_TOTAL = "total";

    // Invoice Detail Table
    private static final String TABLE_INVOICE_DETAIL = "invoice_detail";
    private static final String COLUMN_INVOICE_DETAIL_ID = "id_invoice_detail";
    private static final String COLUMN_INVOICE_PRODUCT_ID = "product_id";
    private static final String COLUMN_INVOICE_PRODUCT_NAME = "name_product";
    private static final String COLUMN_INVOICE_PRODUCT_PRICE = "price";
    private static final String COLUMN_INVOICE_PRODUCT_QUANTITY = "quantity";
    private static final String COLUMN_INVOICE_PRODUCT_TOTAL = "total";

    public Data_Shop(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User table
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT NOT NULL, " +
                COLUMN_USER_PHONE + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_USER_GENDER + " TEXT, " +
                COLUMN_USER_DOB + " TEXT, " +
                COLUMN_USER_ROLE + " TEXT);";
        db.execSQL(CREATE_USER_TABLE);

        // Create Product table
        String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT + " (" +
                COLUMN_PRODUCT_ID + " TEXT PRIMARY KEY, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_PRODUCT_PRICE + " INTEGER, " +
                COLUMN_PRODUCT_DESCRIPTION + " TEXT" +
                ");";
        db.execSQL(CREATE_PRODUCT_TABLE);

        // Create Invoice table
        String CREATE_INVOICE_TABLE = "CREATE TABLE " + TABLE_INVOICE + " (" +
                COLUMN_INVOICE_ID + " TEXT PRIMARY KEY, " +
                COLUMN_INVOICE_DATE + " TEXT, " +
                COLUMN_INVOICE_TIME + " TEXT, " +
                COLUMN_INVOICE_TOTAL + " INTEGER);";
        db.execSQL(CREATE_INVOICE_TABLE);

        // Create Invoice Detail table
        String CREATE_INVOICE_DETAIL_TABLE = "CREATE TABLE " + TABLE_INVOICE_DETAIL + " (" +
                COLUMN_INVOICE_DETAIL_ID + " TEXT , " +
                COLUMN_INVOICE_PRODUCT_ID + " TEXT, " +
                COLUMN_INVOICE_PRODUCT_NAME + " TEXT, " +
                COLUMN_INVOICE_PRODUCT_PRICE + " REAL, " +
                COLUMN_INVOICE_PRODUCT_QUANTITY + " INTEGER, " +
                COLUMN_INVOICE_PRODUCT_TOTAL + " REAL, " +
                "PRIMARY KEY (" + COLUMN_INVOICE_DETAIL_ID + "," + COLUMN_INVOICE_PRODUCT_ID + ")," +
                "FOREIGN KEY (" + COLUMN_INVOICE_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCT + "(" + COLUMN_PRODUCT_ID + "));";
        db.execSQL(CREATE_INVOICE_DETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICE_DETAIL);
        onCreate(db);
    }

    // User-related methods
    public boolean insertUser(String name, String phone, String email, String password, String gender, String dob, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_PHONE, phone);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_GENDER, gender);
        values.put(COLUMN_USER_DOB, dob);
        values.put(COLUMN_USER_ROLE, role);

        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    public int checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?", new String[]{username, password});
        if (cursor.getCount() >0)
        {
            cursor.moveToFirst();
            String vaitro  = cursor.getString(7).trim();

            if(vaitro.equalsIgnoreCase("ql"))
            {
                return 0;
            }
            if (vaitro.equalsIgnoreCase("nv"))
            {
                return 1;
            }
        }
        return -1;
    }

    public String getUserDetail(String username, String columnName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + columnName + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        String result = null;
        if (cursor.moveToFirst()) {
            result = cursor.getString(0);
        }
        cursor.close();
        return result;
    }

    public boolean updateUserInfo(int userId, String name, String phone, String email, String password, String gender, String dob, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Set the updated values
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_PHONE, phone);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_GENDER, gender);
        values.put(COLUMN_USER_DOB, dob);
        values.put(COLUMN_USER_ROLE, role);

        // Perform update where id_user matches
        int result = db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        return result > 0; // Return true if at least one row was updated
    }

    public boolean deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_USER, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close(); // Đóng kết nối với cơ sở dữ liệu
        return rows > 0; // Trả về true nếu xóa thành công ít nhất một dòng
    }

    // Product-related methods
    public Boolean insertdata_product(String id, String name, int price, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCT_ID, id);
        cv.put(COLUMN_PRODUCT_NAME, name);
        cv.put(COLUMN_PRODUCT_PRICE, price);
        cv.put(COLUMN_PRODUCT_DESCRIPTION, description);
        long res = db.insert(TABLE_PRODUCT, null, cv);
        if (res == -1) {
            Log.d("aaa", "insertdata_product: Khong thanh cong");
            return false;
        }
        else{
            Log.d("aaa", "insertdata_product: Thanh cong");
            return true;
        }
    }

    public Boolean updatedata_product(String id, String name, int price, String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCT_ID, id);
        cv.put(COLUMN_PRODUCT_NAME, name);
        cv.put(COLUMN_PRODUCT_PRICE, price);
        cv.put(COLUMN_PRODUCT_DESCRIPTION, description);
        long update = db.update(TABLE_PRODUCT, cv, COLUMN_PRODUCT_ID + "=?", new String[]{id});
        if (update == -1)
        {
            Log.d("aaa", "updatedata_product: Khong thanh cong");
            return false;
        } else
        {
            Log.d("aaa", "updatedata_product: Thanh cong");
            return true;
        }
    }
    public Boolean deletedata_product(String masanpham)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_PRODUCT, COLUMN_PRODUCT_ID + "=?", new String[]{masanpham});
        db.close();
        return rows > 0;
    }

    public Cursor searchdata_product(String keyword)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = " SELECT * FROM " + TABLE_PRODUCT +
                " WHERE " + COLUMN_PRODUCT_ID + " LIKE ? OR " +
                COLUMN_PRODUCT_NAME + " LIKE ? OR " +
                COLUMN_PRODUCT_PRICE + " LIKE ? OR " +
                COLUMN_PRODUCT_DESCRIPTION + " LIKE ? ";
        String searchKey = "%" + keyword + "%";
        Cursor cursor = db.rawQuery(sql, new String[]{searchKey, searchKey, searchKey, searchKey});
        return cursor;
    }


    public Cursor getdata_product(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PRODUCT, null);
        return cursor;
    }

    public Cursor getProductById(String productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM product WHERE id_product = ?", new String[]{productId});
    }

    public boolean insertInvoice(String invoiceId, String date, String time, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INVOICE_ID, invoiceId);
        values.put(COLUMN_INVOICE_DATE, date);
        values.put(COLUMN_INVOICE_TIME, time);
        values.put(COLUMN_INVOICE_TOTAL, total);

        long result = db.insert(TABLE_INVOICE, null, values);
        return result != -1;
    }

    public boolean insertInvoiceDetail(String invoiceId, String productId, String productName, int price, int quantity, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INVOICE_DETAIL_ID, invoiceId);  // Có thể sử dụng ID hóa đơn làm ID chi tiết hoặc tạo ID riêng
        values.put(COLUMN_INVOICE_PRODUCT_ID, productId);
        values.put(COLUMN_INVOICE_PRODUCT_NAME, productName);
        values.put(COLUMN_INVOICE_PRODUCT_PRICE, price);
        values.put(COLUMN_INVOICE_PRODUCT_QUANTITY, quantity);
        values.put(COLUMN_INVOICE_PRODUCT_TOTAL, total);

        long result = db.insert(TABLE_INVOICE_DETAIL, null, values);
        return result != -1;
    }

    public Cursor getUserData(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(sql, null);
        return cur;
    }

    public Cursor find_nv(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cur = db.rawQuery("SELECT * FROM user where user_name = ?",new String[] {name});
        return  cur;
    }

    public boolean updatePasswordByEmail(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PASSWORD, newPassword);

        // Thực hiện cập nhật mật khẩu cho người dùng có email tương ứng
        int result = db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?", new String[]{email});
        return result > 0;
    }

    public boolean updateUserInfoById(int userId, String name, String phone, String email, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Cập nhật các trường thông tin
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_PHONE, phone);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_DOB, dob);

        // Thực hiện cập nhật thông tin người dùng dựa trên id_user
        int result = db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        return result > 0; // Trả về true nếu có ít nhất một dòng bị cập nhật
    }

    public int get_id_user(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_user FROM user WHERE email = ?", new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            // Kiểm tra nếu cursor có dữ liệu
            int userId = cursor.getInt(0);
            cursor.close();
            return userId; // Trả về id_user nếu có dữ liệu
        } else {
            cursor.close();
            return -1; // Trả về -1 nếu không có dữ liệu
        }
    }

    public Cursor getInvoiceById(String invoiceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INVOICE + " WHERE " + COLUMN_INVOICE_ID + " = ?", new String[]{invoiceId});
        return cursor;
    }

    public Cursor getAllInvoices() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INVOICE, null);
        return cursor;
    }
    public int calculateRevenue(String date1, String date2){
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            // Chuyển đổi định dạng ngày
            Date test1 = inputFormat.parse(date1);
            Date test2 = inputFormat.parse(date2);
            date1 = outputFormat.format(test2);
            date2 = outputFormat.format(test2);
        } catch (ParseException e) {
            e.printStackTrace();
             // Trả về danh sách rỗng nếu lỗi xảy ra
        }
        SQLiteDatabase db = this.getReadableDatabase();
        int res = 0;
        String query = "SELECT " + COLUMN_INVOICE_TOTAL + " FROM " + TABLE_INVOICE
                + " WHERE " + COLUMN_INVOICE_DATE + " BETWEEN ? AND ?";
        Cursor cs =  db.rawQuery(query, new String[]{date1, date2});
        if (cs.moveToFirst()) {
            do {
                // Cộng dồn tổng doanh thu từ các dòng
                res += cs.getInt(0);
            } while (cs.moveToNext());
        }
        cs.close();
        Log.d("Revenue Calculation", "Total Revenue: " + res);
        return res;
    }
    public void displayAllInvoices() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query to select all data from the invoice table
        String query = "SELECT * FROM " + TABLE_INVOICE;

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Check if the cursor contains data
        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ từng cột
                String id = cursor.getString(0);
                String date = cursor.getString(1);
                String time = cursor.getString(2);
                int total = cursor.getInt(3);

                // Hiển thị trực tiếp qua Logcat
                Log.d("Invoice Data", "ID: " + id + ", Date: " + date + ", Time: " + time + ", Total: " + total);
            } while (cursor.moveToNext());
        } else {
            Log.d("Invoice Data", "No data found in the invoice table.");
        }

        // Đóng Cursor
        cursor.close();
    }

    public void displayAllInvoiceDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query to select all data from the invoice_detail table
        String query = "SELECT * FROM " + TABLE_INVOICE_DETAIL;

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Kiểm tra xem bảng có dữ liệu hay không
        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ từng cột
                String detailId = cursor.getString(0);
                String productId = cursor.getString(1);
                String productName = cursor.getString(2);
                double productPrice = cursor.getDouble(3);
                int quantity = cursor.getInt(4);
                double total = cursor.getDouble(5);

                // Hiển thị dữ liệu qua Logcat
                Log.d("InvoiceDetail Data",
                        "Detail ID: " + detailId +
                                ", Product ID: " + productId +
                                ", Product Name: " + productName +
                                ", Price: " + productPrice +
                                ", Quantity: " + quantity +
                                ", Total: " + total);
            } while (cursor.moveToNext());
        } else {
            Log.d("InvoiceDetail Data", "No data found in the invoice_detail table.");
        }

        // Đóng Cursor
        cursor.close();
    }

    public int calculateTotalInRange(String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSum = 0;

        String query = "SELECT SUM(" + COLUMN_INVOICE_TOTAL + ") AS total_sum " +
                "FROM " + TABLE_INVOICE +
                " WHERE " + COLUMN_INVOICE_DATE + " BETWEEN ? AND ?";

        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                totalSum = cursor.getInt(0);
            }
            cursor.close();
        }

        db.close();
        return totalSum;
    }


}



