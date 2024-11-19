package com.example.dziekanat.model;


import java.time.LocalDate;

public class Grade {
    private Integer id;

    private Student student;

    private java.lang.Class classes;

    private Double grade;

    private LocalDate date = LocalDate.now();

    public Grade() {
        super();
    }

    public Grade(Student student, java.lang.Class classes, Double grade, LocalDate date) {
        this.student = student;
        this.classes = classes;
        this.grade = grade;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", student=" + student.getFullName() +
                ", classes=" + classes.getName() +
                ", grade=" + grade +
                ", date=" + date +
                '}';
    }
}
