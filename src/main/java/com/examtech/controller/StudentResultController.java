package com.examtech.controller;

import com.examtech.model.ExamAnswer;
import com.examtech.model.ExamResult;
import com.examtech.model.User;
import com.examtech.repository.ExamAnswerRepository;
import com.examtech.repository.ExamResultRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentResultController {

    private final ExamResultRepository examResultRepository;
    private final ExamAnswerRepository examAnswerRepository;

    public StudentResultController(
            ExamResultRepository examResultRepository,
            ExamAnswerRepository examAnswerRepository) {

        this.examResultRepository = examResultRepository;
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
        // Get logged-in student's name
        // -----------------------------------------------------

        String studentName = user.getName();

        if (studentName == null) {
            studentName = "";
        }

        final String loggedInStudentName = studentName.trim();

        // -----------------------------------------------------
        // Get ALL exam results from database
        // -----------------------------------------------------

        List<ExamResult> allResults = examResultRepository.findAll();

        // -----------------------------------------------------
        // Select ALL results belonging to this student
        // -----------------------------------------------------

        List<ExamResult> results = new ArrayList<>();

        for (ExamResult result : allResults) {

            if (result.getStudentName() == null) {
                continue;
            }

            String resultStudentName = result.getStudentName().trim();

            if (resultStudentName.equalsIgnoreCase(
                    loggedInStudentName)) {

                results.add(result);
            }
        }

        // -----------------------------------------------------
        // Sort results by ID
        // Oldest result first
        // -----------------------------------------------------

        results.sort(
                Comparator.comparing(
                        ExamResult::getId,
                        Comparator.nullsLast(
                                Comparator.naturalOrder())));

        // -----------------------------------------------------
        // Get answers for EVERY result
        // -----------------------------------------------------

        Map<Long, List<ExamAnswer>> answersByResult = new HashMap<>();

        for (ExamResult result : results) {

            if (result.getId() == null) {
                continue;
            }

            List<ExamAnswer> answers = examAnswerRepository.findByExamResultId(
                    result.getId());

            answersByResult.put(
                    result.getId(),
                    answers);
        }

        // -----------------------------------------------------
        // Send data to Thymeleaf
        // -----------------------------------------------------

        model.addAttribute(
                "user",
                user);

        model.addAttribute(
                "results",
                results);

        model.addAttribute(
                "answersByResult",
                answersByResult);

        return "student-results";
    }
}