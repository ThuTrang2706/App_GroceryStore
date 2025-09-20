package com.example.cnpm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class InvoiceDetailsActivity extends AppCompatActivity {

    private TableLayout tblInvoiceDetails;
    private TextView tvInvoiceTotal;

    private Data_Shop database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_details);
        tblInvoiceDetails = findViewById(R.id.tableLayout_hd);
        tvInvoiceTotal = findViewById(R.id.tv_invoice_total);

        database = new Data_Shop(this);
        Intent get = getIntent();
        loadInvoiceDetails(get.getStringExtra("id"));
    }


    private void loadInvoiceDetails(String invoiceId) {
        // Xóa dữ liệu cũ trong TableLayout
        int childCount = tblInvoiceDetails.getChildCount();
        if (childCount > 1) {
            tblInvoiceDetails.removeViews(1, childCount - 1);
        }

        Cursor cursor = database.getReadableDatabase().rawQuery(
                "SELECT product_id, name_product, price, quantity FROM invoice_detail WHERE id_invoice_detail = ?",
                new String[]{invoiceId});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                double totalPrice = 0;

                do {
                    String productId = cursor.getString(0);
                    String productName = cursor.getString(1);
                    double price = cursor.getDouble(2);
                    int quantity = cursor.getInt(3);
                    double itemTotal = price * quantity;
                    totalPrice += itemTotal;

                    // Tạo dòng mới trong bảng
                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1.0f

                    );
                    TableRow.LayoutParams params_productname = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            2.0f

                    );

                    row.setLayoutParams(params);

                    TextView tvProductId = new TextView(this);
                    tvProductId.setText(productId);
                    tvProductId.setPadding(8, 8, 8, 8);
                    tvProductId.setLayoutParams(params);

                    TextView tvProductName = new TextView(this);
                    tvProductName.setText(productName);
                    tvProductName.setPadding(8, 8, 8, 8);
                    tvProductName.setLayoutParams(params_productname);

                    TextView tvPrice = new TextView(this);
                    tvPrice.setText(String.valueOf(price));
                    tvPrice.setPadding(8, 8, 8, 8);
                    tvPrice.setLayoutParams(params_productname);

                    TextView tvQuantity = new TextView(this);
                    tvQuantity.setText(String.valueOf(quantity));
                    tvQuantity.setPadding(8, 8, 8, 8);
                    tvQuantity.setLayoutParams(params);

                    tvProductId.setGravity(Gravity.CENTER);
                    tvProductName.setGravity(Gravity.CENTER);
                    tvPrice.setGravity(Gravity.CENTER);
                    tvQuantity.setGravity(Gravity.CENTER);

                    row.addView(tvProductId);
                    row.addView(tvProductName);
                    row.addView(tvPrice);
                    row.addView(tvQuantity);

                    tblInvoiceDetails.addView(row);

                } while (cursor.moveToNext());

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                tvInvoiceTotal.setText("Tổng giá hóa đơn: " + currencyFormat.format(totalPrice));
            } else {
                Toast.makeText(this, "Không tìm thấy hóa đơn!", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Lỗi khi truy vấn dữ liệu!", Toast.LENGTH_SHORT).show();
        }
    }
}
