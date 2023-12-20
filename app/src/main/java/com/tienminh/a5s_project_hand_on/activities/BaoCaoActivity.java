package com.tienminh.a5s_project_hand_on.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tienminh.a5s_project_hand_on.R;

public class BaoCaoActivity extends AppCompatActivity {
    TextView tieude;
    String fullName = "";
    Integer user_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_actv);

        Button btnOffice = findViewById(R.id.btnOffice);
        Button btnLectureHall = findViewById(R.id.btnLectureHall);
        Button btnLab = findViewById(R.id.btnLab);
        Button btnCommon = findViewById(R.id.btnCommon);
        Button btnBack = findViewById(R.id.btnBack);
        tieude = findViewById(R.id.TieuDeChamDiem);
        tieude.setText("Báo cáo");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.containsKey("fullname")) {
                fullName = bundle.getString("fullname");
            }

            if (bundle.containsKey("user_id")) {
                user_id = bundle.getInt("user_id");
            }
        }

        btnOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaoCaoActivity.this, DownloadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnOffice.getText().toString());
                bundle.putInt("user_id", user_id);
                bundle.putInt("area_id", 1);
                bundle.putString("fullname", fullName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnLectureHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaoCaoActivity.this, DownloadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnLectureHall.getText().toString());
                bundle.putInt("area_id", 2);
                bundle.putInt("user_id", user_id);
                bundle.putString("fullname", fullName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaoCaoActivity.this, DownloadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnLab.getText().toString());
                bundle.putInt("area_id", 3);
                bundle.putInt("user_id", user_id);
                bundle.putString("fullname", fullName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaoCaoActivity.this, DownloadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnCommon.getText().toString());
                bundle.putInt("area_id", 4);
                bundle.putInt("user_id", user_id);
                bundle.putString("fullname", fullName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaoCaoActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fullname", fullName);
                bundle.putInt("user_id", user_id);
                bundle.putString("fullname", fullName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.containsKey("fullname")) {
                fullName = bundle.getString("fullname");
            }

            if (bundle.containsKey("user_id")) {
                user_id = bundle.getInt("user_id");
            }
        }
    }
}
