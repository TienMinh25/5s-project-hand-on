package com.tienminh.a5s_project_hand_on.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienminh.a5s_project_hand_on.R;
import com.tienminh.a5s_project_hand_on.classes.User;
import com.tienminh.a5s_project_hand_on.database.DatabaseCallback;
import com.tienminh.a5s_project_hand_on.database.DatabaseHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignInActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_actv);

        // Tạo đối tượng DatabaseHelper và gọi onCreate để tạo bảng và thêm dữ liệu ban đầu
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        databaseHelper.onCreate(db);

        edtUsername = findViewById(R.id.input_username);
        edtPassword = findViewById(R.id.input_password);

        btnLogin = findViewById(R.id.button_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                User userCheck = new User(username, password, 0);
                User adminCheck = new User(username, password, 1);

                // Kiểm tra đăng nhập với quyền admin
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        new DatabaseHelper.ExecuteQueryCheckAdmin<Boolean>(new DatabaseCallback<Boolean>() {
                            @Override
                            public void onTaskComplete(Boolean result) {
                                if (result != null && result) {
                                    startActivity(new Intent(SignInActivity.this, AdminSignUpActivity.class));
                                    finish();
                                } else {
                                    // Kiểm tra đăng nhập với quyền user

                                    new DatabaseHelper.ExecuteQueryTaskCheckUserSignIn<User>(new DatabaseCallback<User>() {
                                        @Override
                                        public void onTaskComplete(User result) {
                                            if (result != null) {
                                                Toast.makeText(SignInActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("fullname", result.getFullname());
                                                bundle.putInt("user_id", result.getId());
                                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(SignInActivity.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }, SignInActivity.this, userCheck).execute();
                                }
                            }
                        }, SignInActivity.this, adminCheck).execute();
                    }
                });

            }
        });
    }
}
