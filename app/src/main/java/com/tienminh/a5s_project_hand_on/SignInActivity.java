package com.tienminh.a5s_project_hand_on;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienminh.a5s_project_hand_on.database.DatabaseCallback;
import com.tienminh.a5s_project_hand_on.database.DatabaseHelper;

public class SignInActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_actv);

        edtUsername = findViewById(R.id.input_username);
        edtPassword = findViewById(R.id.input_password);

        btnLogin = findViewById(R.id.button_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                Object[] info_users = new Object[] {username, password};
                // check admin
                String sql_check_admin = "SELECT * FROM users WHERE username = ? AND password = ? AND is_admin = 1";
                new DatabaseHelper.ExecuteQueryCheckAdmin<String>(new DatabaseCallback<String>() {
                    @Override
                    public void onTaskComplete(String result) {
                        if (result != null) {
                            Log.d("END", "cnt");
                            startActivity(new Intent(SignInActivity.this, AdminSignUpActivity.class));
                            finish();
                        }
                        else {
                            // Kiểm tra đăng nhập với csdl
                            String sql_check_user = "SELECT * FROM users WHERE username = ? AND password = ? AND is_admin = 0";

                            new DatabaseHelper.ExecuteQueryTaskCheckUserSignIn<String>(new DatabaseCallback<String>() {
                                @Override
                                public void onTaskComplete(String result) {
                                    if (result != null) {
                                        Toast.makeText(SignInActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("fullname", result);
                                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                        intent.putExtras(bundle);
                                        // Mở MainActivity

                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(SignInActivity.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, sql_check_user, info_users).execute();
                        }
                    }
                }, sql_check_admin, info_users).execute();
            }
        });
    }
}
