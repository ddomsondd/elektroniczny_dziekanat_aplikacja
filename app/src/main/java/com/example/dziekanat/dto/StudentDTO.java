package com.example.dziekanat.dto;

public class StudentDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String studentIndex;
    private String yearOfStudy;
    private String faculty;
    private String specialization;

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getStudentIndex() {
        return studentIndex;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getSpecialization() {
        return specialization;
    }
}
