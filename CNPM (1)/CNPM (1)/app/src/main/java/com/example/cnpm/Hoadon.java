package com.example.cnpm;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Hoadon extends AppCompatActivity {
    private Data_Shop db;
    private EditText etTimKiem;
    private TextView tvTongTien;
    private TableLayout tableLayout;
    private List<Integer> quantities = new ArrayList<>(); // Danh sách lưu số lượng sản phẩm
    private ArrayList<pr_information> productList = new ArrayList<pr_information>();
    private int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);

        db = new Data_Shop(this);


        etTimKiem = findViewById(R.id.et_tim_kiem);
        tvTongTien = findViewById(R.id.tv_tong_tien);
        tableLayout = findViewById(R.id.tableLayout);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnThem = findViewById(R.id.btn_them);
        Button btnLuuHoaDon = findViewById(R.id.luuhd);
        Button btnXoaHoaDon = findViewById(R.id.xoahd);

        btnThem.setOnClickListener(v -> themSanPham());
        btnLuuHoaDon.setOnClickListener(v -> luuHoaDon());
        btnXoaHoaDon.setOnClickListener(v -> xoaHoaDon());

//        loadSanPham();
    }

//    private void loadSanPham() {
//        productList.clear();
//        Cursor cursor = db.getdata_product();
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String id = cursor.getString(0);
//                String name = cursor.getString(1);
//                int price = cursor.getInt(2);
//                String description = cursor.getString(3);
//
//                productList.add(new pr_information(id, name, price, description));
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//        hienThiSanPham();
//    }
    private void addNewQuantities(int newSize) {
        if (quantities == null) {
            quantities = new ArrayList<>();
        }
        while (quantities.size() < newSize) {
            quantities.add(1); // Mặc định số lượng là 1 cho sản phẩm mới
        }
    }

    private void hienThiSanPham() {
        addNewQuantities(productList.size()); // Đồng bộ danh sách số lượng với danh sách sản phẩm
        if(tableLayout.getChildCount() > 0)
        {
            tableLayout.removeAllViews();
        }
        totalAmount = 0; // Đặt lại tổng tiền

        for (int i = 0; i < productList.size(); i++) {
            pr_information product = productList.get(i);

            // Tạo TableRow mới
            TableRow row = new TableRow(this);
            row.setPadding(8, 8, 8, 8);

            // TextView: STT
            TextView tvStt = createTextView(String.valueOf(i + 1), Gravity.CENTER, 8);
            row.addView(tvStt);

            // TextView: Tên sản phẩm
            TextView tvTen = createTextView(product.getTensanpham(), Gravity.START, 8);
            row.addView(tvTen);

            // LinearLayout: Số lượng
            LinearLayout llSoLuong = new LinearLayout(this);
            llSoLuong.setOrientation(LinearLayout.HORIZONTAL);
            llSoLuong.setGravity(Gravity.CENTER);

            // ImageButton: Giảm số lượng
            ImageButton btnGiam = createImageButton(android.R.drawable.ic_media_previous, "Giảm số lượng", 80);
            llSoLuong.addView(btnGiam);

            // TextView: Số lượng
            TextView tvSoLuong = createTextView(String.valueOf(quantities.get(i)), Gravity.CENTER, 8);
            llSoLuong.addView(tvSoLuong);

            // ImageButton: Tăng số lượng
            ImageButton btnTang = createImageButton(android.R.drawable.ic_media_next, "Tăng số lượng", 80);
            llSoLuong.addView(btnTang);

            row.addView(llSoLuong);

            // TextView: Giá
            TextView tvGia = createTextView(String.valueOf(product.getGiaca()), Gravity.CENTER, 8);
            row.addView(tvGia);

            // Cộng dồn tổng tiền
            totalAmount += product.getGiaca() * quantities.get(i);

            // Xử lý sự kiện tăng/giảm số lượng
            final int index = i;
            btnGiam.setOnClickListener(v -> {
                int currentQuantity = quantities.get(index);
                if (currentQuantity > 0) {
                    quantities.set(index, currentQuantity - 1); // Giảm số lượng
                    totalAmount -= product.getGiaca();

                    // Nếu số lượng về 0, xóa sản phẩm khỏi danh sách
                    if (quantities.get(index) == 0) {
                        productList.remove(index);
                        quantities.remove(index);
                        hienThiSanPham(); // Cập nhật lại giao diện
                    } else {
                        tvSoLuong.setText(String.valueOf(quantities.get(index))); // Cập nhật số lượng hiển thị
                    }

                    tvTongTien.setText(String.valueOf(totalAmount)); // Cập nhật tổng tiền
                }
            });

            btnTang.setOnClickListener(v -> {
                int currentQuantity = quantities.get(index);
                quantities.set(index, currentQuantity + 1); // Cập nhật danh sách
                tvSoLuong.setText(String.valueOf(quantities.get(index))); // Cập nhật giao diện
                totalAmount += product.getGiaca();
                tvTongTien.setText(String.valueOf(totalAmount));
            });

            // Thêm TableRow vào TableLayout
            tableLayout.addView(row);
        }

        // Cập nhật tổng tiền
        tvTongTien.setText(String.valueOf(totalAmount));
    }


