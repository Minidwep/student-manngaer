package com.example.studentmanager.model;

import com.example.studentmanager.entity.Direction;
import com.example.studentmanager.entity.Staff;
import com.example.studentmanager.entity.Subject;

import javax.validation.constraints.NotNull;
import java.text.ParseException;

/**
 * 对应专业和方向JSON对象，并进行数据验证
 */
public class SubjectParam {

    private Integer id;

    @NotNull(message = "名称不能为空")
    private String name;

    @NotNull(message = "状态不能为空")
    private String status;

    private Integer subjectId;

    private String directorNo;

    public Subject convertToSubject() throws ParseException{
        Subject subject = new Subject();
        subject.setId(this.id);
        subject.setName(this.name);
        subject.setStatus(this.status);

        Staff staff = new Staff();
        staff.setStaffNo(this.directorNo);
        subject.setDirector(staff);
        return subject;
    }

    public Direction convertToDirection() throws ParseException{
        Direction direction = new Direction();
        direction.setId(this.id);
        direction.setName(this.name);
        direction.setStatus(this.status);

        Subject subject = new Subject();
        subject.setId(this.subjectId);
        direction.setSubject(subject);
        return direction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getDirectorNo() {
        return directorNo;
    }

    public void setDirectorNo(String directorNo) {
        this.directorNo = directorNo;
    }
}
