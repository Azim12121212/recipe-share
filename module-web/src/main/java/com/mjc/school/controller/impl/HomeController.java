package com.mjc.school.controller.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String showHomePage() {
        return "Welcome back to code with Azim";
    }
}