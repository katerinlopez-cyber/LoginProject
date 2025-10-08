package com.example.mvcapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.mvcapp.service.AdminService;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    // This method shows the signup page
    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }

    // This method processes the registration form
    @PostMapping("/signup")
    public String processRegistration(@RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      @RequestParam("fullName") String fullName,
                                      RedirectAttributes redirectAttributes) {
        try {
            adminService.registerAdmin(username, password, fullName);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please log in.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed. Username may already exist.");
            return "redirect:/signup";
        }
    }

    // This method shows the dashboard after a successful login
    @GetMapping("/dashboard")
    public String showDashboard(java.security.Principal principal, org.springframework.ui.Model model) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
            model.addAttribute("success", "Welcome, " + principal.getName() + "!");
        }
        return "dashboard";
    }
}
