package com.examtech.service;

import com.examtech.model.ExamResult;
import com.examtech.repository.ExamResultRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamResultService {

    private final ExamResultRepository examResultRepository;

    public ExamResultService(ExamResultRepository examResultRepository) {
        this.examResultRepository = examResultRepository;
    }

    // Save exam result
    public ExamResult saveResult(ExamResult result) {
        return examResultRepository.save(result);
    }

    // Get result by ID
    public Optional<ExamResult> getResultById(Long id) {
        return examResultRepository.findById(id);
    }

    // Get all results
    public List<ExamResult> getAllResults() {
        return examResultRepository.findAll();
    }

    // Get results of a student
    public List<ExamResult> getStudentResults(String studentName) {
        return examResultRepository.findByStudentName(studentName);
    }

    // Get results of a particular exam
    public List<ExamResult> getExamResults(Long examId) {
        return examResultRepository.findByExamId(examId);
    }

    // Get result of a student for a particular exam
    public List<ExamResult> getStudentExamResult(
            String studentName,
            Long examId) {

        return examResultRepository.findByStudentNameAndExamId(
                studentName,
                examId);
    }

    // Delete result
    public void deleteResult(Long id) {
        examResultRepository.deleteById(id);
    }
}