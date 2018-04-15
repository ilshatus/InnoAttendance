package com.example.ilshat.innoattendance.RepositoryModel;


public class Event {
    private int id;
    private int classId;
    private String name;
    private String place;
    private String attendedId;
    private String date;
    private String groupId;

    public Event(int id, int classId, String name, String place, String date) {
        this.id = id;
        this.classId = classId;
        this.name = name;
        this.place = place;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getClassId() {
        return classId;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return name;
    }
}
