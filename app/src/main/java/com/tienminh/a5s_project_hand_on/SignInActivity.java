package com.tienminh.a5s_project_hand_on;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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

                if(username.equals("admin") && password.equals("admin")) {
                    // Mở AdminSignUpActivity
                    startActivity(new Intent(SignInActivity.this, AdminSignUpActivity.class));
                    finish();
                }
                else {
                    // Kiểm tra đăng nhập với csdl
                    DatabaseHelper db = new DatabaseHelper(SignInActivity.this);

                    if(!db.checkMATKHAU(username, password)){
                        Toast.makeText(SignInActivity.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignInActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        // Mở MainActivity
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }
        });
    }
}
