package com.example.studentinformationmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.adapter.CourseCustomAdapter;
import com.example.studentinformationmanagement.adapter.StudentAdapter;
import com.example.studentinformationmanagement.databinding.ActivityStudentManagementBinding;
import com.example.studentinformationmanagement.databinding.FilterPopupBinding;
import com.example.studentinformationmanagement.model.Course;
import com.example.studentinformationmanagement.model.Student;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class StudentManagementActivity extends AppCompatActivity {

    private ActivityStudentManagementBinding activityStudentManagementBinding;
    private FilterPopupBinding filterPopupBinding;
    private ArrayList<String> courses = new ArrayList<>();
    private List<String> filterCourses = new ArrayList<>();
    private List<Student> filteredData = new ArrayList<>();
    private Set<Integer> studentVisitable = new HashSet<>();
    private StudentAdapter studentAdapter;
    Button btnfilter;

    public StudentManagementActivity() {
    }
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_management);
        btnfilter = (Button) findViewById(R.id.btnfilter);

        activityStudentManagementBinding = DataBindingUtil.setContentView(this, R.layout.activity_student_management);
//        filterPopupBinding = DataBindingUtil.setContentView(this, R.layout.filter_popup);
//        ArrayList<Student> itemList = new ArrayList<>(
//                Arrays.asList(new Student("Bao", "7 District", "01/01/2001", "192.168.1.11", "Java", "01"),
//                        new Student("Tinh", "4 District", "01/01/2001", "192.168.1.12", "Java", "02")
//                )
//        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityStudentManagementBinding.studentRecyclerView.setLayoutManager(layoutManager);


//        studentAdapter = new StudentAdapter(itemList);
//        activityStudentManagementBinding.studentRecyclerView.setAdapter(studentAdapter);

        // handle filter
        getCourse();
//        searchhelper();
        popUpAction();
//        getStudentsWithFilter(null);
    }


    private void popUpAction() {
        CourseCustomAdapter adapter;
        adapter = new CourseCustomAdapter(this, courses);

        activityStudentManagementBinding.btnfilter.setOnClickListener(view -> {
            final DialogPlus dialogPlus = DialogPlus.newDialog(this)
                    .setContentHolder(new ViewHolder(R.layout.filter_popup))
                    .setExpanded(true)
                    .setGravity(Gravity.TOP)
                    .setAdapter(adapter)
//                    .setOnItemClickListener(new OnItemClickListener() {
//                        @Override
//                        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
//                            // Handle list item clicks
//                            String selectedItem = courses.get(position);
//                            Toast.makeText(StudentManagementActivity.this, "Selected from DialogPlus: " + selectedItem, Toast.LENGTH_SHORT).show();
//                            Log.d("course", "Selected");
//                            dialog.dismiss();
//                        }
//                    })
//                    .setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(DialogPlus dialog, View view) {
//                            view.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    filterCourses.clear();
//                                    adapter.getSelectedItems().stream().forEach(x -> filterCourses.add(courses.get(x)));
//                                    getStudentsWithFilter(filterCourses);
//                                    Toast.makeText(StudentManagementActivity.this, "Updated", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    })
                    .create();

            View dialogContentView = dialogPlus.getHolderView();
            dialogContentView.findViewById(R.id.btnCancel).setOnClickListener(v -> {
                dialogPlus.dismiss();
            });
            dialogContentView.findViewById(R.id.btnUpdate).setOnClickListener(v -> {
                filterCourses.clear();
                adapter.getSelectedItems().stream().forEach(x -> filterCourses.add(courses.get(x)));
                getStudentsWithFilter(filterCourses);
                Toast.makeText(StudentManagementActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                dialogPlus.dismiss();
            });

            // Get ListView from the dialog layout
            ListView listView = (ListView) dialogPlus.getHolderView().findViewById(R.id.courseList);

            // Set adapter and item click listener
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = courses.get(position);
                    String toastText = adapter.checkInSetSelected(position) ? "Unselected" : "Selected";

                    Toast.makeText(StudentManagementActivity.this, toastText + ": " + selectedItem, Toast.LENGTH_SHORT).show();
                    adapter.toggleItemSelection(position);
//                    dialogPlus.dismiss();
                }
            });
            dialogPlus.show();
        });
    }

    private void getCourse() {
        CompletableFuture<QuerySnapshot> future = new CompletableFuture<>();
        collectionReference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot querySnapshot = task.getResult();
                future.complete(querySnapshot);
            }
            try {
                QuerySnapshot querySnapshot = future.get();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    Log.d("getCourse", "getCollection size" + querySnapshot.size());

                    for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                        Map<String, Object> data = documentSnapshot.getData();
                        Log.d("getCourse", "name " + data.get("name"));
                        // search for name, mail and course
                        String documentData = (String) documentSnapshot.getData().get("course");
                        if(!courses.contains(documentData)){
                            courses.add(documentData);
                        }
                    }
                }
                Log.d("getCourse", String.valueOf(courses.size()));

                // init list student
                List<String> filterValues = new ArrayList<>();
                courses.forEach(x -> filterValues.add(x));

                getStudentsWithFilter(filterValues);

            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
