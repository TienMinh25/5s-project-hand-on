package com.tienminh.a5s_project_hand_on.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tienminh.a5s_project_hand_on.R;

public class BaoCaoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_actv);

        Button btnOffice = findViewById(R.id.btnOffice);
        Button btnLectureHall = findViewById(R.id.btnLectureHall);
        Button btnLab = findViewById(R.id.btnLab);
        Button btnCommon = findViewById(R.id.btnCommon);
        Button btnBack = findViewById(R.id.btnBack);

        btnOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaoCaoActivity.this, VanPhongActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnOffice.getText().toString());
                bundle.putInt("area_id", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnLectureHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaoCaoActivity.this, GiangDuongActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnLectureHall.getText().toString());
                bundle.putInt("area_id", 2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaoCaoActivity.this, LabActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnLab.getText().toString());
                bundle.putInt("area_id", 3);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaoCaoActivity.this, KhuVucChungActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnCommon.getText().toString());
                bundle.putInt("area_id", 4);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaoCaoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
