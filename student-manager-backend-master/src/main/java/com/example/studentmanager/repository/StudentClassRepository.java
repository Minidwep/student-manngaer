package com.example.studentmanager.repository;

import com.example.studentmanager.entity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentClassRepository extends JpaRepository<StudentClass, Integer> {

    @Query(nativeQuery=true, value="select distinct c.enroll_year from STUDENT_CLASS c")
    List<String> findAllEnrollYears();
}
