package com.connectu.connectuapi.controller;

import com.connectu.connectuapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @PostMapping("/addFakeUsers")
    public String addFakeUsers() {
        userService.addFakeUsers(20);
        return "Fake users added successfully!";
    }
}
