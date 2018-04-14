package com.example.ilshat.innoattendance.RepositoryModel;


public class Course {
    private int id;
    private String name;
    private String year;
    private int subjectId;

    public Course(int id, String name, String year, int subjectId) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.subjectId = subjectId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public int getSubject() {
        return subjectId;
    }
}
