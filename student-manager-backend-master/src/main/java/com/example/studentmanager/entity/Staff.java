package com.example.studentmanager.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="STAFF")
public class Staff {
    @Id

    @Column(name = "staff_no")
    private String staffNo;
    @OneToOne
    @JoinColumn(name="staff_no", unique = true)
    private User user;
    private String name;
    private String telephone;
    private String gender;
    private Date dob;
    @Column(name = "academic_rank")
    private String academicRank;

    public Staff() {

    }

    public Staff(User user, String name, String telephone, String gender, Date dob, String academicRank) {
        this.user = user;
        this.staffNo = user.getUsername();
        this.name = name;
        this.telephone = telephone;
        this.gender = gender;
        this.dob = dob;
        this.academicRank = academicRank;
    }

    public String getStaffNo() {
        return this.staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Staff{" +
                "staffNo='" + staffNo + '\'' +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", gender='" + gender + '\'' +
                ", dob=" + dob +
                ", academicRank='" + academicRank + '\'' +
                '}';
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAcademicRank() {
        return academicRank;
    }

    public void setAcademicRank(String academicRank) {
        this.academicRank = academicRank;
    }
    @Transient
    public String getEmail() {
        return this.user.getEmail();
    }
}
