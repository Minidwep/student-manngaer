package com.example.studentmanager.service;

import com.example.studentmanager.entity.StudentClass;
import com.example.studentmanager.repository.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentClassService {
    private final StudentClassRepository studentClassRepository;

    @Autowired
    public StudentClassService(StudentClassRepository studentClassRepository) {
        this.studentClassRepository = studentClassRepository;
    }

    public List<StudentClass> findAll() {
        return studentClassRepository.findAll();
    }
}
