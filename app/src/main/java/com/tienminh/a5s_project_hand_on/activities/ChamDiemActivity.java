package com.tienminh.a5s_project_hand_on.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienminh.a5s_project_hand_on.R;
import com.tienminh.a5s_project_hand_on.classes.Room;
import com.tienminh.a5s_project_hand_on.classes.Score;
import com.tienminh.a5s_project_hand_on.database.DatabaseCallback;
import com.tienminh.a5s_project_hand_on.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChamDiemActivity extends AppCompatActivity {
    TextView nameOfRoom;
    Spinner spinnerFloor, spinnerTrashCan, spinnerAshtray, spinnerWall, spinnerWindow, spinnerCeiling, spinnerLight, spinnerCorridor;
    Button btnSave, btnBack;
    Integer room_id, user_id, area_id;
    String room_name;
    String fullName = "";
    String title = "";
    String nameOfRoomText = "";
    Boolean check = false;
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
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            nameOfRoomText = bundle.getString("nameOfRoom");
            nameOfRoom.setText(nameOfRoomText);
            if (bundle.containsKey("user_id")) {
                user_id = bundle.getInt("user_id");
            }
            if (bundle.containsKey("room_name")) {
                room_name = bundle.getString("room_name");
            }
            if (bundle.containsKey("area_id")) {
                area_id = bundle.getInt("area_id");
            }
            if (bundle.containsKey("fullname")) {
                fullName = bundle.getString("fullname");
            }
            if (bundle.containsKey("title")) {
                title = bundle.getString("title");
            }
        }
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                new DatabaseHelper.ExecuteGetRoomID<Integer>(new DatabaseCallback<Integer>() {
                    @Override
                    public void onTaskComplete(Integer result) {
                        room_id = result;
                    }
                }, ChamDiemActivity.this, new Room(room_name, area_id)).execute();

                new DatabaseHelper.ExecuteCheckMarkRoom<Boolean>(new DatabaseCallback<Boolean>() {
                    @Override
                    public void onTaskComplete(Boolean result) {
                        if (result != null && result) {
                            check = result;
                        }
                    }
                }, ChamDiemActivity.this, room_id).execute();
            }
        });
        service.shutdown();

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
                        }, ChamDiemActivity.this, scores, check).execute();
                    }
                });
                service.shutdown();

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1;
                switch (area_id){
                    case 1:
                        intent1 = new Intent(ChamDiemActivity.this, VanPhongActivity.class);
                        break;
                    case 2:
                        intent1 = new Intent(ChamDiemActivity.this, GiangDuongActivity.class);
                        break;
                    case 3:
                        intent1 = new Intent(ChamDiemActivity.this, LabActivity.class);
                        break;
                    default:
                        intent1 = new Intent(ChamDiemActivity.this, KhuVucChungActivity.class);
                        break;
                }
                Bundle bundle1 = new Bundle();
                bundle1.putInt("user_id", user_id);
                bundle1.putString("fullname", fullName);
                bundle1.putInt("area_id", area_id);
                bundle1.putString("title", title);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            nameOfRoomText = bundle.getString("nameOfRoom");
            nameOfRoom.setText(nameOfRoomText);
            if (bundle.containsKey("user_id")) {
                user_id = bundle.getInt("user_id");
            }
            if (bundle.containsKey("room_name")) {
                room_name = bundle.getString("room_name");
            }
            if (bundle.containsKey("area_id")) {
                area_id = bundle.getInt("area_id");
            }
            if (bundle.containsKey("fullname")) {
                fullName = bundle.getString("fullname");
            }
            if (bundle.containsKey("title")) {
                title = bundle.getString("title");
            }
        }

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                new DatabaseHelper.ExecuteGetRoomID<Integer>(new DatabaseCallback<Integer>() {
                    @Override
                    public void onTaskComplete(Integer result) {
                        room_id = result;
                    }
                }, ChamDiemActivity.this, new Room(room_name, area_id)).execute();
            }
        });
    }
}
