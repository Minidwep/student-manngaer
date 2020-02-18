package com.example.studentmanager.controller;

import com.example.studentmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/username-exists", method = RequestMethod.GET)
    public boolean usernameExists(String username) {
        return userService.usernameExists(username);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(String username, String password) {

    }
}