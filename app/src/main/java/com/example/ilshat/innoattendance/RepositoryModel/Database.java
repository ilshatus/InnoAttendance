package com.example.ilshat.innoattendance.RepositoryModel;

import okhttp3.*;
import org.json.JSONObject;
import java.util.ArrayList;
import android.util.Log;


public class Database {
    private static final String CLASS = "Database module";

    private String link;
    private OkHttpClient client;
    private static Database db;

    private Database(String link) {
        this.link = link;
        client = new OkHttpClient();
    }

    public static Database getInstance() {
        if(db == null)
            db = new Database(Settings.DATABASE_LINK);
        return db;
    }

    public String login(String email, String password) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "login");
            requestBody.put("email", email);
            requestBody.put("password", password);
            JSONObject json = send(requestBody);
            return (json != null) ? json.getString("token") : null;
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public User createUser(String token, String id, String name, String surname, String email, String password, String status) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "createUser");
            requestBody.put("token", token);
            requestBody.put("id", id);
            requestBody.put("name", name);
            requestBody.put("surname", surname);
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("status", status);
            JSONObject json = send(requestBody);
            return parseUser(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public User editUser(String token, String id, String name, String surname, String email, String password, String status) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "editUser");
            requestBody.put("token", token);
            requestBody.put("id", id);
            requestBody.put("name", name);
            requestBody.put("surname", surname);
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("status", status);
            JSONObject json = send(requestBody);
            return parseUser(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public boolean addStudentToGroup(String token, User student, Group group) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "addStudentToGroup");
            requestBody.put("token", token);
            requestBody.put("student", student.getId());
            requestBody.put("group", group.getId());
            JSONObject json = send(requestBody);
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public boolean removeStudentFromGroup(String token, User student, Group group) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "removeStudentFromGroup");
            requestBody.put("token", token);
            requestBody.put("student", student.getId());
            requestBody.put("group", group.getId());
            JSONObject json = send(requestBody);
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public boolean attachRepresentativeToGroup(String token, User student, Group group) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "attachRepresentativeToGroup");
            requestBody.put("token", token);
            requestBody.put("student", student.getId());
            requestBody.put("group", group.getId());
            JSONObject json = send(requestBody);
            if((json != null) && json.getString("result").equals("OK")) {
                if(json.getString("statusUpdated").equals("updated")){
                    student.setStatus("representative");
                }
                return true;
            }
            return false;
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public boolean detachRepresentativeFromGroup(String token, Group group) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "detachRepresentativeFromGroup");
            requestBody.put("token", token);
            requestBody.put("group", group.getId());
            JSONObject json = send(requestBody);
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public Subject createSubject(String token, String subject) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "createSubject");
            requestBody.put("token", token);
            requestBody.put("subject", subject);
            JSONObject json = send(requestBody);
            return parseSubject(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public Course createCourseToSubject(String token, Subject subject, String course, String year) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "createCourseToSubject");
            requestBody.put("token", token);
            requestBody.put("subject", subject.getId());
            requestBody.put("course", course);
            requestBody.put("year", year);
            JSONObject json = send(requestBody);
            return parseCourse(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public boolean removeCourse(String token, Course course) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "removeCourse");
            requestBody.put("token", token);
            requestBody.put("course", course.getId());
            JSONObject json = send(requestBody);
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public Class createClassToCourse(String token, Course course, String className) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "createClassToCourse");
            requestBody.put("token", token);
            requestBody.put("course", course.getId());
            requestBody.put("className", className);
            JSONObject json = send(requestBody);
            return parseClass(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public boolean removeClass(String token, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "removeClass");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());
            JSONObject json = send(requestBody);
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public boolean attachTeacherToClass(String token, Class classInstance, String teacher) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "attachTeacherToClass");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());
            requestBody.put("teacher", teacher);
            JSONObject json = send(requestBody);
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public Event addEventToClass(String token, Class classInstance, String name, String place, String date) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "addEventToClass");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());
            requestBody.put("name", name);
            requestBody.put("place", place);
            requestBody.put("date", date);
            JSONObject json = send(requestBody);
            return parseEvent(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public boolean removeEvent(String token, Event event) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "removeEvent");
            requestBody.put("token", token);
            requestBody.put("event", event.getId());
            JSONObject json = send(requestBody);
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public boolean addExistingGroupToClass(String token, Class classInstance, Group group) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "addExistingGroupToClass");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());
            requestBody.put("group", group.getId());
            JSONObject json = send(requestBody);
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public Group createGroupToClass(String token, Class classInstance, String group) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "createGroupToClass");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());
            requestBody.put("group", group);
            JSONObject json = send(requestBody);
            return parseGroup(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public boolean removeGroupFromClass(String token, Class classInstance, Group group) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "removeGroupFromClass");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());
            requestBody.put("group", group.getId());
            JSONObject json = send(requestBody);
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public User getUser(String token) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getUser");
            requestBody.put("token", token);
            JSONObject json = send(requestBody);
            return parseUser(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Subject> getListOfSubjects(String token) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfSubjects");
            requestBody.put("token", token);
            JSONObject json = send(requestBody);
            return parseArrayOfSubjects(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Subject> getListOfSubjectsForRepresentative(String token) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfSubjectsForRepresentative");
            requestBody.put("token", token);
            JSONObject json = send(requestBody);
            return parseArrayOfSubjects(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Subject> getListOfSubjectsForStudent(String token) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfSubjectsForStudent");
            requestBody.put("token", token);
            JSONObject json = send(requestBody);
            return parseArrayOfSubjects(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Course> getListOfCourses(String token, Subject subject) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfCourses");
            requestBody.put("token", token);
            requestBody.put("subject", subject.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfCourses(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Course> getListOfCoursesForRepresentative(String token, Subject subject) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfCoursesForRepresentative");
            requestBody.put("token", token);
            requestBody.put("subject", subject.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfCourses(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Course> getListOfCoursesForStudent(String token, Subject subject) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfCoursesForStudent");
            requestBody.put("token", token);
            requestBody.put("subject", subject.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfCourses(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Class> getListOfClasses(String token, Course course) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfClasses");
            requestBody.put("token", token);
            requestBody.put("course", course.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfClasses(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Class> getListOfClassesForRepresentative(String token, Course course) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfClassesForRepresentative");
            requestBody.put("token", token);
            requestBody.put("course", course.getId());
            JSONObject json = send(requestBody);
            ArrayList<Class> result = new ArrayList<Class>();
            return parseArrayOfClasses(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Class> getListOfClassesForStudent(String token, Course course) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfClassesForStudent");
            requestBody.put("token", token);
            requestBody.put("course", course.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfClasses(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Group> getListOfGroups(String token, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfGroups");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfGroups(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Group> getListOfGroupsForRepresentative(String token, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfGroupsForRepresentative");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfGroups(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Group> getListOfGroupsForStudent(String token, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfGroupsForStudent");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfGroups(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<User> getListOfStudents(String token) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfStudents");
            requestBody.put("token", token);
            JSONObject json = send(requestBody);
            return parseArrayOfUsers(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<User> getListOfStudents(String token, Group group) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfStudentsOfGroup");
            requestBody.put("token", token);
            requestBody.put("group", group.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfUsers(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Event> getListOfEvents(String token, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfEvents");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfEvents(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<User> getStudentsAttended(String token, Event event) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getStudentsAttended");
            requestBody.put("token", token);
            requestBody.put("event", event.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfUsers(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public boolean setStudentsAttended(String token, Event event, ArrayList<User> students) {
        try {
            String list = "";
            for(User student : students)
                list = list.concat(student.getId() + ";");

            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "setStudentsAttended");
            requestBody.put("token", token);
            requestBody.put("event", event.getId());
            requestBody.put("list", list);

            JSONObject json = send(requestBody);
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public ArrayList<Event> getAttendance(String token, User student, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getAttendance");
            requestBody.put("token", token);
            requestBody.put("student", student.getId());
            requestBody.put("class", classInstance.getId());
            JSONObject json = send(requestBody);
            return parseArrayOfEvents(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public User getUser(String token, String fullName) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getUserByName");
            requestBody.put("token", token);
            requestBody.put("fullName", fullName);
            JSONObject json = send(requestBody);
            return parseUser(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public Group getGroup(String token, String name) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getGroupByName");
            requestBody.put("token", token);
            requestBody.put("name", name);
            JSONObject json = send(requestBody);
            return parseGroup(json);
        } catch(Exception e) {
            Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    private JSONObject send(JSONObject requestBody) throws Exception {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), encode(requestBody.toString()));
        Request request = new Request.Builder().url(link).post(body).build();
        ResponseBody response = client.newCall(request).execute().body();
        return (response == null) ? null : new JSONObject(decode(response.string()));
    }

    private ArrayList<User> parseArrayOfUsers(JSONObject json) throws Exception {
        if(json == null)
            return null;
        ArrayList<User> result = new ArrayList<User>();
        for(int i = 0; i < json.length(); i++)
            result.add(parseUser(json.getJSONObject(Integer.toString(i))));
        return result;
    }

    private ArrayList<Subject> parseArrayOfSubjects(JSONObject json) throws Exception {
        if(json == null)
            return null;
        ArrayList<Subject> result = new ArrayList<Subject>();
        for(int i = 0; i < json.length(); i++)
            result.add(parseSubject(json.getJSONObject(Integer.toString(i))));
        return result;
    }

    private ArrayList<Course> parseArrayOfCourses(JSONObject json) throws Exception {
        if(json == null)
            return null;
        ArrayList<Course> result = new ArrayList<Course>();
        for(int i = 0; i < json.length(); i++)
            result.add(parseCourse(json.getJSONObject(Integer.toString(i))));
        return result;
    }

    private ArrayList<Class> parseArrayOfClasses(JSONObject json) throws Exception {
        if(json == null)
            return null;
        ArrayList<Class> result = new ArrayList<Class>();
        for(int i = 0; i < json.length(); i++)
            result.add(parseClass(json.getJSONObject(Integer.toString(i))));
        return result;
    }

    private ArrayList<Event> parseArrayOfEvents(JSONObject json) throws Exception {
        if(json == null)
            return null;
        ArrayList<Event> result = new ArrayList<Event>();
        for(int i = 0; i < json.length(); i++)
            result.add(parseEvent(json.getJSONObject(Integer.toString(i))));
        return result;
    }

    private ArrayList<Group> parseArrayOfGroups(JSONObject json) throws Exception {
        if(json == null)
            return null;
        ArrayList<Group> result = new ArrayList<Group>();
        for(int i = 0; i < json.length(); i++)
            result.add(parseGroup(json.getJSONObject(Integer.toString(i))));
        return result;
    }

    private User parseUser(JSONObject json) throws Exception {
        if(json == null)
            return null;
        return new User(json.getString("id"),
                json.getString("name"),
                json.getString("surname"),
                json.getString("email"),
                json.getString("status"));
    }

    private Subject parseSubject(JSONObject json) throws Exception {
        if(json == null)
            return null;
        return new Subject(json.getInt("id"),
                json.getString("name"));
    }

    private Course parseCourse(JSONObject json) throws Exception {
        if(json == null)
            return null;
        return new Course(json.getInt("id"),
                json.getString("name"),
                json.getString("year"),
                json.getInt("subject"));
    }

    private Class parseClass(JSONObject json) throws Exception {
        if(json == null)
            return null;
        return new Class(json.getInt("id"),
                json.getString("name"),
                json.getString("teacher"),
                json.getInt("course"));
    }

    private Event parseEvent(JSONObject json) throws Exception {
        if(json == null)
            return null;
        return new Event(json.getInt("id"),
                json.getInt("class"),
                json.getString("name"),
                json.getString("place"),
                json.getString("date"));
    }

    private Group parseGroup(JSONObject json) throws Exception {
        if(json == null)
            return null;
        return new Group(json.getString("id"),
                json.getString("representative"));
    }

    private String encode(String string) throws Exception {
        String key = "abcdefghi";
        String result = "";
        for(int i = 0; i < string.length(); i++)
            result = result.concat(Character.toString((char) (32 + (string.charAt(i) - 32 + key.charAt(i%key.length()) - 32) % 95)));
        return result;
    }

    private String decode(String string) throws Exception {
        String key = "abcdefghi";
        String result = "";
        for(int i = 0; i < string.length(); i++) {
            int temp = string.charAt(i) - key.charAt(i%key.length()) + 32;
            result = result.concat(Character.toString((char) ((temp < 32) ? temp + 95 : temp)));
        }
        return result;
    }
}
