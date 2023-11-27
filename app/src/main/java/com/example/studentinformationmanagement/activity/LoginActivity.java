package com.example.studentinformationmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentinformationmanagement.MainActivity;
import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.activity.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText editTextLoginEmail, editTextLoginPassword;
    private Button btnLogin, btnLoginWithGoogle;
    private TextView textViewForgotPassword, btnGoToSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginWithGoogle = findViewById(R.id.btnLoginWithGoogle);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        btnGoToSignup = findViewById(R.id.btnGoToSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(
                        editTextLoginEmail.getText().toString(),
                        editTextLoginPassword.getText().toString()
                );
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi nhấn vào "Quên mật khẩu"
                // Thêm logic phục hồi mật khẩu ở đây
            }
        });

        btnLoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi nhấn vào "Đăng nhập bằng Google"
                // Thêm logic đăng nhập bằng Google ở đây
            }
        });

        btnGoToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến màn hình đăng ký khi nhấn vào "Don't have an account? Sign Up"
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish(); // Đóng màn hình đăng nhập
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Đăng nhập thành công
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Chuyển đến màn hình chính hoặc màn hình sau đăng nhập thành công
                             Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                             startActivity(intent);
                            showSnackbar("Welcome, " + user.getEmail());
                            finish(); // Đóng màn hình đăng nhập
                        }
                    } else {
                        // Đăng nhập thất bại
                        showSnackbar("Authentication failed. Please check your email and password.");
                    }
                });
    }

    // Helper method to show a Snackbar message
    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}

