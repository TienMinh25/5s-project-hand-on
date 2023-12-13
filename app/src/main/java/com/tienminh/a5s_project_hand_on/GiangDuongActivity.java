package com.tienminh.a5s_project_hand_on;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienminh.a5s_project_hand_on.adapter.MyAdapterRecycler;
import com.tienminh.a5s_project_hand_on.classEvents.RecyclerTouchListener;
import com.tienminh.a5s_project_hand_on.classes.Room;
import com.tienminh.a5s_project_hand_on.database.DatabaseCallbackArray;
import com.tienminh.a5s_project_hand_on.database.DatabaseHelper;

import java.util.ArrayList;

public class GiangDuongActivity extends AppCompatActivity {
    TextView txtView;
    Integer area_id;
    Button btnAdd, btnBack;
    RecyclerView recyclerView;
    ArrayList<Room> data;
    MyAdapterRecycler adapter;
    Room new_room;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_actv);

        txtView = findViewById(R.id.TieuDeChamDiem);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        recyclerView = findViewById(R.id.recyclerViewRooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            // Lấy Bundle từ Intent
            Bundle bundle = intent.getExtras();

            // Kiểm tra xem "title" có tồn tại trong Bundle không
            if (bundle.containsKey("title")) {
                // Lấy giá trị của "title" từ Bundle
                String title = bundle.getString("title");
                txtView.setText(title);
            }
            if (bundle.containsKey("area_id")) {
                area_id = bundle.getInt("area_id");
            }
        }

        data = getData(new Room(area_id)); // Hàm này trả về danh sách dữ liệu của bạn
        adapter = new MyAdapterRecycler(data, R.layout.item_layout);
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo một đối tượng AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(GiangDuongActivity.this);
                builder.setTitle("Thêm Room");

                // Tạo layout của dialog
                LinearLayout layout = new LinearLayout(GiangDuongActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                // Tạo EditText để nhập tên Room
                final EditText editTextName = new EditText(GiangDuongActivity.this);
                editTextName.setHint("Nhập tên Room");
                layout.addView(editTextName);

                // Thiết lập layout cho dialog
                builder.setView(layout);
                // Thiết lập nút "Thêm"
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Lấy tên từ EditText
                        String roomName = editTextName.getText().toString();
                        new_room = new Room(roomName, area_id);
                        // Thêm Room vào ArrayList<Room> data
                        data.add(new_room); // Lưu ý: Bạn cần cung cấp areaId theo logic của ứng dụng
                        adapter.notifyDataSetChanged();
                        new DatabaseHelper.ExecuteAddRoom<String>(new DatabaseCallbackArray<String>() {
                            @Override
                            public void onTaskComplete(String result) {
                                if (result != null) {
                                    Toast.makeText(GiangDuongActivity.this, "Thêm phòng thành công",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(GiangDuongActivity.this, "Thêm phòng thất bại",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, GiangDuongActivity.this, new_room).execute();
                        // Đóng dialog
                        dialogInterface.dismiss();

                    }
                });

                // Thiết lập nút "Hủy"
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Đóng dialog
                        new_room = new Room("", area_id);
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GiangDuongActivity.this, MarkActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(), "onClick phần tử " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private ArrayList<Room> getData(Room room){
        ArrayList<Room> new_result = new ArrayList<>();

        new DatabaseHelper.ExecuteGetRooms<ArrayList<Room>>(new DatabaseCallbackArray<ArrayList<Room>>() {
            @Override
            public void onTaskComplete(ArrayList<Room> result) {
                for (Room i:result) {
                    new_result.add(i);
                }
            }
        }, this, room).execute();
        return new_result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        data = getData(new Room(area_id));
        adapter.notifyDataSetChanged();
    }
}
