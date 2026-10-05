package com.examtech.repository;

import com.examtech.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Find all questions belonging to a particular exam
    List<Question> findByExamId(Long examId);

    // Find questions by type
    List<Question> findByType(String type);
}