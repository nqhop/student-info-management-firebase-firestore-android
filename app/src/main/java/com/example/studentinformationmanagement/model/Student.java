package com.example.studentinformationmanagement.model;

import android.util.Log;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.studentinformationmanagement.BR;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Student extends BaseObservable {
    String name;
    String id;
    String email;
    String address;
    String dayOfBirth;
    String classroom;
    String course;
    List<Certificate> certificateList;
    public Student (){
    }

    public Student(String name, String address, String dayOfBirth,
                   String classroom, String course, List<Certificate> certificates, String id) {
        this.name = name;
        this.address = address;
        this.dayOfBirth = dayOfBirth;
        this.classroom = classroom;
        this.course = course;
        this.certificateList = certificates;
        this.id = id;
        this.email = this.id + "@student.edu.vn";
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.email = this.id + "@student.edu.vn";
        notifyPropertyChanged(BR.id);
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    @Bindable
    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
        notifyPropertyChanged(BR.dayOfBirth);
    }
    @Bindable
    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
        notifyPropertyChanged(BR.classroom);
    }
    @Bindable
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<Certificate> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<Certificate> certificateList) {
        this.certificateList = certificateList;
    }

    public boolean isValid() {
        return isStringNotEmpty(name)
                && isStringNotEmpty(id)
                && isStringNotEmpty(email)
                && isStringNotEmpty(address)
                && isStringNotEmpty(dayOfBirth)
                && isValidDateFormat(dayOfBirth)
                && isStringNotEmpty(classroom)
                && isStringNotEmpty(course);
    }

    private boolean isStringNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isValidDateFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        sdf.setLenient(false);

        try {
            Date parsedDate = sdf.parse(date);
            return parsedDate != null;
        } catch (ParseException e) {
            return false;
        }
    }
}
