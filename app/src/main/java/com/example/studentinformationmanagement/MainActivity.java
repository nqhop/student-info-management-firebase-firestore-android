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

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MyDemo();
        requestStoragePermission();

        Intent i = new Intent(this, FormActivity.class);
        i.putExtra("id", "");
//        startActivity(i);

        Intent studentManagement = new Intent(this, StudentManagementActivity.class);
        startActivity(studentManagement);
//         i.putExtra("id", "Nusob5B4cLZaxP1Ju6tJ");
//         startActivity(i);

    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("MyDemo","requestStoragePermission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                    REQUEST_CODE_STORAGE_PERMISSION);
        } else {
            // Permission already granted
            // Proceed with file operations
            Log.d("MyDemo","Permission already granted");
        }


//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            Log.d("MyDemo","requestStoragePermission");
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUEST_CODE_STORAGE_PERMISSION);
//        } else {
//            // Permission already granted
//            // Proceed with file operations
//
//            Log.d("MyDemo","Permission already granted");
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // Proceed with file operations
                Log.d("MyDemo", "Permission granted");
            } else {
                // Permission denied
                // Handle accordingly (e.g., show a message to the user)
                Log.d("MyDemo", "Permission denied");
            }
        } else {
            Log.d("MyDemo", "requestCode not equal REQUEST_CODE_STORAGE_PERMISSION");
        }
    }


    public void MyDemo(){

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Log.d("MyDemo","From MainActivity Permission OK");
        }else {
            Log.d("MyDemo", "From MainActivity Permission not OK");
        }




        // Get the app-specific directory in external storage
        File directory = getExternalFilesDir(null);
        Log.d("MyDemo","From MainActivity " + directory.toString());

        if (directory != null) {
            // Create a file object within the app-specific directory
            File file = new File(directory, "example.txt");

            try {
                // Create a FileWriter object to write the file
                FileWriter writer = new FileWriter(file);

                // Write data to the file
                writer.write("Hello, world!");

                // Close the FileWriter
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}