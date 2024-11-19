package com.example.dziekanat.dto;

import java.time.LocalDate;

public class GradeDTO {
    private Integer id;
    private String studentFullName;
    private String className;
    private Double grade;
    private LocalDate date;

    public Integer getId() {
        return id;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public String getClassName() {
        return className;
    }

    public Double getGrade() {
        return grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
