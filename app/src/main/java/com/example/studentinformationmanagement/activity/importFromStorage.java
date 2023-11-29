package com.example.studentinformationmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.model.Certificate;
import com.example.studentinformationmanagement.model.Student;
import com.example.studentinformationmanagement.storage.MyCSVWriter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class importFromStorage extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Students");
    String csvFilePath;
    List<String> fileName;
    List<String> filePath;
    ListView l;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_from_storage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        l = findViewById(R.id.importListView);
        title = findViewById(R.id.textView4);
        fileName = new ArrayList<>();
        filePath = new ArrayList<>();
        Intent intent = getIntent();
        String studentsID = intent.getStringExtra("studentsID");
        Log.d("importFromStorage", "addListStudent " + studentsID);

        assert studentsID != null;
        if(studentsID.isEmpty()){
            title.setText("Import students");
            csvFilePath = (MyCSVWriter.getStudentsPath() == null ? "/storage/emulated/0/Android/data/com.example.studentinformationmanagement/files/students" : MyCSVWriter.getStudentsPath());
        }else {
            title.setText("Import cetificates for student");
            csvFilePath = (MyCSVWriter.getStudentsPath() == null ? "/storage/emulated/0/Android/data/com.example.studentinformationmanagement/files/cetificates" : MyCSVWriter.getCertificatePath());
        }
        Log.d("importFromStorage", csvFilePath);

        File directory = new File(csvFilePath);
        File[] files = directory.listFiles();


        if (files != null) {
            for (File file : files) {
                Log.d("importFromStorage", "file path " + file.getPath());
                fileName.add(file.getName());
                filePath.add(file.getPath());

                if (file.isFile() && file.getName().endsWith(".csv")) {
                    // CSV file found, process it with OpenCSV
                    String fileName = file.getName();
                    String filePath = file.getAbsolutePath();

                    try (CSVReader reader = new CSVReader(new FileReader(file))) {
                        String[] nextLine;
                        while ((nextLine = reader.readNext()) != null) {
                            Log.d("importFromStorage", Arrays.toString(nextLine));
                        }
                    } catch (IOException | CsvValidationException e) {
                    }
                }
            }

            ArrayAdapter<String> arr;
            arr
                    = new ArrayAdapter<String>(
                    this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    fileName);
            l.setAdapter(arr);
            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    importStudent(position);
                    Toast.makeText(importFromStorage.this, fileName.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void importStudent(int position) {

        File file = new File(filePath.get(position));
        Log.d("importStudent", filePath.get(position));

        if (file.isFile() && file.getName().endsWith(".csv")) {
            // CSV file found, process it with OpenCSV

//            [id, name, address, classroom, email, course, dayOfBirth]

//            (String name, String address, String dayOfBirth,
//                    String classroom, String course, List< Certificate > certificates, String id)

            try (CSVReader reader = new CSVReader(new FileReader(file))) {
                String[] nextLine;
                boolean isFirstLine = true;
                while ((nextLine = reader.readNext()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    Student student = new Student(nextLine[1], nextLine[2], nextLine[6], nextLine[3], nextLine[5], null, nextLine[0]);
                    Log.d("importStudent","student " + student.getName());
                    collectionReference.add(student).addOnFailureListener(d -> {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                finish();
            } catch (IOException | CsvValidationException e) {
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}