//    private void hienThiSanPham() {
////        tableLayout.removeAllViews();
//
//        totalAmount = 0;
//        for (pr_information product : productList) {
//            TableRow row = new TableRow(this);
//            TextView tvTen = new TextView(this);
//            tvTen.setText(product.getTensanpham());
//            row.addView(tvTen);
//
//            totalAmount += product.getGiaca();
//            tableLayout.addView(row);
//        }
//
//        tvTongTien.setText(String.valueOf(totalAmount));
//    }

    private void themSanPham() {
//        Cursor cursor = db.getdata_product();
//        if (cursor != null && cursor.moveToFirst()) {
//            String id = cursor.getString(0);
//            String name = cursor.getString(1);
//            int price = cursor.getInt(2);
//            String description = cursor.getString(3);
//
//            productList.add(new pr_information(id, name, price, description));
//            hienThiSanPham();
//            cursor.close();
//        } else {
//            Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
//        }
        //Code sau sua
        // Tìm kiếm sản phẩm theo mã
        String maSanPham = etTimKiem.getText().toString().trim();
        if (maSanPham.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tìm kiếm sản phẩm theo mã
        Cursor cursor = db.getProductById(maSanPham);
        if (cursor != null && cursor.moveToFirst()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            String description = cursor.getString(3);

            pr_information product = new pr_information(id, name, price, description);

            // Kiểm tra xem sản phẩm đã có trong danh sách hay chưa
            boolean exists = false;
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getMasanpham().equals(id)) {
                    quantities.set(i, quantities.get(i) + 1); // Tăng số lượng
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                productList.add(product);
                quantities.add(1); // Số lượng mặc định là 1
            }

            hienThiSanPham();
            cursor.close();

            etTimKiem.setText(null);
        } else {
            Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }

    private void luuHoaDon() {
        // Implement logic to save invoice
        Date date_current = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
// Định dạng giờ
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(date_current);; // Lấy ngày hiện tại
        String time = timeFormat.format(date_current);; // Lấy giờ hiện tại (có thể sử dụng Date/Time picker nếu cần)
        String invoiceId = "INV_" + date + "_" + time;
        int total = totalAmount; // Lấy tổng tiền từ UI

        // Lưu hóa đơn vào cơ sở dữ liệu
        boolean isInvoiceSaved = db.insertInvoice(invoiceId, date, time, total);
        if (!isInvoiceSaved) {
            Toast.makeText(this, "Lưu hóa đơn không thành công!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lưu chi tiết hóa đơn vào cơ sở dữ liệu
        for (int i = 0; i < productList.size(); i++) {
            pr_information product = productList.get(i);
            int quantity = quantities.get(i);
            int price = product.getGiaca();
            int productTotal = price * quantity;

            // Lưu chi tiết sản phẩm vào bảng invoice_detail
            boolean isDetailSaved = db.insertInvoiceDetail(invoiceId, product.getMasanpham(), product.getTensanpham(), price, quantity, productTotal);
            if (!isDetailSaved) {
                Toast.makeText(this, "Lưu chi tiết hóa đơn không thành công!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Toast.makeText(this, "Hóa đơn đã được lưu!", Toast.LENGTH_SHORT).show();

        // Xóa giỏ hàng hiện tại sau khi lưu hóa đơn
        xoaHoaDon();
    }

    private void xoaHoaDon() {
        productList.clear();
        quantities.clear(); // Xóa danh sách quantities
        tableLayout.removeAllViews();
        totalAmount = 0;
        tvTongTien.setText(String.valueOf(totalAmount));
        Toast.makeText(this, "Hóa đơn đã được xóa", Toast.LENGTH_SHORT).show();
    }

    // Hàm tiện ích để tạo TextView với các thuộc tính cơ bản
    private TextView createTextView(String text, int gravity, int padding) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(gravity);
        textView.setPadding(padding, padding, padding, padding);
        return textView;
    }

    // Hàm tiện ích để tạo ImageButton với các thuộc tính cơ bản
    private ImageButton createImageButton(int drawableResId, String contentDescription, int size) {
        ImageButton button = new ImageButton(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        button.setBackground(null); // Xóa background mặc định
        button.setImageResource(drawableResId);
        button.setContentDescription(contentDescription);
        button.setPadding(16, 16, 16, 16);
        return button;
    }

}

