package com.example.dziekanat.dto;

import java.util.List;

public class StudentWithGradesDTO {
    private StudentDTO student;
    private List<GradeDTO> grades;

    public StudentWithGradesDTO(StudentDTO student, List<GradeDTO> grades) {
        this.student = student;
        this.grades = grades;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public List<GradeDTO> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeDTO> grades) {
        this.grades = grades;
    }
}