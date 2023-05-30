package com.connectu.connectuapi.controller;

import com.connectu.connectuapi.service.IThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/threads")
public class ThreadController {
    @Autowired
    private IThreadService threadService;
    @PostMapping("/addFakeThread")
    public String addFakeUsers() {
        threadService.addFakeThread(10);
        return "Fake Thread added successfully!";
    }
}
