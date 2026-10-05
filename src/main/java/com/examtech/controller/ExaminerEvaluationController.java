package com.examtech.controller;

import com.examtech.model.ExamAnswer;
import com.examtech.model.ExamResult;
import com.examtech.model.User;
import com.examtech.repository.ExamAnswerRepository;
import com.examtech.service.ExamResultService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/examiner")
public class ExaminerEvaluationController {

    private final ExamResultService examResultService;
    private final ExamAnswerRepository examAnswerRepository;

    public ExaminerEvaluationController(
            ExamResultService examResultService,
            ExamAnswerRepository examAnswerRepository) {

        this.examResultService = examResultService;
        this.examAnswerRepository = examAnswerRepository;
    }

    // =========================================================
    // SHOW ALL RESULTS FOR EVALUATION
    // =========================================================
    @GetMapping("/evaluate")
    public String showEvaluationPage(
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        // User must be logged in
        if (user == null) {
            return "redirect:/login";
        }

        // Only EXAMINER can access this page
        if (!"EXAMINER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        List<ExamResult> results = examResultService.getAllResults();

        model.addAttribute("results", results);

        return "examiner-evaluate";
    }

    // =========================================================
    // SHOW EVALUATION FORM FOR ONE RESULT
    // =========================================================
    @GetMapping("/evaluate/{id}")
    public String showEvaluationForm(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        // User must be logged in
        if (user == null) {
            return "redirect:/login";
        }

        // Only EXAMINER can evaluate
        if (!"EXAMINER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        ExamResult result = examResultService.getResultById(id).orElse(null);

        if (result == null) {
            return "redirect:/examiner/evaluate";
        }

        List<ExamAnswer> answers = examAnswerRepository.findByExamResultId(id);

        model.addAttribute("result", result);
        model.addAttribute("answers", answers);

        return "examiner-evaluation";
    }

    // =========================================================
    // SAVE EVALUATION
    // =========================================================
    @PostMapping("/evaluate/{id}/save")
    public String saveEvaluation(
            @PathVariable Long id,
            @RequestParam Map<String, String> formData,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        // User must be logged in
        if (user == null) {
            return "redirect:/login";
        }

        // Only EXAMINER can save evaluation
        if (!"EXAMINER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        ExamResult result = examResultService.getResultById(id).orElse(null);

        if (result == null) {
            return "redirect:/examiner/evaluate";
        }

        List<ExamAnswer> answers = examAnswerRepository.findByExamResultId(id);

        int manualMarks = 0;

        for (ExamAnswer answer : answers) {

            if (answer.getQuestion() == null) {
                continue;
            }

            String type = answer.getQuestion().getType();

            // Evaluate only SHORT ANSWER and ESSAY questions
            if ("SHORT_ANSWER".equalsIgnoreCase(type)
                    || "ESSAY".equalsIgnoreCase(type)) {

                String marksKey = "marks_" + answer.getId();
                String feedbackKey = "feedback_" + answer.getId();

                String marksValue = formData.get(marksKey);
                String feedbackValue = formData.get(feedbackKey);

                int marks = 0;

                if (marksValue != null
                        && !marksValue.trim().isEmpty()) {

                    try {
                        marks = Integer.parseInt(marksValue.trim());
                    } catch (NumberFormatException e) {
                        marks = 0;
                    }
                }

                // Prevent negative marks
                if (marks < 0) {
                    marks = 0;
                }

                // Do not allow marks above question maximum
                Integer maximum = answer.getQuestion().getMarks();

                if (maximum != null && marks > maximum) {
                    marks = maximum;
                }

                answer.setMarksAwarded(marks);

                if (feedbackValue != null) {
                    answer.setFeedback(feedbackValue.trim());
                } else {
                    answer.setFeedback("");
                }

                examAnswerRepository.save(answer);

                manualMarks += marks;
            }
        }

        // Get automatically evaluated objective marks
        int objectiveMarks = result.getObjectiveMarks() != null
                ? result.getObjectiveMarks()
                : 0;

        // Calculate final marks
        int finalMarks = objectiveMarks + manualMarks;

        result.setTotalMarksObtained(finalMarks);

        // Evaluation is now completed
        result.setStatus("Completed");

        examResultService.saveResult(result);

        return "redirect:/examiner/evaluate";
    }
}