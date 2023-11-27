package com.example.studentinformationmanagement.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentinformationmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = "HistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Lấy UID của người dùng đang đăng nhập
        String userId = "replace_with_actual_user_id";

        showLoginHistory(userId);
    }

    private void showLoginHistory(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference loginHistoryRef = db.collection("loginHistory").document(userId).collection("events");

        loginHistoryRef.orderBy("timestamp")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Xử lý mỗi bản ghi lịch sử đăng nhập ở đây
                                Date timestamp = document.getDate("timestamp");
                                Log.d(TAG, "Login event at: " + timestamp);
                            }
                        } else {
                            Log.w(TAG, "Error getting login history", task.getException());
                        }
                    }
                });
    }
}
