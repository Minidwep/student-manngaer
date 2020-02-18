package com.example.studentmanager.repository;

import com.example.studentmanager.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("from Student s where s.studentNo like %?1% or s.name like %?1%")
    Page<Student> search(String key, Pageable pageable);
}
