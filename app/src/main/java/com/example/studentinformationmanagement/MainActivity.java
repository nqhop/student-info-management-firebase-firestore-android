package com.example.studentinformationmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.studentinformationmanagement.activity.FormActivity;
import com.example.studentinformationmanagement.activity.StudentManagementActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
//         i.putExtra("id", "Nusob5B4cLZaxP1Ju6tJ");
//         startActivity(i);

    }


}