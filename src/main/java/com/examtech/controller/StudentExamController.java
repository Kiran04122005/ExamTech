package com.examtech.controller;

import com.examtech.exam.Exam;
import com.examtech.model.ExamAnswer;
import com.examtech.model.ExamResult;
import com.examtech.model.User;
import com.examtech.question.Question;
import com.examtech.repository.ExamAnswerRepository;
import com.examtech.repository.ExamRepository;
import com.examtech.repository.QuestionRepository;
import com.examtech.service.ExamResultService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class StudentExamController {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExamResultService examResultService;
    private final ExamAnswerRepository examAnswerRepository;

    public StudentExamController(
            ExamRepository examRepository,
            QuestionRepository questionRepository,
            ExamResultService examResultService,
            ExamAnswerRepository examAnswerRepository) {

        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.examResultService = examResultService;
        this.examAnswerRepository = examAnswerRepository;
    }

    // =========================================================
    // START EXAM
    // URL: GET /student/exam/{id}
    // =========================================================

    @GetMapping("/student/exam/{id}")
    public String startExam(
            @PathVariable Long id,
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
        // Only STUDENT can attempt exams
        // -----------------------------------------------------

        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        // -----------------------------------------------------
        // Find exam
        // -----------------------------------------------------

        Optional<Exam> examOptional = examRepository.findById(id);

        if (examOptional.isEmpty()) {
            return "redirect:/student/dashboard";
        }

        Exam exam = examOptional.get();

        // -----------------------------------------------------
        // Only active exams can be attempted
        // -----------------------------------------------------

        if (!exam.isActive()) {
            return "redirect:/student/dashboard";
        }

        // -----------------------------------------------------
        // Get questions
        // -----------------------------------------------------

        List<Question> questions = questionRepository.findByExamId(id);

        // -----------------------------------------------------
        // Randomize question order
        // -----------------------------------------------------

        Collections.shuffle(questions);

        model.addAttribute("exam", exam);
        model.addAttribute("questions", questions);

        return "student-exam";
    }

    // =========================================================
    // SUBMIT EXAM
    // URL: POST /student/exam/{id}/submit
    // =========================================================

    @PostMapping("/student/exam/{id}/submit")
    public String submitExam(
            @PathVariable Long id,
            @RequestParam Map<String, String> answers,
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
        // Only STUDENT can submit exams
        // -----------------------------------------------------

        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        // -----------------------------------------------------
        // Find exam
        // -----------------------------------------------------

        Optional<Exam> examOptional = examRepository.findById(id);

        if (examOptional.isEmpty()) {
            return "redirect:/student/dashboard";
        }

        Exam exam = examOptional.get();

        // -----------------------------------------------------
        // Only active exams can be submitted
        // -----------------------------------------------------

        if (!exam.isActive()) {
            return "redirect:/student/dashboard";
        }

        // -----------------------------------------------------
        // Get questions
        // -----------------------------------------------------

        List<Question> questions = questionRepository.findByExamId(id);

        int objectiveMarks = 0;
        int objectiveTotalMarks = 0;

        boolean hasManualEvaluation = false;

        // -----------------------------------------------------
        // Get logged-in student
        // -----------------------------------------------------

        String studentName = "Student";

        if (user.getName() != null) {
            studentName = user.getName();
        }

        // =====================================================
        // Process questions for automatic grading
        // =====================================================

        for (Question question : questions) {

            String answerKey = "answer_" + question.getId();

            String studentAnswer = answers.get(answerKey);

            if (studentAnswer == null) {
                studentAnswer = "";
            }

            studentAnswer = studentAnswer.trim();

            // =================================================
            // MCQ
            // Automatically graded
            // =================================================

            if ("MCQ".equalsIgnoreCase(question.getType())) {

                if (question.getMarks() != null) {
                    objectiveTotalMarks += question.getMarks();
                }

                if (!studentAnswer.isEmpty()
                        && question.getCorrectAnswer() != null
                        && studentAnswer.equalsIgnoreCase(
                                question.getCorrectAnswer())) {

                    if (question.getMarks() != null) {
                        objectiveMarks += question.getMarks();
                    }
                }
            }

            // =================================================
            // SHORT ANSWER
            // Examiner evaluates later
            // =================================================

            else if ("SHORT_ANSWER".equalsIgnoreCase(
                    question.getType())) {

                hasManualEvaluation = true;
            }

            // =================================================
            // ESSAY
            // Examiner evaluates later
            // =================================================

            else if ("ESSAY".equalsIgnoreCase(
                    question.getType())) {

                hasManualEvaluation = true;
            }
        }

        // -----------------------------------------------------
        // Create ExamResult
        // -----------------------------------------------------

        ExamResult result = new ExamResult();

        result.setStudentName(studentName);

        result.setExam(exam);

        // Automatically calculated marks
        result.setObjectiveMarks(objectiveMarks);

        // Initially total marks = objective marks
        result.setTotalMarksObtained(objectiveMarks);

        // Maximum marks of the exam
        result.setMaximumMarks(exam.getTotalMarks());

        // Result status
        if (hasManualEvaluation) {
            result.setStatus("Pending Evaluation");
        } else {
            result.setStatus("Completed");
        }

        // -----------------------------------------------------
        // Save result first
        // This generates ExamResult ID
        // -----------------------------------------------------

        ExamResult savedResult = examResultService.saveResult(result);

        // -----------------------------------------------------
        // Save every student's answer
        // -----------------------------------------------------

        for (Question question : questions) {

            String answerKey = "answer_" + question.getId();

            String studentAnswer = answers.get(answerKey);

            if (studentAnswer == null) {
                studentAnswer = "";
            }

            studentAnswer = studentAnswer.trim();

            // Create answer record
            ExamAnswer examAnswer = new ExamAnswer();

            examAnswer.setStudentName(studentName);

            examAnswer.setExamResult(savedResult);

            examAnswer.setQuestion(question);

            examAnswer.setStudentAnswer(studentAnswer);

            // =================================================
            // MCQ
            // Automatically evaluated
            // =================================================

            if ("MCQ".equalsIgnoreCase(question.getType())) {

                int marksAwarded = 0;

                if (!studentAnswer.isEmpty()
                        && question.getCorrectAnswer() != null
                        && studentAnswer.equalsIgnoreCase(
                                question.getCorrectAnswer())) {

                    if (question.getMarks() != null) {
                        marksAwarded = question.getMarks();
                    }
                }

                examAnswer.setMarksAwarded(marksAwarded);

                examAnswer.setFeedback(
                        "Automatically evaluated.");
            }

            // =================================================
            // SHORT ANSWER / ESSAY
            // Examiner evaluates later
            // =================================================

            else {

                examAnswer.setMarksAwarded(0);

                examAnswer.setFeedback(
                        "Pending examiner evaluation.");
            }

            // Save answer to database
            examAnswerRepository.save(examAnswer);
        }

        // -----------------------------------------------------
        // Send data to result page
        // -----------------------------------------------------

        model.addAttribute(
                "exam",
                exam);

        model.addAttribute(
                "questions",
                questions);

        model.addAttribute(
                "objectiveMarks",
                objectiveMarks);

        model.addAttribute(
                "objectiveTotalMarks",
                objectiveTotalMarks);

        model.addAttribute(
                "result",
                savedResult);

        return "exam-result";
    }
}