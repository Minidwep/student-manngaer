package com.example.studentmanager.controller;

import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.User;
import com.example.studentmanager.model.StudentParam;
import com.example.studentmanager.repository.StudentRepository;
import com.example.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json")
    public void createStudent(@RequestBody @Validated StudentParam studentParam) throws ParseException {
        System.out.println("sinfo = "+studentParam.convertTo().toString());
        studentService.add(studentParam.convertTo());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<Student> findAll(int page, int pageSize) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        page = page < 0 ? 0 : page;
        pageSize = pageSize < 1 ? 1 : pageSize;
        return studentService.findAll(page, pageSize);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteStudent(String studentNo) throws ParseException {
        studentService.delete(studentNo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    public void updateStudent(@RequestBody @Validated StudentParam studentParam) throws ParseException {
        studentService.update(studentParam.convertTo());
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Page<Student> search(String key, int page, int pageSize) {
        page = page < 0 ? 0 : page;
        pageSize = pageSize < 1 ? 1 : pageSize;
        return studentService.search(key, page, pageSize);
    }
}