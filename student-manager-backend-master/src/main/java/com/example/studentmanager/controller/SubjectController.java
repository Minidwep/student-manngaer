package com.example.studentmanager.controller;

import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Subject;
import com.example.studentmanager.model.StudentParam;
import com.example.studentmanager.model.SubjectParam;
import com.example.studentmanager.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;
    private final Integer NoBorder = 1000000;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Subject> list() {
        return this.subjectService.findAll();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<Subject> findAll(int page, int pageSize) {

        page = page < 0 ? 0 : page;
        pageSize = pageSize < 1 ? 1 : pageSize;

        return subjectService.findAll(page, pageSize);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json")
    public void createSubject(@RequestBody @Validated SubjectParam subjectParam) throws ParseException {
        if(subjectParam.getId() < NoBorder){
            subjectParam.setId(null);
            subjectService.updateSubject(subjectParam.convertToSubject());
        }else{
            subjectParam.setId(null);
            subjectService.updateDirection(subjectParam.convertToDirection());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteSubject(@NotNull Integer id) throws ParseException {
        if(id < NoBorder){
            subjectService.deleteSubject(id);
        }else{
            subjectService.deleteDirection(id);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    public void updateSubject(@RequestBody @Validated SubjectParam subjectParam) throws Exception {
        if(subjectParam.getId() == null){
            throw new Exception("Id不能为空");
        }
        if(subjectParam.getId() < NoBorder){
            subjectService.updateSubject(subjectParam.convertToSubject());
        }else{
            subjectService.updateDirection(subjectParam.convertToDirection());
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Page<Subject> search(String key, int page, int pageSize) {
        return subjectService.search(key, page, pageSize);
    }
}