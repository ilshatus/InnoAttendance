package com.example.ilshat.innoattendance.RepositoryModel;


public class User {
    private String id;
    private String name;
    private String surname;
    private String fullName;
    private String email;
    private String status;
    private String password;

    public User(String id, String name, String surname, String email, String status) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.fullName = name + " " + surname;
        this.email = email;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return fullName;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
