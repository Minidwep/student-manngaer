package com.example.studentmanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name="STUDENT_CLASS")
public class StudentClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "enroll_year")
    private String enrollYear;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private Subject subject;
    @ManyToOne
    @JoinColumn(name = "direction_id")
    @NotFound(action=NotFoundAction.IGNORE)
    private Direction direction;

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

    public String getEnrollYear() {
        return enrollYear;
    }

    public void setEnrollYear(String enrollYear) {
        this.enrollYear = enrollYear;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @JsonBackReference
    public Direction getDirection() {
        return direction;
    }

    @JsonBackReference
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
