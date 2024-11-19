package com.example.dziekanat.model;

public class Employee {
private Integer id;

    private User user;

    private String position;

    private String faculty;

    private String academicTitle;

    public Employee() {
        super();
    }

    public Employee(User user, String position, String faculty, String academicTitle) {
        this.user = user;
        this.position = position;
        this.faculty = faculty;
        this.academicTitle = academicTitle;
    }

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    public boolean isInFaculty(String facultyName) {
        return this.faculty != null && this.faculty.equalsIgnoreCase(facultyName);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + getFullName() + '\'' +
                ", position='" + position + '\'' +
                ", faculty='" + faculty + '\'' +
                ", academicTitle='" + academicTitle + '\'' +
                ", username='" + user.getUsername() + '\'' +
                '}';
    }
}
