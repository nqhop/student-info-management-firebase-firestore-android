package com.example.studentinformationmanagement.storage;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.studentinformationmanagement.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCSVWriter {

    private DatabaseReference databaseReference;
    private Context context;

    public MyCSVWriter(Context context) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        this.context = context;
    }


    public void exportDataToCSV(List<Student> students) {

        String csvFilePath = context.getExternalFilesDir(null) + "/" + String.valueOf(System.currentTimeMillis()) + ".csv";
        String csvFilePath2 = context.getExternalFilesDir(null) + "/" + String.valueOf(System.currentTimeMillis());

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




//        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath2))) {
//            // Read data from Firebase Database
//            databaseReference.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    // Process the retrieved data
//
//                    // Retrieve the data from the first child
//                    DataSnapshot firstChildSnapshot = dataSnapshot.getChildren().iterator().next();
//                    List<String> headerRecord = (List<String>) getKeyAndValue(firstChildSnapshot.getValue().toString()).get("key");
//                    String[] headerRecordArr = headerRecord.toArray(new String[0]);
//
//                    Log.d("test", Arrays.toString(headerRecordArr));
//
//
//                    writer.writeNext(headerRecordArr);
//                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                        List<String> row = (List<String>) getKeyAndValue(firstChildSnapshot.getValue().toString()).get("value");
//
//                        Log.d("exportDataToCSV", "childSnapshot.getKey() " + childSnapshot.getKey());
//                        Log.d("exportDataToCSV", "childSnapshot.getValue() " + childSnapshot.getValue());
//                        // Get the data values
//                        String value1 = childSnapshot.child("value1").getValue(String.class);
//                        String value2 = childSnapshot.child("value2").getValue(String.class);
//
//                        // Create a CSV record
//                        String[] record = {childSnapshot.getKey().toString(), childSnapshot.getValue().toString()};
//
//                        // Write the record to the CSV file
//                        writer.writeNext(row.toArray(new String[0]));
//                    }
//                    Log.d("exportDataToCSV", "exportDataToCSV successful");
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Handle the error
//                }
//            });
//        } catch (IOException e) {
//            Log.d("exportDataToCSV","IOException 2 " + e.toString());
//            e.printStackTrace();
//        }
    }
    private Map getKeyAndValue(Object object){
        String keyAndValue = object.toString();
        keyAndValue = keyAndValue.substring(1, keyAndValue.length() - 1);
//        Log.d("getKeyAndValue", keyAndValue);

        List<String> key = new ArrayList<>();
        List<String> value = new ArrayList<>();
        ;
        for (String item : keyAndValue.split(", ")) {
//            Log.d("getKeyAndValue", item);
            key.add(item.substring(0, item.indexOf("=")));
            value.add(item.substring(item.indexOf("=") + 1, item.length()));
//            Log.d("getKeyAndValue", item.substring(0, item.indexOf("=")));
//            Log.d("getKeyAndValue", item.substring(item.indexOf("=") + 1, item.length()));
        }

        Map<String, Object> multiValues = new HashMap<String, Object>();
        multiValues.put("key", key);
        multiValues.put("value", value);
        return multiValues;
    }
}
