package com.example.studentinformationmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.adapter.CertificateAdapter;
import com.example.studentinformationmanagement.databinding.ActivityFormBinding;
import com.example.studentinformationmanagement.model.Certificate;
import com.example.studentinformationmanagement.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class FormActivity extends AppCompatActivity {
    private ActivityFormBinding activityFormBinding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Students");
    private CertificateAdapter certificateAdapter;
    Student formStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        formStudent = new Student();
        activityFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_form);

        activityFormBinding.addBtn.setOnClickListener(v -> {
            addStudent(formStudent);
            Toast.makeText(this,  "Complete", Toast.LENGTH_SHORT).show();
        });


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        assert id != null;
        if(id.isEmpty()) {
            activityFormBinding.setStudent(formStudent);
        } else {
            getStudent(id);
        }

//        Integer id, String title, String description, String date

        ArrayList<Certificate> itemList = new ArrayList<>(
                Arrays.asList(new Certificate("01", "Title 01", "Desc 01", "01/01/2001"),
                        new Certificate("02", "Title 02", "Desc 02", "02/01/2001")
                        )
        );


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityFormBinding.recyclerView.setLayoutManager(layoutManager);

        certificateAdapter = new CertificateAdapter(itemList);
        activityFormBinding.recyclerView.setAdapter(certificateAdapter);
    }

    public void addStudent(Student student) {
        collectionReference.add(student)
                .addOnSuccessListener(documentReference -> {
                    // Data added successfully
                    Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                    Log.w("Firestore", "Error adding document", e);
                });
    }


    public void getStudent(String collectionId) {
        collectionReference
                .document(collectionId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Convert the document to your data model
                            Student data = document.toObject(Student.class);
                            Log.d("Test", "Document data: " + data.toString());
                            activityFormBinding.setStudent(data);
                        } else {
                            Log.d("Test", "No such document");
                        }
                    } else {
                        Log.w("Test", "Error getting document", task.getException());
                    }
                });
    }
}