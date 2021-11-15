package com.example.agrawal_classes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Transactional
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home Page");
        model.addAttribute("message", "Welcome to our website!");
        return "home/index";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicy(Model model) {
        model.addAttribute("title", "Privacy Policy");
        return "home/privacypolicy";
    }

    @GetMapping("/user")
    public String userPage(Model model) {
        model.addAttribute("title", "User Page");
        model.addAttribute("message", "Welcome, User!");
        return "home/user";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("title", "Admin Page");
        model.addAttribute("message", "Welcome, Admin!");
        return "home/admin";
    }

    @GetMapping("/staff")
    public String staffPage(Model model) {
        model.addAttribute("title", "Staff Page");
        model.addAttribute("message", "Welcome, Staff!");
        return "home/staff";
    }

    @GetMapping("/teacher")
    public String teacherPage(Model model) {
        model.addAttribute("title", "Teacher Page");
        model.addAttribute("message", "Welcome, Teacher!");
        return "home/teacher";
    }

    @GetMapping("/student")
    public String studentPage(Model model) {
        model.addAttribute("title", "Student Page");
        model.addAttribute("message", "Welcome, Student!");
        return "home/student";
    }

    @GetMapping({ "/admin/academics", "/student/academics", "/staff/academics", "/teacher/academics" })
    public String academicPortal(Model model) {
        model.addAttribute("title", "Academic Portal");
        model.addAttribute("message", "Manage all academic stuff");
        return "home/academic";
    }

}
