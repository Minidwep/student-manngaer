package com.example.studentmanager.repository;

import com.example.studentmanager.entity.Staff;
import com.example.studentmanager.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<Staff, String> {
    @Query("select s from Staff s where s.staffNo like %?1% or s.name like %?1%")
    Page<Staff> search(String key, Pageable pageable);
}