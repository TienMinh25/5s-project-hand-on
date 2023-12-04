package com.tienminh.a5s_project_hand_on;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tienminh.a5s_project_hand_on.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy tên người dùng từ cơ sở dữ liệu
//        TB_NGUOI_DUNG_TENDANGNHAP thay bang bien username duoc truyen thong qua intent khi dang nhap
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String fullName = "";

        if (bundle != null) {
            fullName = bundle.getString("fullname");
        }

        // Tìm TextView trong layout
        TextView welcomeMessageTextView = findViewById(R.id.txt_welcome);

        // Tạo đoạn chào mừng và cập nhật TextView
        String welcomeMessage = "Xin chào, " + fullName;
        welcomeMessageTextView.setText(welcomeMessage);
    }
}