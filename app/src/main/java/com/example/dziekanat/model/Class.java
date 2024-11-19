package com.example.dziekanat.model;


public class Class {


    private Integer id;
    private String name;
    private Employee employee;

    private Group group;

    public Class() {
        super();
    }

    public Class(String name, Employee employee, Group group) {
        this.name = name;
        this.employee = employee;
        this.group = group;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employee=" + employee.getFullName() +
                ", group=" + group.getName() +
                '}';
    }
}
