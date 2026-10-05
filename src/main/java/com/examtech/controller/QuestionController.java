package com.examtech.controller;

import com.examtech.exam.Exam;
import com.examtech.model.User;
import com.examtech.question.Question;
import com.examtech.service.ExamService;
import com.examtech.service.QuestionService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/examiner/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final ExamService examService;

    public QuestionController(
            QuestionService questionService,
            ExamService examService) {

        this.questionService = questionService;
        this.examService = examService;
    }

    // =========================================================
    // QUESTION BANK
    // =========================================================

    @GetMapping
    public String showQuestionBank(
            HttpSession session,
            Model model) {

        if (!isExaminer(session)) {
            return "redirect:/login";
        }

        List<Question> questions = questionService.getAllQuestions();

        model.addAttribute(
                "questions",
                questions);

        return "question-bank";
    }

    // =========================================================
    // CREATE QUESTION PAGE
    // =========================================================

    @GetMapping("/create")
    public String showCreateQuestionPage(
            HttpSession session,
            Model model) {

        if (!isExaminer(session)) {
            return "redirect:/login";
        }

        model.addAttribute(
                "question",
                new Question());

        List<Exam> exams = examService.getAllExams();

        model.addAttribute(
                "exams",
                exams);

        return "create-question";
    }

    // =========================================================
    // CREATE QUESTION
    // =========================================================

    @PostMapping("/create")
    public String createQuestion(
            @ModelAttribute Question question,
            HttpSession session) {

        if (!isExaminer(session)) {
            return "redirect:/login";
        }

        questionService.createQuestion(question);

        return "redirect:/examiner/questions";
    }

    // =========================================================
    // EDIT QUESTION PAGE
    // =========================================================

    @GetMapping("/edit/{id}")
    public String showEditQuestionPage(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        if (!isExaminer(session)) {
            return "redirect:/login";
        }

        Question question = questionService
                .getQuestionById(id)
                .orElse(null);

        if (question == null) {
            return "redirect:/examiner/questions";
        }

        List<Exam> exams = examService.getAllExams();

        model.addAttribute(
                "question",
                question);

        model.addAttribute(
                "exams",
                exams);

        return "edit-question";
    }

    // =========================================================
    // UPDATE QUESTION
    // =========================================================

    @PostMapping("/edit/{id}")
    public String updateQuestion(
            @PathVariable Long id,
            @ModelAttribute Question question,
            HttpSession session) {

        if (!isExaminer(session)) {
            return "redirect:/login";
        }

        questionService.updateQuestion(
                id,
                question);

        return "redirect:/examiner/questions";
    }

    // =========================================================
    // DELETE QUESTION
    // =========================================================

    @GetMapping("/delete/{id}")
    public String deleteQuestion(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        if (!isExaminer(session)) {
            return "redirect:/login";
        }

        boolean deleted = questionService.deleteQuestion(id);

        if (!deleted) {

            List<Question> questions = questionService.getAllQuestions();

            model.addAttribute(
                    "questions",
                    questions);

            model.addAttribute(
                    "error",
                    "This question cannot be deleted because it has already been used in a submitted exam.");

            return "question-bank";
        }

        return "redirect:/examiner/questions";
    }

    // =========================================================
    // SHOW REUSE QUESTION PAGE
    // =========================================================

    @GetMapping("/reuse/{id}")
    public String showReuseQuestionPage(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        if (!isExaminer(session)) {
            return "redirect:/login";
        }

        Question question = questionService
                .getQuestionById(id)
                .orElse(null);

        if (question == null) {
            return "redirect:/examiner/questions";
        }

        List<Exam> exams = examService.getAllExams();

        model.addAttribute(
                "question",
                question);

        model.addAttribute(
                "exams",
                exams);

        return "reuse-question";
    }

    // =========================================================
    // REUSE QUESTION
    // =========================================================

    @PostMapping("/reuse/{id}")
    public String reuseQuestion(
            @PathVariable Long id,
            @RequestParam("targetExamId") Long targetExamId,
            HttpSession session,
            Model model) {

        if (!isExaminer(session)) {
            return "redirect:/login";
        }

        boolean reused = questionService.reuseQuestion(
                id,
                targetExamId);

        if (!reused) {

            Question question = questionService
                    .getQuestionById(id)
                    .orElse(null);

            if (question == null) {
                return "redirect:/examiner/questions";
            }

            List<Exam> exams = examService.getAllExams();

            model.addAttribute(
                    "question",
                    question);

            model.addAttribute(
                    "exams",
                    exams);

            model.addAttribute(
                    "error",
                    "The question cannot be reused in the selected exam. Please select a different exam.");

            return "reuse-question";
        }

        return "redirect:/examiner/questions";
    }

    // =========================================================
    // CHECK EXAMINER LOGIN
    // =========================================================

    private boolean isExaminer(
            HttpSession session) {

        Object userObject = session.getAttribute("loggedInUser");

        if (userObject == null) {
            return false;
        }

        User user = (User) userObject;

        return "EXAMINER"
                .equalsIgnoreCase(user.getRole());
    }
}