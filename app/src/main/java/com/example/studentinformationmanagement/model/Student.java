package com.example.studentinformationmanagement.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.studentinformationmanagement.BR;

public class Student extends BaseObservable {
    String name;
    String address;
    String dayOfBirth;
    String classroom;
    String course;
    public Student (){
    }

    public Student(String name, String address, String dayOfBirth, String classroom, String course) {
        this.name = name;
        this.address = address;
        this.dayOfBirth = dayOfBirth;
        this.classroom = classroom;
        this.course = course;
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
        notifyPropertyChanged(BR.course);
    }
}
