package com.example.ilshat.innoattendance;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Database {
    private String link;

    public Database(String link) {
        this.link = link;
    }

    public String login(String email, String password) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "login");
        requestBody.put("email", email);
        requestBody.put("password", password);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("token");
    }

    public boolean createUser(String token, String id, String name, String surname,
                              String email, String password, String status) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "createUser");
        requestBody.put("token", token);
        requestBody.put("id", id);
        requestBody.put("name", name);
        requestBody.put("surname", surname);
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("status", status);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean editUser(String token, String id, String name, String surname,
                            String email, String password, String status) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "editUser");
        requestBody.put("token", token);
        requestBody.put("id", id);
        requestBody.put("name", name);
        requestBody.put("surname", surname);
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("status", status);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean addStudentToGroup(String token, String student, String group) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "addStudentToGroup");
        requestBody.put("token", token);
        requestBody.put("student", student);
        requestBody.put("group", group);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean removeStudentFromGroup(String token, String student, String group) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "removeStudentFromGroup");
        requestBody.put("token", token);
        requestBody.put("student", student);
        requestBody.put("group", group);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean attachRepresentativeToGroup(String token, String student, String group) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "attachRepresentativeToGroup");
        requestBody.put("token", token);
        requestBody.put("student", student);
        requestBody.put("group", group);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean detachRepresentativeFromGroup(String token, String student, String group) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "detachRepresentativeFromGroup");
        requestBody.put("token", token);
        requestBody.put("student", student);
        requestBody.put("group", group);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean createSubject(String token, String subject) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "createSubject");
        requestBody.put("token", token);
        requestBody.put("subject", subject);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }
/*
    public boolean renameSubject(String token, String subject) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "renameSubject");
        requestBody.put("token", token);
        requestBody.put("subject", subject);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }
*/
    public boolean createCourseToSubject(String token, String subject, String course, String year) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "createCourseToSubject");
        requestBody.put("token", token);
        requestBody.put("subject", subject);
        requestBody.put("course", course);
        requestBody.put("year", year);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean removeCourseFromSubject(String token, String subject, String course, String year) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "removeCourseFromSubject");
        requestBody.put("token", token);
        requestBody.put("subject", subject);
        requestBody.put("course", course);
        requestBody.put("year", year);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean createClassToCourse(String token, String course, String className) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "createClassToCourse");
        requestBody.put("token", token);
        requestBody.put("course", course);
        requestBody.put("className", className);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean removeClassFromCourse(String token, String course, String className) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "removeClassFromCourse");
        requestBody.put("token", token);
        requestBody.put("course", course);
        requestBody.put("className", className);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean attachTeacherToClass(String token, String className, String teacher) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "attachTeacherToClass");
        requestBody.put("token", token);
        requestBody.put("className", className);
        requestBody.put("teacher", teacher);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean addEventToClass(String token, String className, String name,
                                   String date, String place) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "addEventToClass");
        requestBody.put("token", token);
        requestBody.put("className", className);
        requestBody.put("name", name);
        requestBody.put("date", date);
        requestBody.put("place", place);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean removeEventFromClass(String token, String className, String name,
                                        String date, String place) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "removeEventFromClass");
        requestBody.put("token", token);
        requestBody.put("className", className);
        requestBody.put("name", name);
        requestBody.put("date", date);
        requestBody.put("place", place);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean addExistingGroupToClass(String token, String className, String group) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "addExistingGroupToClass");
        requestBody.put("token", token);
        requestBody.put("className", className);
        requestBody.put("group", group);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean createGroupToClass(String token, String className, String group) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "createGroupToClass");
        requestBody.put("token", token);
        requestBody.put("className", className);
        requestBody.put("group", group);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean removeGroupFromClass(String token, String className, String group) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "removeGroupFromClass");
        requestBody.put("token", token);
        requestBody.put("className", className);
        requestBody.put("group", group);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }
/*
    public boolean createTeacher(String token, String teacher) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "addCourseToSubject");
        requestBody.put("token", token);
        requestBody.put("teacher", teacher);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }

    public boolean removeTeacher(String token, String teacher) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("do", "removeCourseFromSubject");
        requestBody.put("token", token);
        requestBody.put("teacher", teacher);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(link);

        return response.jsonPath().get("result").equals("OK");
    }
*/
}
