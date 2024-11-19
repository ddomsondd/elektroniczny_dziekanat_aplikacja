package com.example.dziekanat.model;

public class Student {

    private Integer id;
    private User user;
    private Group group;

    public void setUser(User user) {
        this.user = user;
    }

    private String studentIndex;
    private Integer yearOfStudy;
    private String faculty;
    private String specialization;

    public Student() {
        super();
    }

    public Student(User user, Group group, String studentIndex, Integer yearOfStudy, String faculty, String specialization) {
        this.user = user;
        this.group = group;
        this.studentIndex = studentIndex;
        this.yearOfStudy = yearOfStudy;
        this.faculty = faculty;
        this.specialization = specialization;
    }

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    public boolean isInFaculty(String facultyName) {
        return this.faculty != null && this.faculty.equalsIgnoreCase(facultyName);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentIndex='" + studentIndex + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", yearOfStudy=" + yearOfStudy +
                ", faculty='" + faculty + '\'' +
                ", specialization='" + specialization + '\'' +
                ", username='" + user.getUsername() + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public Integer getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public String getStudentIndex() {
        return studentIndex;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getSpecialization() {
        return specialization;
    }
}
