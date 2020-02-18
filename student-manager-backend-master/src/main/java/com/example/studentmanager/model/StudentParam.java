package com.example.studentmanager.model;

import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.StudentClass;
import com.example.studentmanager.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对应从客户端接受的Student类JSON，并使用注解进行数据验证
 */
public class StudentParam {
    @Pattern(regexp = "^\\d{10,12}$", message = "无效学号")
    private String studentNo;
    @NotBlank(message = "姓名不能为空")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "无效邮箱") //参考
    private String email;
    private String telephone;
    private String gender;
    @Pattern(regexp = "^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "无效出生日期")
    private String dob;

    private String password;
    @NotNull(message = "班级不能为空")
    private Integer studentClassId;

    /**
     * 转换为实体类
     * @return
     * @throws ParseException
     */
    public Student convertTo() throws ParseException {
        User user = new User(this.studentNo, this.password, this.email, true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDob = sdf.parse(this.dob);
        Student student = new Student(user, this.name, this.telephone, this.gender, dateDob);
        StudentClass studentClass = new StudentClass();
        studentClass.setId(this.studentClassId);
        student.setStudentClass(studentClass);
        return student;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
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

    public Integer getStudentClassId() {
        return studentClassId;
    }

    public void setStudentClassId(Integer studentClassId) {
        this.studentClassId = studentClassId;
    }
}
