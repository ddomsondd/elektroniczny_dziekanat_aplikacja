package com.example.dziekanat.model;

public class User {
    private Integer id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    Role role;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }


    public Role getRole() {
        return role;
    }
}
