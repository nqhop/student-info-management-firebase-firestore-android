package com.example.studentinformationmanagement.storage;

import android.content.Context;
import android.util.Log;

import com.example.studentinformationmanagement.model.Certificate;
import com.example.studentinformationmanagement.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MyCSVWriter {

    private DatabaseReference databaseReference;
    private Context context;

    public MyCSVWriter(Context context) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        this.context = context;
    }


    public void exportDataToCSV(List<Student> students) {
//        String.valueOf(System.currentTimeMillis())
        String csvFilePath = context.getExternalFilesDir(null) + "/" + "students" + ".csv";

        Log.d("CSV", csvFilePath);
        Log.d("CSV", "students size " + students.size());
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {

            String[] headerRecord = { "id", "name", "address", "classroom", "email", "course", "dayOfBirth"};
            writer.writeNext(headerRecord);

            students.forEach(s -> {
                writer.writeNext(new String[] {s.getId(), s.getName(), s.getAddress(), s.getClassroom(), s.getEmail(), s.getCourse(), s.getDayOfBirth()});
            });

            Log.d("CSV", "exportDataToCSV successful");
        } catch (IOException e) {
            Log.d("CSV", "IOException");
            throw new RuntimeException(e);
        }
    }

    public void exportCetificateToCSV(List<Certificate> certificateList, String id) {
        String csvFilePath = context.getExternalFilesDir(null) + "/Cetificate_" + id + ".csv";
        Log.d("exportCetificateToCSV", csvFilePath);
        Log.d("exportCetificateToCSV", "students size " + certificateList.size());

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {

            String[] headerRecord = { "id", "title", "description", "date"};
            writer.writeNext(headerRecord);

            certificateList.forEach(c -> {
                writer.writeNext(new String[] {c.getId(), c.getTitle(), c.getDescription(), c.getDate()});
            });

            Log.d("exportCetificateToCSV", "exportDataToCSV successful");
        } catch (IOException e) {
            Log.d("exportCetificateToCSV", "IOException");
            throw new RuntimeException(e);
        }
    }
}
