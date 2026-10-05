package com.examtech.controller;

import com.examtech.exam.Exam;
import com.examtech.model.User;
import com.examtech.service.ExamService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/examiner")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    // =========================================================
    // EXAMINER DASHBOARD
    // URL: /examiner/dashboard
    // =========================================================

    @GetMapping("/dashboard")
    public String examinerDashboard(
            HttpSession session,
            Model model) {

        // Check logged-in user
        User user = (User) session.getAttribute("loggedInUser");

        // If not logged in
        if (user == null) {
            return "redirect:/login";
        }

        // Allow only EXAMINER
        if (!"EXAMINER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        List<Exam> exams = examService.getAllExams();

        model.addAttribute("exams", exams);

        return "examiner-dashboard";
    }

    // =========================================================
    // CREATE EXAM PAGE
    // URL: /examiner/create-exam
    // =========================================================

    @GetMapping("/create-exam")
    public String showCreateExamPage(
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"EXAMINER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("exam", new Exam());

        return "create-exam";
    }

    // =========================================================
    // SAVE NEW EXAM
    // URL: POST /examiner/create-exam
    // =========================================================

    @PostMapping("/create-exam")
    public String createExam(
            @ModelAttribute Exam exam,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"EXAMINER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        examService.createExam(exam);

        return "redirect:/examiner/dashboard";
    }

    // =========================================================
    // EDIT EXAM PAGE
    // URL: /examiner/edit-exam/{id}
    // =========================================================

    @GetMapping("/edit-exam/{id}")
    public String showEditExamPage(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"EXAMINER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        Exam exam = examService
                .getExamById(id)
                .orElse(null);

        if (exam == null) {
            return "redirect:/examiner/dashboard";
        }

        model.addAttribute("exam", exam);

        return "edit-exam";
    }

    // =========================================================
    // UPDATE EXAM
    // URL: POST /examiner/edit-exam/{id}
    // =========================================================

    @PostMapping("/edit-exam/{id}")
    public String updateExam(
            @PathVariable Long id,
            @ModelAttribute Exam exam,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"EXAMINER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        examService.updateExam(id, exam);

        return "redirect:/examiner/dashboard";
    }

    // =========================================================
    // DELETE EXAM
    // URL: /examiner/delete-exam/{id}
    // =========================================================

    @GetMapping("/delete-exam/{id}")
    public String deleteExam(
            @PathVariable Long id,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"EXAMINER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        examService.deleteExam(id);

        return "redirect:/examiner/dashboard";
    }
}