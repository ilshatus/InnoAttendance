//import android.util.Log;
package com.example.ilshat.innoattendance.RepositoryModel;

import okhttp3.*;
import org.json.JSONObject;

import java.util.ArrayList;

public class Database {
    //private static final String CLASS = "Database module";

    private String link;
    private OkHttpClient client;

    public Database(String link) {
        this.link = link;
        client = new OkHttpClient();
    }

    public String login(String email, String password) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "login");
            requestBody.put("email", email);
            requestBody.put("password", password);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) ? json.getString("token") : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;

            return (json != null)
                    ? new User(json.getString("id"),
                    json.getString("name"),
                    json.getString("surname"),
                    json.getString("email"),
                    json.getString("status"))
                    : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;

            return (json != null)
                    ? new User(json.getString("id"),
                    json.getString("name"),
                    json.getString("surname"),
                    json.getString("email"),
                    json.getString("status"))
                    : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            if((json != null) && json.getString("result").equals("OK")) {
                if(json.getString("statusUpdated").equals("updated")){
                    student.setStatus("representative");
                }
                return true;
            }
            return false;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public boolean detachRepresentativeFromGroup(String token, Group group) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "detachRepresentativeFromGroup");
            requestBody.put("token", token);
            requestBody.put("group", group.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public Subject createSubject(String token, String subject) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "createSubject");
            requestBody.put("token", token);
            requestBody.put("subject", subject);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            //System.out.println(response.string());
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null)
                    ? new Subject(json.getInt("id"),
                    json.getString("name"))
                    : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null)
                    ? new Course(json.getInt("id"),
                    json.getString("name"),
                    json.getString("year"),
                    json.getInt("subject"))
                    : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public boolean removeCourse(String token, Course course) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "removeCourse");
            requestBody.put("token", token);
            requestBody.put("course", course.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null)
                    ? new Class(json.getInt("id"),
                    json.getString("name"),
                    json.getString("teacher"),
                    json.getInt("course"))
                    : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public boolean removeClass(String token, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "removeClass");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null)
                    ? new Event(json.getInt("id"),
                    json.getInt("class"),
                    json.getString("name"),
                    json.getString("place"),
                    json.getString("date"))
                    : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public boolean removeEvent(String token, Event event) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "removeEvent");
            requestBody.put("token", token);
            requestBody.put("event", event.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null)
                    ? new Group(json.getString("id"),
                    json.getString("representative"))
                    : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return false;
        }
    }

    public User getUser(String token) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getUser");
            requestBody.put("token", token);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;

            return (json != null)
                    ? new User(json.getString("id"),
                        json.getString("name"),
                        json.getString("surname"),
                        json.getString("email"),
                        json.getString("status"))
                    : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Subject> getListOfSubjects(String token) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfSubjects");
            requestBody.put("token", token);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());
                ArrayList<Subject> result = new ArrayList<Subject>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Subject(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Subject> getListOfSubjectsForRepresentative(String token) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfSubjectsForRepresentative");
            requestBody.put("token", token);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());
                ArrayList<Subject> result = new ArrayList<Subject>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Subject(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Subject> getListOfSubjectsForStudent(String token) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfSubjectsForStudent");
            requestBody.put("token", token);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());
                ArrayList<Subject> result = new ArrayList<Subject>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Subject(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Course> getListOfCourses(String token, Subject subject) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfCourses");
            requestBody.put("token", token);
            requestBody.put("subject", subject.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Course> result = new ArrayList<Course>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Course(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("year"),
                            json.getJSONObject(Integer.toString(i)).getInt("subject")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Course> getListOfCoursesForRepresentative(String token, Subject subject) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfCoursesForRepresentative");
            requestBody.put("token", token);
            requestBody.put("subject", subject.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Course> result = new ArrayList<Course>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Course(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("year"),
                            json.getJSONObject(Integer.toString(i)).getInt("subject")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Course> getListOfCoursesForStudent(String token, Subject subject) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfCoursesForStudent");
            requestBody.put("token", token);
            requestBody.put("subject", subject.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Course> result = new ArrayList<Course>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Course(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("year"),
                            json.getJSONObject(Integer.toString(i)).getInt("subject")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Class> getListOfClasses(String token, Course course) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfClasses");
            requestBody.put("token", token);
            requestBody.put("course", course.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Class> result = new ArrayList<Class>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Class(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("teacher"),
                            json.getJSONObject(Integer.toString(i)).getInt("course")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Class> getListOfClassesForRepresentative(String token, Course course) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfClassesForRepresentative");
            requestBody.put("token", token);
            requestBody.put("course", course.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Class> result = new ArrayList<Class>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Class(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("teacher"),
                            json.getJSONObject(Integer.toString(i)).getInt("course")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Class> getListOfClassesForStudent(String token, Course course) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfClassesForStudent");
            requestBody.put("token", token);
            requestBody.put("course", course.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Class> result = new ArrayList<Class>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Class(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("teacher"),
                            json.getJSONObject(Integer.toString(i)).getInt("course")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Group> getListOfGroups(String token, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfGroups");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Group> result = new ArrayList<Group>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Group(json.getJSONObject(Integer.toString(i)).getString("id"),
                            json.getJSONObject(Integer.toString(i)).getString("representative")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Group> getListOfGroupsForRepresentative(String token, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfGroupsForRepresentative");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Group> result = new ArrayList<Group>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Group(json.getJSONObject(Integer.toString(i)).getString("id"),
                            json.getJSONObject(Integer.toString(i)).getString("representative")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Group> getListOfGroupsForStudent(String token, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfGroupsForStudent");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Group> result = new ArrayList<Group>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Group(json.getJSONObject(Integer.toString(i)).getString("id"),
                            json.getJSONObject(Integer.toString(i)).getString("representative")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<User> getListOfStudents(String token) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfStudents");
            requestBody.put("token", token);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<User> result = new ArrayList<User>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new User(json.getJSONObject(Integer.toString(i)).getString("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("surname"),
                            json.getJSONObject(Integer.toString(i)).getString("email"),
                            json.getJSONObject(Integer.toString(i)).getString("status")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<User> getListOfStudents(String token, Group group) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfStudentsOfGroup");
            requestBody.put("token", token);
            requestBody.put("group", group.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<User> result = new ArrayList<User>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new User(json.getJSONObject(Integer.toString(i)).getString("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("surname"),
                            json.getJSONObject(Integer.toString(i)).getString("email"),
                            json.getJSONObject(Integer.toString(i)).getString("status")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<Event> getListOfEvents(String token, Class classInstance) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getListOfEvents");
            requestBody.put("token", token);
            requestBody.put("class", classInstance.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Event> result = new ArrayList<Event>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Event(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getInt("class"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("place"),
                            json.getJSONObject(Integer.toString(i)).getString("date")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public ArrayList<User> getStudentsAttended(String token, Event event) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getStudentsAttended");
            requestBody.put("token", token);
            requestBody.put("event", event.getId());

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<User> result = new ArrayList<User>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new User(json.getJSONObject(Integer.toString(i)).getString("id"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("surname"),
                            json.getJSONObject(Integer.toString(i)).getString("email"),
                            json.getJSONObject(Integer.toString(i)).getString("status")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();
            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;
            return (json != null) && json.getString("result").equals("OK");
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
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

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            if(response != null) {
                JSONObject json = new JSONObject(response.string());

                ArrayList<Event> result = new ArrayList<Event>();
                for(int i = 0; i < json.length(); i++) {
                    result.add(new Event(json.getJSONObject(Integer.toString(i)).getInt("id"),
                            json.getJSONObject(Integer.toString(i)).getInt("class"),
                            json.getJSONObject(Integer.toString(i)).getString("name"),
                            json.getJSONObject(Integer.toString(i)).getString("place"),
                            json.getJSONObject(Integer.toString(i)).getString("date")));
                }
                return result;
            }
            return null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public User getUser(String token, String fullName) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getUserByName");
            requestBody.put("token", token);
            requestBody.put("fullName", fullName);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;

            return (json != null)
                    ? new User(json.getString("id"),
                    json.getString("name"),
                    json.getString("surname"),
                    json.getString("email"),
                    json.getString("status"))
                    : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }

    public Group getGroup(String token, String name) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("do", "getGroupByName");
            requestBody.put("token", token);
            requestBody.put("name", name);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());
            Request request = new Request.Builder().url(link).post(body).build();
            ResponseBody response = client.newCall(request).execute().body();

            JSONObject json = (response != null) ? new JSONObject(response.string()) : null;

            return (json != null)
                    ? new Group(json.getString("id"),
                    json.getString("representative"))
                    : null;
        } catch(Exception e) {
            //Log.v(CLASS, e.getMessage());
            return null;
        }
    }
}
