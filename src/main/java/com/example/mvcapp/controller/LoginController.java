package com.example.mvcapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Show the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

}
