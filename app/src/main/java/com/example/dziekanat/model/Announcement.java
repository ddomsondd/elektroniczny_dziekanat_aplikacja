package com.example.dziekanat.model;

import java.time.LocalDateTime;

public class Announcement {

    private Integer id;

    private String title;
    private String content;

    private Admin admin;
    private LocalDateTime createdAt;

    public Announcement() {
        super();
    }

    public Announcement(String title, String content, Admin admin) {
        this.title = title;
        this.content = content;
        this.admin = admin;
    }

    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", admin=" + admin.getFullName() +
                ", createdAt=" + createdAt +
                '}';
    }
}
