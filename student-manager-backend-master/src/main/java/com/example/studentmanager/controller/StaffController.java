package com.example.studentmanager.controller;

import com.example.studentmanager.entity.Staff;
import com.example.studentmanager.model.StaffParam;
import com.example.studentmanager.service.StaffService;
import com.example.studentmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;
//    @PreAuthorize("hasRole('STAFF')") //有ROLE_ADMIN权限的用户可以访问
//    @RequestMapping(method = RequestMethod.GET)
//    public List<Staff> getStaffs() {
//        return staffRepository.findAll();
//    }

    //@PreAuthorize("hasRole('STAFF')") //有ROLE_STAFF权限的用户可以访问
    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json")
    public void createStaff(@RequestBody @Validated StaffParam staffParam) throws ParseException {
        System.out.println("info="+staffParam.convertTo().toString());
        System.out.println(staffParam.convertTo().getUser().toString());
        staffService.add(staffParam.convertTo());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<Staff> findAll(int page, int pageSize) {

        page = page < 0 ? 0 : page;
        pageSize = pageSize < 1 ? 1 : pageSize;

        return staffService.findAll(page, pageSize);
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public List<Staff> findAll() {
        return staffService.findAll();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteStaff(@RequestParam("staffNo") String staffNo) throws ParseException {
        System.out.println(staffNo+" = staffNo");
        staffService.delete(staffNo);
        userService.delete(staffNo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    public void updateStaff(@RequestBody @Validated StaffParam staffParam) throws ParseException {
        staffService.update(staffParam.convertTo());
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Page<Staff> search(String key, int page, int pageSize) {
        return staffService.search(key, page, pageSize);
    }
}
