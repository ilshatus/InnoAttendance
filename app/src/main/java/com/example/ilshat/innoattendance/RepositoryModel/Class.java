package com.example.ilshat.innoattendance.RepositoryModel;


public class Class {
    private int id;
    private String name;
    private String teacher;
    private int courseId;

    public Class(int id, String name, String teacher, int courseId) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.courseId = courseId;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return name;
    }
}