//    private void searchhelper() {
//        collectionReference.get().addOnCompleteListener(task -> {
//            if(task.isSuccessful()){
//                QuerySnapshot querySnapshot = task.getResult();
//                if(querySnapshot != null && !querySnapshot)
//            }
//        })
//    }

    private void getStudentsWithFilter(List<String> filterValues){

        if(filterValues.size() == 0){
            courses.forEach(x -> filterValues.add(x));
        }

        // Create a query
        Query query = collectionReference.whereIn("course", filterValues);
        query.get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                filteredData.clear();
                int count = 0;
                studentVisitable.clear();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    // all student are visible initially
                    studentVisitable.add(count++);
                    Map<String, Object> data = document.getData();
                    filteredData.add(document.toObject(Student.class));
//                    Student(String name, String address, String dayOfBirth, String classroom, String course, String id)
//                    filteredData.add(new Student((String) data.get("name"), (String) data.get("address"), (String) data.get("dayOfBirth"), (String) data.get("classroom"), (String) data.get("course"), (String) data.get("id")));
                    Log.d("getStudentsWithFilter", document.getData().get("course").toString());
                    Log.d("getStudentsWithFilter", String.valueOf(filteredData.size()));
                }
                // Create and set the adapter for the RecyclerView

                studentAdapter = new StudentAdapter(filteredData);
                studentAdapter.setStudentVisitable(studentVisitable);
                activityStudentManagementBinding.studentRecyclerView.setAdapter(studentAdapter);

            })
            .addOnFailureListener(e -> {
                // Handle query failure
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                txtSearch(newText);
                return false;
            }
        });
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
    private void txtSearch(String str){
        Log.d("txtSearch", str);
        Log.d("txtSearch", String.valueOf(filteredData.size()));
        studentVisitable.remove(0);
        studentAdapter.setStudentVisitable(studentVisitable);
        studentAdapter.notifyItemRemoved(0);
        studentVisitable.remove(2);
        studentAdapter.notifyItemRemoved(2);

//        Query searchQuery = collectionReference.whereArrayContains("Students", str);
//        FirebaseRecyclerOptions<Student> options =
//                new FirebaseRecyclerOptions.Builder<Student>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Students").orderByChild("name").startAt(str).endAt(str+"~"), Student.class)
//                        .build();

//        studentAdapter = new StudentAdapter(options.getSnapshots());
//        activityStudentManagementBinding.studentRecyclerView.setAdapter(studentAdapter);

//        mainAdapter = new MainAdapter(options);
//        mainAdapter.startListening();
//        recyclerView.setAdapter(mainAdapter);
    }
}
