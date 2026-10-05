package com.examtech.repository;

import com.examtech.model.ExamAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Long> {

    // Get all answers belonging to an exam result
    List<ExamAnswer> findByExamResultId(Long examResultId);

    // Get answers submitted by a particular student
    List<ExamAnswer> findByStudentName(String studentName);

    // Get answers belonging to a particular question
    List<ExamAnswer> findByQuestionId(Long questionId);

    // Check whether a question has already been used in a submitted exam
    boolean existsByQuestionId(Long questionId);
}