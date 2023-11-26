package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.studentinformationmanagement.activity.FormActivity;
import com.example.studentinformationmanagement.activity.StudentManagementActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this, FormActivity.class);
        i.putExtra("id", "");
//        startActivity(i);

        Intent studentManagement = new Intent(this, StudentManagementActivity.class);
        startActivity(studentManagement);
    }
}