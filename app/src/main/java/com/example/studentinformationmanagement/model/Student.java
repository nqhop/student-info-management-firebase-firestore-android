package com.example.studentinformationmanagement.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.studentinformationmanagement.BR;

import java.util.List;

public class Student extends BaseObservable {
    String name;
    String address;
    String dayOfBirth;
    String classroom;
    String course;
    String id;
    String mail;
    public Student (){
    }

    public Student(String name, String address, String dayOfBirth, String classroom, String course, String id) {
        this.name = name;
        this.address = address;
        this.dayOfBirth = dayOfBirth;
        this.classroom = classroom;
        this.course = course;
        this.id = id;
        this.mail = id + "@gmail.com";
    }
    List<Certificate> certificateList;

    public Student(String name, String address, String dayOfBirth,
                   String classroom, String course, List<Certificate> certificates) {
        this.name = name;
        this.address = address;
        this.dayOfBirth = dayOfBirth;
        this.classroom = classroom;
        this.course = course;
        this.id = id;
        this.mail = id + "@gmail.com";
        this.certificateList = certificates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Certificate> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<Certificate> certificateList) {
        this.certificateList = certificateList;
    }
}
