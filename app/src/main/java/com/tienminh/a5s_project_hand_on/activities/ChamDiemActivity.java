package com.tienminh.a5s_project_hand_on.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienminh.a5s_project_hand_on.R;
import com.tienminh.a5s_project_hand_on.classes.Score;
import com.tienminh.a5s_project_hand_on.database.DatabaseCallback;
import com.tienminh.a5s_project_hand_on.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChamDiemActivity extends AppCompatActivity {
    TextView nameOfRoom;
    Spinner spinnerFloor, spinnerTrashCan, spinnerAshtray, spinnerWall, spinnerWindow, spinnerCeiling, spinnerLight, spinnerCorridor;
    Button btnSave;
    Integer room_id, user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cham_diem_phong);

        nameOfRoom = findViewById(R.id.room);
        spinnerFloor = findViewById(R.id.spinnerFloor);
        spinnerTrashCan = findViewById(R.id.spinnerTrashCan);
        spinnerAshtray = findViewById(R.id.spinnerAshtray);
        spinnerWall = findViewById(R.id.spinnerWall);
        spinnerWindow = findViewById(R.id.spinnerWindow);
        spinnerCeiling = findViewById(R.id.spinnerCeiling);
        spinnerLight = findViewById(R.id.spinnerLight);
        spinnerCorridor = findViewById(R.id.spinnerCorridor);

        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String title = bundle.getString("nameOfRoom");
            nameOfRoom.setText(title);
            user_id = bundle.getInt("user_id");
            room_id = bundle.getInt("room_id");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Score> scores = new ArrayList<>();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Integer diemNenSan = Integer.parseInt(spinnerFloor.getSelectedItem().toString());
                                Integer diemThungRac = Integer.parseInt(spinnerTrashCan.getSelectedItem().toString());
                                Integer diemGatTan = Integer.parseInt(spinnerAshtray.getSelectedItem().toString());
                                Integer diemTuong = Integer.parseInt(spinnerWall.getSelectedItem().toString());
                                Integer diemCuaSo = Integer.parseInt(spinnerWindow.getSelectedItem().toString());
                                Integer diemTran = Integer.parseInt(spinnerCeiling.getSelectedItem().toString());
                                Integer diemDen = Integer.parseInt(spinnerLight.getSelectedItem().toString());
                                Integer diemHanhLang = Integer.parseInt(spinnerCorridor.getSelectedItem().toString());
                                ArrayList<Integer> diems = new ArrayList<>();
                                diems.add(diemNenSan); diems.add(diemThungRac); diems.add(diemGatTan); diems.add(diemTuong);
                                diems.add(diemCuaSo); diems.add(diemTran); diems.add(diemDen); diems.add(diemHanhLang);


                                for (int i = 0; i < diems.size(); i++) {
                                    Score new_score = new Score(room_id, user_id, i+1, diems.get(i));
                                    scores.add(new_score);
                                }
                            }
                        });

                        new DatabaseHelper.ExecuteMark<Boolean>(new DatabaseCallback<Boolean>() {
                            @Override
                            public void onTaskComplete(Boolean result) {
                                if (result != null && result) {
                                    Toast.makeText(ChamDiemActivity.this, "Chấm điểm thành công",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(ChamDiemActivity.this, "Chấm điểm không thành công, vui lòng thử lại",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, ChamDiemActivity.this, scores).execute();
                    }
                });
            }
        });
    }
}
