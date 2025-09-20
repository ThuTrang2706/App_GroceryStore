package com.example.cnpm;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DoanhThu extends AppCompatActivity {

    private EditText startDateEditText, endDateEditText;
    private Button filterButton;
    private TextView totalRevenueTextView;

    private Calendar startDateCalendar, endDateCalendar;
    Data_Shop db = new Data_Shop(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);

        // Khởi tạo view
        startDateEditText = findViewById(R.id.startDate);
        endDateEditText = findViewById(R.id.endDate);
        filterButton = findViewById(R.id.filterButton);
        totalRevenueTextView = findViewById(R.id.totalRevenue);

        startDateCalendar = Calendar.getInstance();
        endDateCalendar = Calendar.getInstance();

        // Thiết lập sự kiện chọn ngày cho EditText
        setupDatePicker(startDateEditText, startDateCalendar);
        setupDatePicker(endDateEditText, endDateCalendar);

        // Xử lý khi nhấn nút "Lọc"
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterRevenue();
            }
        });
    }

    // Hàm thiết lập DatePicker cho EditText
    private void setupDatePicker(EditText editText, Calendar calendar) {
        editText.setOnClickListener(v -> {
            new DatePickerDialog(
                    DoanhThu.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        updateEditText(editText, calendar);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    // Cập nhật ngày đã chọn vào EditText
    private void updateEditText(EditText editText, Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        editText.setText(dateFormat.format(calendar.getTime()));
    }

    // Hàm lọc doanh thu
    private void filterRevenue() {
        String startDate = startDateEditText.getText().toString().trim();
        String endDate = endDateEditText.getText().toString().trim();

        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!", Toast.LENGTH_SHORT).show();
            return;
        }

        Data_Shop db = new Data_Shop(this);

        int totalRevenue = db.calculateTotalInRange(formatDate(startDate), formatDate(endDate));
        db.displayAllInvoices();
        db.displayAllInvoiceDetails();
        // Hiển thị tổng doanh thu
        totalRevenueTextView.setText("Tổng doanh thu: " + totalRevenue + " VND");
    }

    public String formatDate(String inputDate) {
        // Định dạng đầu vào (dd/MM/yyyy)
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        // Định dạng đầu ra (yyyy-MM-dd)
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            // Chuyển chuỗi ngày đầu vào thành đối tượng Date
            Date date = inputFormat.parse(inputDate);
            // Chuyển đổi đối tượng Date sang chuỗi theo định dạng đầu ra
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // Trả về chuỗi rỗng nếu xảy ra lỗi
            return "";
        }
    }

}
