package com.example.ilshat.innoattendance.RepositoryModel;


public class Group {
    private String id;
    private String representativeId;

    Group(String id, String representativeId) {
        this.id = id;
        this.representativeId = representativeId;
    }

    public String getId() {
        return id;
    }

    public String getRepresentativeId() {
        return representativeId;
    }

    @Override
    public String toString() {
        return id;
    }
}
