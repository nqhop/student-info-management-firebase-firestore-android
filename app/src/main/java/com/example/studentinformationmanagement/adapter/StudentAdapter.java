package com.example.studentinformationmanagement.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.activity.FormActivity;
import com.example.studentinformationmanagement.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    private List<Student> students;
    private List<Student> studentsInitial = new ArrayList<>();
    private List<Student> studentsFilter = new ArrayList<>();
    private List<String> studentsMergeTextForSearch;
    private Set<Integer> studentVisitable = new HashSet<>();
    private List<Integer> studentHiden = new ArrayList<>();

    public Set<Integer> getStudentVisitable(List<String> studentIDs) {
        Set<Integer> sVisitable = new HashSet<>();
        for (int i = 0; i < studentIDs.size(); i++){
            for (int j = 0; j < studentsInitial.size(); j++){
                if (studentsInitial.get(j).getId().equals(studentIDs.get(i))){
                    sVisitable.add(j);
                    continue;
                }
            }
        }
        return sVisitable;
    }

    public void setStudentVisitable(Set<Integer> studentVisitable) {
        Log.d("studentHiden", "studentVisitable input: " +  studentVisitable.toString());
        this.students = new ArrayList<>(studentsInitial);
        this.studentsFilter = new ArrayList<>(studentsInitial);
        this.studentHiden.clear();

        this.studentVisitable.forEach(index -> {
            if (!studentVisitable.contains(index)){
                studentHiden.add(index);
            }
        });
        Log.d("studentHiden", studentHiden.toString());

        Collections.sort(studentHiden, Comparator.reverseOrder());
        for (int position : studentHiden){
            students.remove(position);
            studentsFilter.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void updateStudentVisitable(){
        studentVisitable.remove(2);
        studentVisitable.remove(1);
        notifyItemRemoved(2);
        notifyItemRemoved(1);
    }

    public void filter(List<Student> filteredData){

    }

    public void search(String str){

        Log.d("search", "String: " + str);
//        for(int i = 0; i < studentsMergeTextForSearch.size(); i++){
//            Log.d("search", "studentsMergeTextForSearch: " + studentsMergeTextForSearch.get(i));
//        }



        List<Integer> sVisitable = new ArrayList<>();

        for(int i = 0; i < studentsMergeTextForSearch.size(); i++){
//            Log.d("search", "studentsMergeTextForSearch: " + studentsMergeTextForSearch.get(i));
//            Log.d("search", "studentsMergeTextForSearch: " + studentsMergeTextForSearch.get(i).contains(str));
            if(studentsMergeTextForSearch.get(i).contains(str)){
                sVisitable.add(i);
            }
        }
        Log.d("search", "sVisitable " + sVisitable.toString());
        List<Integer> newStudentVisitable = FilterAndSearch(sVisitable);
        Collections.sort(newStudentVisitable, Comparator.reverseOrder());
        Log.d("search", "newStudentVisitable " + newStudentVisitable);

        Set<Integer> studentsFilterShow = new HashSet<>();
        Set<Integer> studentsFilterHiden = new HashSet<>();

        for(int i = 0; i < studentsFilter.size(); i++){
            if(!newStudentVisitable.contains(i)){
                studentsFilterHiden.add(i);
            };
        }

        Log.d("search", "studentHiden " + studentHiden.toString());

        Log.d("search", "studentsFilter size " + studentsFilter.size());


        this.students = new ArrayList<>(studentsFilter);
        notifyDataSetChanged();
        for (int position : studentsFilterHiden){
            try {
                students.remove(position);
            }catch (Exception e){}

            notifyItemRemoved(position);
        }






//        for (int position : studentHiden){
//            students.remove(position);
//            notifyItemRemoved(position);
//        }
    }

    public List<Integer> FilterAndSearch(List<Integer> sVisitable){
        Log.d("FilterAndSearch", "FilterAndSearch------------");
        List<Integer> sVisit = new ArrayList<>();
        for(int i = 0; i < studentsFilter.size(); i++){
            Log.d("FilterAndSearch", "i -> " + studentsFilter.get(i).getId());
            for(int j = 0; j < sVisitable.size(); j++){
                Log.d("FilterAndSearch", "J -> " + studentsInitial.get(sVisitable.get(j)).getId());
                if(studentsInitial.get(sVisitable.get(j)).getId().equals(studentsFilter.get(i).getId())){
                    sVisit.add(i);
                    continue;
                }
            }
        }
        return  sVisit;
    }
    public void clearSelections() {
        studentVisitable.clear();
        notifyDataSetChanged();
    }

    public StudentAdapter(List<Student> students) {
        this.students = students;
        studentsInitial.addAll(students);
        studentsFilter.addAll(students);
        studentsMergeTextForSearch = new ArrayList<>();
        this.students.forEach(student -> {
//            String mergeTextForSearch = student.getName().toLowerCase() + "#" + student.getMail().toLowerCase() + "#" + student.getCourse().toLowerCase();
            String mergeTextForSearch = student.getName().toLowerCase();
            studentsMergeTextForSearch.add(mergeTextForSearch);
        });
        for (int i = 0; i< students.size(); i++)
            studentVisitable.add(i);
    }

    public boolean matchWithSearchText(String str){
        return true;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        return new StudentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentHolder holder, int position) {
        boolean isVisible = this.studentVisitable.contains(position);
        Log.d("isVisible", String.valueOf(isVisible));
        holder.itemView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        Student student = students.get(position);
        holder.studentNameText.setText(student.getName());
        holder.studentMailText.setText(student.getMail());
        holder.studentCourseText.setText(student.getCourse());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentHolder extends RecyclerView.ViewHolder{
        TextView studentNameText, studentMailText, studentCourseText;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);

            studentNameText = itemView.findViewById(R.id.studentNameText);
            studentMailText = itemView.findViewById(R.id.studentMailText);
            studentCourseText = itemView.findViewById(R.id.studentCourseText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), FormActivity.class);
                    i.putExtra("id", "");
                    v.getContext().startActivity(i);

                }
            });
        }
    }
}
