package com.tienminh.a5s_project_hand_on.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tienminh.a5s_project_hand_on.R;

public class MainActivity extends AppCompatActivity {
    Button btnLogout;
    Button btnChamDiem;
    Button btnTrangChu;
    Button btnHoTro;
    Button btnBaoCao;
    TextView welcomeMessageTextView;
    String fullName = "";
    Integer user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.btnLogout);
        btnChamDiem = findViewById(R.id.btnChamDiem);
        btnTrangChu = findViewById(R.id.trangChu);
        btnHoTro = findViewById(R.id.hoTro);
        btnBaoCao = findViewById(R.id.btnBaoCao);

        // Lấy tên người dùng từ cơ sở dữ liệu
//        TB_NGUOI_DUNG_TENDANGNHAP thay bang bien username duoc truyen thong qua intent khi dang nhap
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            fullName = bundle.getString("fullname");
            user_id = bundle.getInt("user_id");
        }

        // Tìm TextView trong layout
        welcomeMessageTextView = findViewById(R.id.txt_welcome);

        // Tạo đoạn chào mừng và cập nhật TextView
        String welcomeMessage = "Xin chào, " + fullName;
        welcomeMessageTextView.setText(welcomeMessage);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class); // Tạo intent để mở SignInActivity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa hết các activity trước đó
                startActivity(intent);
            }
        });

        btnChamDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MarkActivity.class); // Tạo intent để mở SignInActivity
                Bundle bundle1 = new Bundle();
                bundle1.putString("fullname", fullName);
                bundle1.putInt("user_id", user_id);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btnTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://www.haui.edu.vn/vn");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });

        btnHoTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://www.haui.edu.vn/vn/page/chuongtrinh5s");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });

        btnBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, BaoCaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fullname", fullName);
                bundle.putInt("user_id", user_id);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        String welcomeMessage = "Xin chào, " + fullName;
        Log.d("TEST", welcomeMessage);

        welcomeMessageTextView.setText(welcomeMessage);
    }
}