package com.example.studentmanager.model;

import com.example.studentmanager.entity.Staff;
import com.example.studentmanager.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StaffParam {
    @NotBlank(message = "工号不能为空")
    private String staffNo;
    @NotBlank(message = "姓名不能为空")
    private String name;
    private String email;
    private String telephone;
    private String gender;
    @Pattern(regexp = "^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "无效出生日期")
    private String dob;

    private String password;
    private String academicRank;

    public Staff convertTo() throws ParseException {
        User user = new User(this.staffNo, this.password, this.email, true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDob = sdf.parse(this.dob);
        return new Staff(user, name, telephone, gender, dateDob, academicRank);
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAcademicRank() {
        return academicRank;
    }

    public void setAcademicRank(String academicRank) {
        this.academicRank = academicRank;
    }
}
