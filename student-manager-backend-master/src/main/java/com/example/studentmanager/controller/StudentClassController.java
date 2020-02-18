package com.example.studentmanager.controller;

import com.example.studentmanager.entity.StudentClass;
import com.example.studentmanager.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/class")
public class StudentClassController {
    private final StudentClassService studentClassService;

    @Autowired
    public StudentClassController(StudentClassService studentClassService) {
        this.studentClassService = studentClassService;
    }
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public List<StudentClass> findAll() {
        return studentClassService.findAll();
    }
}
