package com.tienminh.a5s_project_hand_on;

import static com.tienminh.a5s_project_hand_on.database.DatabaseHelper.TB_NGUOIDUNG_TENDANGNHAP;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.tienminh.a5s_project_hand_on.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        // Lấy tên người dùng từ cơ sở dữ liệu
        String userName = databaseHelper.getUserName(TB_NGUOIDUNG_TENDANGNHAP);

        // Tìm TextView trong layout
        TextView welcomeMessageTextView = findViewById(R.id.txt_welcome);

        // Tạo đoạn chào mừng và cập nhật TextView
        String welcomeMessage = "Xin chào, " + userName;
        welcomeMessageTextView.setText(welcomeMessage);
    }
}