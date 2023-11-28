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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    private List<Student> students;
    private Set<Integer> studentVisitable = new HashSet<>();


    public Set<Integer> getStudentVisitable() {
        return studentVisitable;
    }

    public void setStudentVisitable(Set<Integer> studentVisitable) {
        this.studentVisitable = studentVisitable;
    }

    public StudentAdapter(List<Student> students) {
        this.students = students;
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
