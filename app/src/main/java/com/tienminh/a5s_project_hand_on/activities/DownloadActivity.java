package com.tienminh.a5s_project_hand_on.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienminh.a5s_project_hand_on.R;
import com.tienminh.a5s_project_hand_on.adapter.MyAdapterRecycler;
import com.tienminh.a5s_project_hand_on.classEvents.RecyclerTouchListener;
import com.tienminh.a5s_project_hand_on.classes.GenExcel;
import com.tienminh.a5s_project_hand_on.classes.Room;
import com.tienminh.a5s_project_hand_on.database.DatabaseCallback;
import com.tienminh.a5s_project_hand_on.database.DatabaseCallbackArray;
import com.tienminh.a5s_project_hand_on.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadActivity extends AppCompatActivity {
    TextView txtView;
    Integer area_id, user_id;
    Button btnBack;
    RecyclerView recyclerView;
    ArrayList<Room> data = new ArrayList<>();
    MyAdapterRecycler adapter;
    String fullName = "";
    String title = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_actv);

        txtView = findViewById(R.id.TieuDeChamDiem);
        btnBack = findViewById(R.id.btnBack);

        recyclerView = findViewById(R.id.recyclerViewRooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapterRecycler(data, R.layout.item_layout);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            // Lấy Bundle từ Intent
            Bundle bundle = intent.getExtras();

            // Kiểm tra xem "title" có tồn tại trong Bundle không
            if (bundle.containsKey("title")) {
                // Lấy giá trị của "title" từ Bundle
                title = bundle.getString("title");
                txtView.setText(title);
            }
            if (bundle.containsKey("area_id")) {
                area_id = bundle.getInt("area_id");
            }
            if (bundle.containsKey("fullname")) {
                fullName = bundle.getString("fullname");
            }
            if (bundle.containsKey("user_id")) {
                user_id = bundle.getInt("user_id");
            }
        }

        getData(new Room(area_id));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DownloadActivity.this, BaoCaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", user_id);
                bundle.putString("fullname", fullName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // phan goi db sinh file csv
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {

                        new DatabaseHelper.ExecuteGetInformationForGenCSV<ArrayList<Integer>>(new DatabaseCallback<ArrayList<Integer>>() {
                            @Override
                            public void onTaskComplete(ArrayList<Integer> result) {
                                if (result != null) {
                                    GenExcel.generateExcel(result, data.get(position).getName(), title);
                                    Toast.makeText(DownloadActivity.this, "Download successful", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(DownloadActivity.this, "Download failure", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, DownloadActivity.this, new Room(data.get(position).getName(), area_id)).execute();
                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void getData(Room room){
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                new DatabaseHelper.ExecuteGetRooms<ArrayList<Room>>(new DatabaseCallbackArray<ArrayList<Room>>() {
                    @Override
                    public void onTaskComplete(ArrayList<Room> result) {
                        data.clear();
                        for (Room i:result) {
                            data.add(i);
                        }
                    }
                }, DownloadActivity.this, room).execute();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        service.shutdown();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(new Room(area_id));
        adapter.notifyDataSetChanged();
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            // Lấy Bundle từ Intent
            Bundle bundle = intent.getExtras();

            // Kiểm tra xem "title" có tồn tại trong Bundle không
            if (bundle.containsKey("title")) {
                // Lấy giá trị của "title" từ Bundle
                title = bundle.getString("title");
                txtView.setText(title);
            }
            if (bundle.containsKey("area_id")) {
                area_id = bundle.getInt("area_id");
            }
            if (bundle.containsKey("fullname")) {
                fullName = bundle.getString("fullname");
            }
            if (bundle.containsKey("user_id")) {
                user_id = bundle.getInt("user_id");
            }
        }
    }

}
