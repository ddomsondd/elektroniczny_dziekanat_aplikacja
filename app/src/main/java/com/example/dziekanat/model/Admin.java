package com.example.dziekanat.model;

public class Admin {

    private Integer id;
  private User user;

    private String position;

    public Admin() {
        super();
    }

    public Admin(User user, String position) {
        this.user = user;
        this.position = position;
    }

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", fullName='" + getFullName() + '\'' +
                ", position='" + position + '\'' +
                ", username='" + user.getUsername() + '\'' +
                '}';
    }
}
