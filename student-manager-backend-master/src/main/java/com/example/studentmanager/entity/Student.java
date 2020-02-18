package com.example.studentmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="STUDENT")
public class Student implements Serializable {
    @Id
    @Column(name = "student_no")
    private String studentNo;
    @OneToOne(fetch=FetchType.EAGER)
    @MapsId("studentNo")
    @JoinColumn(name="student_no", unique = true)
    @JsonIgnore
    private User user;
    private String name;
    private String telephone;
    private String gender;
    private Date dob;
    @ManyToOne
    @JoinColumn(name="class_id")
    private StudentClass studentClass;

    public Student() {

    }

    public Student (User user, String name, String telephone, String gender, Date dob) {
        this.user = user;
        this.studentNo = user.getUsername();
        this.name = name;
        this.telephone = telephone;
        this.gender = gender;
        this.dob = dob;
    }

    public User getUser() {
        return user;
    }

    public String getStudentNo() {
        return this.user.getUsername();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public StudentClass getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClass studentClass) {
        this.studentClass = studentClass;
    }

    @Transient
    public String getEmail() {
        return this.user.getEmail();
    }
}
