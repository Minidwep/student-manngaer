package com.example.studentmanager.repository;

import com.example.studentmanager.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Query("select s from Subject s where s.name like %?1%")
    Page<Subject> search(String key, Pageable pageable);
}
