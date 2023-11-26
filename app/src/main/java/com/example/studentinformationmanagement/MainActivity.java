package com.example.studentinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.studentinformationmanagement.activity.FormActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this, FormActivity.class);
        i.putExtra("id", "Nusob5B4cLZaxP1Ju6tJ");
        startActivity(i);
    }
}