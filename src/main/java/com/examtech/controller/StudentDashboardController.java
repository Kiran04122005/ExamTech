package com.examtech.controller;

import com.examtech.exam.Exam;
import com.examtech.model.User;
import com.examtech.service.ExamService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentDashboardController {

    private final ExamService examService;

    public StudentDashboardController(ExamService examService) {
        this.examService = examService;
    }

    // =========================================================
    // STUDENT DASHBOARD
    // URL: /student/dashboard
    // =========================================================

    @GetMapping("/student/dashboard")
    public String showStudentDashboard(
            HttpSession session,
            Model model) {

        // Get logged-in user from session
        User user = (User) session.getAttribute("loggedInUser");

        // If nobody is logged in
        if (user == null) {
            return "redirect:/login";
        }

        // Allow only STUDENT
        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        // Send logged-in user to HTML
        model.addAttribute("user", user);

        // Get all examinations
        List<Exam> exams = examService.getAllExams();

        // Send examinations to dashboard
        model.addAttribute("exams", exams);

        return "student-dashboard";
    }
}