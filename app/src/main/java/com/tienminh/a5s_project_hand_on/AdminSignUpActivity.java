package com.tienminh.a5s_project_hand_on;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienminh.a5s_project_hand_on.database.DatabaseHelper;

public class AdminSignUpActivity extends AppCompatActivity {

    EditText edtFullName, edtPosition, edtUsername, edtPassword, edtRetypePassword, edtEmail, edtPhone;
    Button btnRegister;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_signup_actv);

        // tao ket noi o day

        edtFullName = findViewById(R.id.full_name);
        edtPosition = findViewById(R.id.position);
        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        edtRetypePassword = findViewById(R.id.retype_password);
        edtEmail = findViewById(R.id.email);
        edtPhone = findViewById(R.id.phone);

        btnRegister = findViewById(R.id.register_btn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Lấy dữ liệu
                String fullName = edtFullName.getText().toString();
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String repassword = edtRetypePassword.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();

                // Validate dữ liệu
                if(!validateInput(fullName, username, password, repassword, email, phone)) {
                    return;
                }

                // Kiểm tra tên đăng nhập
//                if (!DatabaseHelper.checkExistUser(username)) {
//                    Toast.makeText(AdminSignUpActivity.this, "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Thêm dữ liệu vào database
//                if (DatabaseHelper.insertData(fullName, email, password, username, phone)) {
//                    Toast.makeText(AdminSignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
//                    // Clear text trong các edittext
//                    edtFullName.setText("");
//                    edtPosition.setText("");
//                    edtUsername.setText("");
//                    edtPassword.setText("");
//                    edtRetypePassword.setText("");
//                    edtEmail.setText("");
//                    edtPhone.setText("");
//                }
//                else {
//                    Toast.makeText(AdminSignUpActivity.this, "Thêm dữ liệu thất bại!", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        //Xử lý nút logout
        btnLogout = findViewById(R.id.button_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xóa saved session nếu có
                Intent intent = new Intent(AdminSignUpActivity.this, SignInActivity.class); // Tạo intent để mở SignInActivity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa hết các activity trước đó
                startActivity(intent);
            }
        });
    }

    boolean validateInput(String fullName, String username, String password, String repassword, String email, String phone) {

        if(fullName.isEmpty()) {
            edtFullName.setError("Vui lòng nhập họ tên");
            return false;
        }

        if(username.isEmpty()) {
            edtUsername.setError("Vui lòng nhập tên đăng nhập");
            return false;
        }

        if(password.isEmpty()) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            return false;
        }

        if(repassword.isEmpty()) {
            edtRetypePassword.setError("Vui lòng nhập lại mật khẩu");
            return false;
        }

        if(email.isEmpty()) {
            edtEmail.setError("Vui lòng nhập email");
            return false;
        }

        if(phone.isEmpty()) {
            edtPhone.setError("Vui lòng nhập số điện thoại");
            return false;
        }

        if(!password.equals(repassword)) {
            edtRetypePassword.setError("Mật khẩu không khớp");
            return false;
        }

        return true;
    }

}
