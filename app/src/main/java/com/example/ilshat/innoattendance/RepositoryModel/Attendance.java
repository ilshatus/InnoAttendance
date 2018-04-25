package com.example.ilshat.innoattendance.RepositoryModel;


public class Attendance {
    private User student;
    private boolean attended;

    public Attendance(User student, boolean attended) {
        this.student = student;
        this.attended = attended;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    @Override
    public String toString() {
        return student.getFullName();
    }
}
