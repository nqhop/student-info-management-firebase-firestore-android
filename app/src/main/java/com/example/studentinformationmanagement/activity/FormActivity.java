package com.example.studentinformationmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.adapter.CertificateAdapter;
import com.example.studentinformationmanagement.adapter.ItemClickListener;
import com.example.studentinformationmanagement.databinding.ActivityFormBinding;
import com.example.studentinformationmanagement.dialog.AddWarningDialog;
import com.example.studentinformationmanagement.dialog.CertificateDialog;
import com.example.studentinformationmanagement.dialog.CertificateDialogListener;
import com.example.studentinformationmanagement.dialog.DeleteWarningDialog;
import com.example.studentinformationmanagement.model.Certificate;
import com.example.studentinformationmanagement.model.Student;
import com.example.studentinformationmanagement.storage.MyCSVWriter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormActivity extends AppCompatActivity implements CertificateDialogListener, ItemClickListener {
    private ActivityFormBinding activityFormBinding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Students");
    private CertificateAdapter certificateAdapter;
    private String currentId;
    CertificateDialog dialogFragment;

    List<Certificate> certificates;
    Student formStudent;
    Boolean addNew = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        formStudent = new Student();
        activityFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_form);

        activityFormBinding.addBtn.setOnClickListener(v -> {
            if(!formStudent.isValid()) {
                AddWarningDialog.showAddDialog(this, "Item Name", (dialog, which) -> {
                });
                return;
            }
            formStudent.setCertificateList(certificates);
            addStudent(formStudent);
            Toast.makeText(this,  "Complete", Toast.LENGTH_SHORT).show();
        });

        activityFormBinding.deleteBtn.setOnClickListener(v -> {
            DeleteWarningDialog.showDeleteDialog(this, "Item Name", (dialog, which) -> {
                deleteStudent();
            });
        });

        activityFormBinding.updateButton.setOnClickListener(v -> {
            updateStudent();
        });


       CertificateDialog dialogFragment = new CertificateDialog(this, null);

        activityFormBinding.addCert.setOnClickListener(v -> {
            dialogFragment.show(getSupportFragmentManager(), "addDialog");
        });


        certificates = new ArrayList<>();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        assert id != null;
        if(id.isEmpty()) {
            activityFormBinding.setStudent(formStudent);
            currentId = null;
            activityFormBinding.deleteBtn.setVisibility(View.INVISIBLE);
            addNew = true;
        } else {
            getStudent(id);
            currentId = id;
            activityFormBinding.addBtn.setVisibility(View.GONE);
            activityFormBinding.updateButton.setVisibility(View.VISIBLE);
            addNew = false;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityFormBinding.recyclerView.setLayoutManager(layoutManager);

        certificateAdapter = new CertificateAdapter(certificates, this);
        activityFormBinding.recyclerView.setAdapter(certificateAdapter);
        certificateAdapter.setClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void updateStudent() {
        Map<String, Object> updatedData = new HashMap<>();

        // Assuming your Student class has getters for its properties
        updatedData.put("name", formStudent.getName());
        updatedData.put("address", formStudent.getAddress());
        updatedData.put("dayOfBirth", formStudent.getDayOfBirth());
        updatedData.put("classroom", formStudent.getClassroom());
        updatedData.put("course", formStudent.getCourse());
        updatedData.put("certificateList", formStudent.getCertificateList());

        collectionReference.document(currentId).update(updatedData)
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                   } else {

                   }
                });
    }


    public void deleteStudent() {
        collectionReference.document(currentId).delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(this, StudentManagementActivity.class);
                        startActivity(i);
                    } else {
                        // Handle errors
                    }
                });
    }

    public void addStudent(Student student) {
        collectionReference.add(student)
                .addOnSuccessListener(documentReference -> {
                    // Data added successfully
                    Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                    Intent i = new Intent(this, StudentManagementActivity.class);
                    startActivity(i);
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
                            formStudent = document.toObject(Student.class);
                            activityFormBinding.setStudent(formStudent);

                            if(!(formStudent.getCertificateList() == null)) {
                                for(Certificate certificate : formStudent.getCertificateList()){
                                    certificates.add(certificate);
                                    certificateAdapter.notifyDataSetChanged();
                                }
                            }

                        } else {
                            currentId = null;
                            activityFormBinding.deleteBtn.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Log.w("Test", "Error getting document", task.getException());
                    }
                });
    }

    @Override
    public void onCertificateAdded(Certificate certificate) {
        if(!certificate.isValid()){
            AddWarningDialog.showAddDialog(this, "Item Name", (dialog, which) -> {
            });
        } else {
            certificates.add(certificate);
            formStudent.setCertificateList(certificates);
            certificateAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCertificateUpdated(Certificate certificate, int position) {
        certificates.get(position).setTitle(certificate.getTitle());
        certificates.get(position).setId(certificate.getId());
        certificates.get(position).setDescription(certificate.getDescription());
        certificates.get(position).setDate(certificate.getDate());
        formStudent.setCertificateList(certificates);
        certificateAdapter.notifyItemChanged(position);
    }

    @Override
    public void onCertificateDeleted(int position) {
        certificates.remove(position);
        formStudent.setCertificateList(certificates);
        certificateAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v, int pos) {
        CertificateDialog dialogFragment = new CertificateDialog(this, certificates.get(pos), pos);
        dialogFragment.show(getSupportFragmentManager(), "updateDialog");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(addNew) return true;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.addStudent).setVisible(false);
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.loginHistory).setVisible(false);
        menu.findItem(R.id.myExport).setTitle("Export certificate");
        menu.findItem(R.id.myImport).setTitle("Import certificate");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.myExport){
            MyCSVWriter myCSVWriter = new MyCSVWriter(this);
            myCSVWriter.exportCetificateToCSV(formStudent.getCertificateList(), formStudent.getId());;
            Toast.makeText(this, "Exported", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.myImport) {
            Intent i = new Intent(this, importFromStorage.class);
            i.putExtra("studentsID", formStudent.getId());
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}