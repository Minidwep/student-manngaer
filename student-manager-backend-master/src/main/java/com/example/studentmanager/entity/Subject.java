package com.example.studentmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="SUBJECT")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String status;
    // 专业负责人
    @OneToOne
    @JsonIgnore
    @JoinColumn(name="director_no")
    @NotFound(action= NotFoundAction.IGNORE)
    private Staff director;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    //@NotFound(action=NotFoundAction.IGNORE)
    @JsonManagedReference
    private List<Direction> directions = new ArrayList<Direction>();

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

    public Staff getDirector() {
        return director;
    }

    public void setDirector(Staff director) {
        this.director = director;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }
    @Transient
    public String getDirectorName() {
        return this.director == null ? null : this.director.getName();
    }

    @Transient
    public String getDirectorNo() {
        return this.director == null ? null : this.director.getStaffNo();
    }

}
