package com.example.dziekanat.model;

import java.util.Set;

public class Group {

    private Integer id;
    private String name;

    private Integer semester;
    private Set<Class> classes;
    private Set<Student> students;

    public Group() {
        super();
    }

    public Group(String name, Integer semester) {
        this.name = name;
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", semester=" + semester +
                '}';
    }

    public String getName() {
        return name;
    }
}
