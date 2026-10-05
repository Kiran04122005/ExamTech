package com.examtech.repository;

import com.examtech.model.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    // Find all results of a particular student
    List<ExamResult> findByStudentName(String studentName);

    // Find results for a particular exam
    List<ExamResult> findByExamId(Long examId);

    // Find results of a student for a particular exam
    List<ExamResult> findByStudentNameAndExamId(
            String studentName,
            Long examId);
}