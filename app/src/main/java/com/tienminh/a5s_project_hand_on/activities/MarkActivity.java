package com.tienminh.a5s_project_hand_on.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tienminh.a5s_project_hand_on.R;


public class MarkActivity extends AppCompatActivity {
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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            user_id = bundle.getInt("user_id");
            Log.d("TEST_@@", Integer.toString(user_id));
        }

        btnOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarkActivity.this, VanPhongActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnOffice.getText().toString());
                bundle.putInt("area_id", new Integer(1));
                bundle.putInt("user_id", user_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnLectureHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarkActivity.this, GiangDuongActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnLectureHall.getText().toString());
                bundle.putInt("area_id", new Integer(2));
                bundle.putInt("user_id", user_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarkActivity.this, LabActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnLab.getText().toString());
                bundle.putInt("area_id", new Integer(3));
                bundle.putInt("user_id", user_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarkActivity.this, KhuVucChungActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", btnCommon.getText().toString());
                bundle.putInt("area_id", new Integer(4));
                bundle.putInt("user_id", user_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarkActivity.this, MainActivity.class);
                Bundle bundle1 = new Bundle();
                startActivity(intent);
            }
        });
    }
}
