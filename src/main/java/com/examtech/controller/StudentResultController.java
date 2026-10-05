package com.examtech.controller;

import com.examtech.model.ExamAnswer;
import com.examtech.model.ExamResult;
import com.examtech.model.User;
import com.examtech.repository.ExamAnswerRepository;
import com.examtech.service.ExamResultService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentResultController {

    private final ExamResultService examResultService;
    private final ExamAnswerRepository examAnswerRepository;

    public StudentResultController(
            ExamResultService examResultService,
            ExamAnswerRepository examAnswerRepository) {

        this.examResultService = examResultService;
        this.examAnswerRepository = examAnswerRepository;
    }

    @GetMapping("/student/results")
    public String showStudentResults(
            HttpSession session,
            Model model) {

        // -----------------------------------------------------
        // Check logged-in user
        // -----------------------------------------------------

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        // -----------------------------------------------------
        // Only STUDENT can view student results
        // -----------------------------------------------------

        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        // -----------------------------------------------------
        // Get student's results
        // -----------------------------------------------------

        String studentName = user.getName();

        List<ExamResult> results = examResultService.getStudentResults(studentName);

        // -----------------------------------------------------
        // Get answers for each result
        // -----------------------------------------------------

        Map<Long, List<ExamAnswer>> answersByResult = new HashMap<>();

        for (ExamResult result : results) {

            List<ExamAnswer> answers = examAnswerRepository.findByExamResultId(
                    result.getId());

            answersByResult.put(
                    result.getId(),
                    answers);
        }

        // -----------------------------------------------------
        // Send data to Thymeleaf page
        // -----------------------------------------------------

        model.addAttribute("user", user);
        model.addAttribute("results", results);
        model.addAttribute(
                "answersByResult",
                answersByResult);

        return "student-results";
    }
}