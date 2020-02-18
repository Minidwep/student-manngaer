package com.example.studentmanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "DIRECTION")
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    //@NotFound(action= NotFoundAction.IGNORE)
    private Subject subject;


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

    @JsonBackReference
    public Subject getSubject() {
        return subject;
    }

    @JsonBackReference
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Transient
    public Integer getSubjectId() {
        return this.subject.getId();
    }
}