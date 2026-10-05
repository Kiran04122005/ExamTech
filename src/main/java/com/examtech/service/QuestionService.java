package com.examtech.service;

import com.examtech.exam.Exam;
import com.examtech.question.Question;
import com.examtech.repository.ExamAnswerRepository;
import com.examtech.repository.ExamRepository;
import com.examtech.repository.QuestionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamAnswerRepository examAnswerRepository;
    private final ExamRepository examRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            ExamAnswerRepository examAnswerRepository,
            ExamRepository examRepository) {

        this.questionRepository = questionRepository;
        this.examAnswerRepository = examAnswerRepository;
        this.examRepository = examRepository;
    }

    // ---------------------------------------------------------
    // Create a new question
    // ---------------------------------------------------------

    public Question createQuestion(Question question) {

        return questionRepository.save(question);
    }

    // ---------------------------------------------------------
    // Get all questions
    // ---------------------------------------------------------

    public List<Question> getAllQuestions() {

        return questionRepository.findAll();
    }

    // ---------------------------------------------------------
    // Get question by ID
    // ---------------------------------------------------------

    public Optional<Question> getQuestionById(Long id) {

        return questionRepository.findById(id);
    }

    // ---------------------------------------------------------
    // Get questions for a particular exam
    // ---------------------------------------------------------

    public List<Question> getQuestionsByExam(Long examId) {

        return questionRepository.findByExamId(examId);
    }

    // ---------------------------------------------------------
    // Get questions by type
    // ---------------------------------------------------------

    public List<Question> getQuestionsByType(String type) {

        return questionRepository.findByType(type);
    }

    // ---------------------------------------------------------
    // Update a question
    // ---------------------------------------------------------

    public Question updateQuestion(
            Long id,
            Question updatedQuestion) {

        Optional<Question> existingQuestion = questionRepository.findById(id);

        if (existingQuestion.isPresent()) {

            Question question = existingQuestion.get();

            question.setQuestionText(
                    updatedQuestion.getQuestionText());

            question.setType(
                    updatedQuestion.getType());

            question.setOptionA(
                    updatedQuestion.getOptionA());

            question.setOptionB(
                    updatedQuestion.getOptionB());

            question.setOptionC(
                    updatedQuestion.getOptionC());

            question.setOptionD(
                    updatedQuestion.getOptionD());

            question.setCorrectAnswer(
                    updatedQuestion.getCorrectAnswer());

            question.setMarks(
                    updatedQuestion.getMarks());

            question.setExam(
                    updatedQuestion.getExam());

            return questionRepository.save(question);
        }

        return null;
    }

    // ---------------------------------------------------------
    // Delete a question safely
    // ---------------------------------------------------------

    public boolean deleteQuestion(Long id) {

        // Check whether the question exists
        if (!questionRepository.existsById(id)) {

            return false;
        }

        // Check whether this question has already
        // been used in a submitted exam
        boolean alreadyUsed = examAnswerRepository.existsByQuestionId(id);

        if (alreadyUsed) {

            // Do not delete the question because
            // student answers depend on it
            return false;
        }

        // Safe to delete
        questionRepository.deleteById(id);

        return true;
    }

    // ---------------------------------------------------------
    // Reuse a question
    // ---------------------------------------------------------
    //
    // This creates a COPY of the existing question.
    // The original question remains in its original exam.
    //
    // ---------------------------------------------------------

    public boolean reuseQuestion(
            Long questionId,
            Long targetExamId) {

        // Find original question
        Optional<Question> sourceOptional = questionRepository.findById(questionId);

        // Find target exam
        Optional<Exam> targetExamOptional = examRepository.findById(targetExamId);

        // If either does not exist
        if (sourceOptional.isEmpty()
                || targetExamOptional.isEmpty()) {

            return false;
        }

        Question sourceQuestion = sourceOptional.get();

        Exam targetExam = targetExamOptional.get();

        // -----------------------------------------------------
        // Prevent copying the question into the same exam
        // -----------------------------------------------------

        if (sourceQuestion.getExam() != null
                && sourceQuestion.getExam().getId()
                        .equals(targetExam.getId())) {

            return false;
        }

        // -----------------------------------------------------
        // Create a new Question object
        // -----------------------------------------------------

        Question copiedQuestion = new Question();

        // Copy question text
        copiedQuestion.setQuestionText(
                sourceQuestion.getQuestionText());

        // Copy question type
        copiedQuestion.setType(
                sourceQuestion.getType());

        // Copy MCQ options
        copiedQuestion.setOptionA(
                sourceQuestion.getOptionA());

        copiedQuestion.setOptionB(
                sourceQuestion.getOptionB());

        copiedQuestion.setOptionC(
                sourceQuestion.getOptionC());

        copiedQuestion.setOptionD(
                sourceQuestion.getOptionD());

        // Copy correct answer
        copiedQuestion.setCorrectAnswer(
                sourceQuestion.getCorrectAnswer());

        // Copy marks
        copiedQuestion.setMarks(
                sourceQuestion.getMarks());

        // Attach the copied question
        // to the selected target exam
        copiedQuestion.setExam(targetExam);

        // Save as a NEW question
        questionRepository.save(copiedQuestion);

        return true;
    }
}