package com.examtech.model;

import com.examtech.exam.Exam;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_results")
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Student name
    private String studentName;

    // Exam attempted
    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    // Automatically calculated MCQ/objective marks
    private Integer objectiveMarks;

    // Total marks obtained
    // Initially contains objective marks.
    // Examiner marks are added later.
    private Integer totalMarksObtained;

    // Maximum marks of the exam
    private Integer maximumMarks;

    // Result status
    // Examples:
    // Pending Evaluation
    // Completed
    private String status;

    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public ExamResult() {
    }

    // =========================================================
    // ID
    // =========================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // =========================================================
    // STUDENT NAME
    // =========================================================

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // =========================================================
    // EXAM
    // =========================================================

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    // =========================================================
    // OBJECTIVE MARKS
    // =========================================================

    public Integer getObjectiveMarks() {
        return objectiveMarks;
    }

    public void setObjectiveMarks(Integer objectiveMarks) {
        this.objectiveMarks = objectiveMarks;
    }

    // =========================================================
    // TOTAL MARKS OBTAINED
    // =========================================================

    public Integer getTotalMarksObtained() {
        return totalMarksObtained;
    }

    public void setTotalMarksObtained(Integer totalMarksObtained) {
        this.totalMarksObtained = totalMarksObtained;
    }

    // =========================================================
    // MAXIMUM MARKS
    // =========================================================

    public Integer getMaximumMarks() {
        return maximumMarks;
    }

    public void setMaximumMarks(Integer maximumMarks) {
        this.maximumMarks = maximumMarks;
    }

    // =========================================================
    // STATUS
    // =========================================================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}