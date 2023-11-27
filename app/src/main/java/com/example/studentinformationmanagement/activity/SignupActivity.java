package com.example.studentinformationmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentinformationmanagement.MainActivity;
import com.example.studentinformationmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private EditText editTextEmail, editTextPassword, editTextName, editTextAge;
    private Button btnSignup;
    private ImageButton btnBackToHome; // Added ImageButton for back-to-home

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        btnSignup = findViewById(R.id.btnSignup);
        btnBackToHome = findViewById(R.id.btnBackToHome); // Initialize ImageButton

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser(
                        editTextEmail.getText().toString(),
                        editTextPassword.getText().toString(),
                        editTextName.getText().toString(),
                        Integer.parseInt(editTextAge.getText().toString())
                );
            }
        });

        // Set click event for the back-to-home button
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the back-to-home action here
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the signup screen
            }
        });
    }

    private void signUpUser(String email, String password, String name, int age) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng ký thành công, lưu thông tin vào Firestore
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                saveUserInfoToFirestore(user.getUid(), email, name, age);
                            }
                        } else {
                            // Đăng ký thất bại
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserInfoToFirestore(String userId, String email, String name, int age) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("name", name);
        user.put("age", age);

        db.collection("users")
                .document(userId)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User information saved to Firestore");
                            Toast.makeText(SignupActivity.this, "Sign up successful!",
                                    Toast.LENGTH_SHORT).show();
                            // Thực hiện chuyển đến màn hình chính hoặc màn hình đăng nhập
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Đóng màn hình đăng ký
                        } else {
                            Log.w(TAG, "Error saving user information to Firestore", task.getException());
                            Toast.makeText(SignupActivity.this, "Error saving user information.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
