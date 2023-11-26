package com.example.studentinformationmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.adapter.StudentAdapter;
import com.example.studentinformationmanagement.databinding.ActivityStudentManagementBinding;
import com.example.studentinformationmanagement.model.Student;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentManagementActivity extends AppCompatActivity {

    private ActivityStudentManagementBinding activityStudentManagementBinding;
    private StudentAdapter studentAdapter;

    public StudentManagementActivity() {
    }
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private CollectionReference collectionReference = db.collection("Students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_management);
//        getSupportActionBar().show();

        activityStudentManagementBinding = DataBindingUtil.setContentView(this, R.layout.activity_student_management);
        ArrayList<Student> itemList = new ArrayList<>(
                Arrays.asList(new Student("Bao", "7 District", "01/01/2001", "192.168.1.11", "Java", "01", "01@gmail.com"),
                        new Student("Tinh", "4 District", "01/01/2001", "192.168.1.12", "Java", "02", "02@gmail.com")
                )
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityStudentManagementBinding.studentRecyclerView.setLayoutManager(layoutManager);
        studentAdapter = new StudentAdapter(itemList);
        activityStudentManagementBinding.studentRecyclerView.setAdapter(studentAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addStudent){
            Intent addStudent = new Intent(this, FormActivity.class);
//            addStudent.putExtra("id", "/BgtsaIFxMyJkcKLjM8LP");
            addStudent.putExtra("id", "");
            startActivity(addStudent);
        } else if (item.getItemId() == R.id.myImport) {
            Toast.makeText(this, "Import", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.myExport) {
            Toast.makeText(this, "Export", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
