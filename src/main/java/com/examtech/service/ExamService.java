package com.examtech.service;

import com.examtech.exam.Exam;
import com.examtech.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    // Create a new exam
    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    // Get all exams
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    // Get exam by ID
    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }

    // Update an exam
    public Exam updateExam(Long id, Exam updatedExam) {

        Optional<Exam> existingExam = examRepository.findById(id);

        if (existingExam.isPresent()) {

            Exam exam = existingExam.get();

            exam.setTitle(updatedExam.getTitle());
            exam.setDescription(updatedExam.getDescription());
            exam.setDurationMinutes(updatedExam.getDurationMinutes());
            exam.setTotalMarks(updatedExam.getTotalMarks());
            exam.setActive(updatedExam.isActive());

            return examRepository.save(exam);
        }

        return null;
    }

    // Delete an exam
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }
}
