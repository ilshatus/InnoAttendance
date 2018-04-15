package com.example.ilshat.innoattendance.Model;


import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.ilshat.innoattendance.RepositoryModel.Class;
import com.example.ilshat.innoattendance.RepositoryModel.Course;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.RepositoryModel.Subject;
import com.example.ilshat.innoattendance.RepositoryModel.User;
import com.example.ilshat.innoattendance.RepositoryModel.Group;


import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.*;

public class Model {
    public interface CompleteCallback {
        void onComplete(Boolean success);
    }

    public interface GetGroupCallback {
        void onComplete(Group groups);
    }

    public interface GetUserCallback {
        void onComplete(User user);
    }

    public interface GetHeaderInfoCallback {
        void onComplete(String userName, String email);
    }

    public interface GetSubjectsCallback {
        void onComplete(ArrayList<Subject> subjects);
    }

    public interface CreateSubjectCallback {
        void onComplete(Subject subject);
    }

    public interface GetCoursesCallback {
        void onComplete(ArrayList<Course> courses);
    }

    public interface CreateCourseCallback {
        void onComplete(Course course);
    }

    public interface GetClassesCallback {
        void onComplete(ArrayList<Class> classes);
    }

    public interface CreateClassCallback {
        void onComplete(Class _class);
    }

    public interface GetEventsCallback {
        void onComplete(ArrayList<Event> events);
    }

    public interface CreateEventCallback {
        void onComplete(Event event);
    }

    public interface GetGroupsCallback {
        void onComplete(ArrayList<Group> groups);
    }

    public interface CreateGroupCallback {
        void onComplete(Group group);
    }

    private final Database database;
    private final SharedPreferences settings;

    public Model(SharedPreferences settings) {
        this.database = Database.getInstance();
        this.settings = settings;
    }

    public void userLogout(CompleteCallback callback) {
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
        callback.onComplete(true);
    }

    public void getHeaderInfo(GetHeaderInfoCallback callback) {
        callback.onComplete(settings.getString(USERNAME, ""), settings.getString(EMAIL, ""));
    }

    public void userLogin(String email, String password, CompleteCallback callback) {
        UserLoginTask userLoginTask = new UserLoginTask(email, password, callback);
        userLoginTask.execute();
    }

    public void addUser(User user, CompleteCallback callback) {
        AddUserTask addUserTask = new AddUserTask(callback, user);
        addUserTask.execute();
    }

    public void getUser(String fullName, GetUserCallback callback) {
        GetUserTask getUserTask = new GetUserTask(fullName, callback);
        getUserTask.execute();
    }

    public void getGroup(String name, GetGroupCallback callback) {
        GetGroupTask getGroupTask = new GetGroupTask(name, callback);
        getGroupTask.execute();
    }

    public void addStudentToGroup(User user, Group group, CompleteCallback callback) {
        AddStudentToGroupTask addStudentToGroupTask =
                new AddStudentToGroupTask(user, group, callback);
        addStudentToGroupTask.execute();
    }

    public void deleteStudentFromGroup(User user, Group group, CompleteCallback callback) {
        DeleteStudentFromGroupTask deleteStudentFromGroupTask =
                new DeleteStudentFromGroupTask(user, group, callback);
        deleteStudentFromGroupTask.execute();
    }

    public void addRepresentativeToGroup(User user, Group group, CompleteCallback callback) {
        AddRepresentativeToGroupTask addRepresentativeToGroupTask = new AddRepresentativeToGroupTask(user,
                group, callback);
        addRepresentativeToGroupTask.execute();
    }

    public void deleteRepresentativeFromGroup(User user, Group group, CompleteCallback callback) {
        DeleteRepresentativeFromGroupTask task = new DeleteRepresentativeFromGroupTask(user, group, callback);
        task.execute();
    }

    public void getSubjects(GetSubjectsCallback callback) {
        GetSubjectsTask task = new GetSubjectsTask(callback);
        task.execute();
    }

    public void createSubject(String subjectName, CreateSubjectCallback callback) {
        CreateSubjectTask task = new CreateSubjectTask(subjectName, callback);
        task.execute();
    }

    public void getCourses(Subject subject, GetCoursesCallback callback) {
        GetCoursesTask task = new GetCoursesTask(subject, callback);
        task.execute();
    }

    public void createCourse(Subject subject, String courseName,
                             String courseYear, CreateCourseCallback callback) {
        CreateCourseTask task = new CreateCourseTask(subject, courseName, courseYear, callback);
        task.execute();
    }

    public void getClasses(Course course, GetClassesCallback callback) {
        GetClassesTask task = new GetClassesTask(course, callback);
        task.execute();
    }

    public void createClass(Course course, String className, String teacher, CreateClassCallback callback) {
        CreateClassTask task = new CreateClassTask(course, className, teacher, callback);
        task.execute();
    }

    public void getEvents(Class _class, GetEventsCallback callback) {
        GetEventsTask task = new GetEventsTask(_class, callback);
        task.execute();
    }

    public void createEvent(Class _class, String eventName, String eventPlace,
                            String eventDate, CreateEventCallback callback) {
        CreateEventTask task = new CreateEventTask(_class, eventName, eventPlace, eventDate, callback);
        task.execute();
    }

    public void getGroups(Class _class, GetGroupsCallback callback) {
        GetGroupsTask task = new GetGroupsTask(_class, callback);
        task.execute();
    }

    public void createGroup(Class _class, String groupName, CreateGroupCallback callback) {
        CreateGroupTask task = new CreateGroupTask(_class, groupName, callback);
        task.execute();
    }

    private class CreateGroupTask extends AsyncTask<Void, Void, Group> {
        private final CreateGroupCallback callback;
        private Class _class;
        private String groupName;

        public CreateGroupTask(Class _class, String groupName, CreateGroupCallback callback) {
            this.callback = callback;
            this._class = _class;
            this.groupName = groupName;
        }

        @Override
        protected Group doInBackground(Void... params) {
            return database.createGroupToClass(settings.getString(AUTH_TOKEN, ""),
                    _class, groupName);
        }

        @Override
        protected void onPostExecute(Group group) {
            callback.onComplete(group);
        }
    }

    private class GetGroupsTask extends AsyncTask<Void, Void, ArrayList<Group>> {
        private final GetGroupsCallback callback;
        private Class _class;

        public GetGroupsTask(Class _class, GetGroupsCallback callback) {
            this.callback = callback;
            this._class = _class;
        }

        @Override
        protected ArrayList<Group> doInBackground(Void... params) {
            return database.getListOfGroups(settings.getString(AUTH_TOKEN, ""), _class);
        }

        @Override
        protected void onPostExecute(ArrayList<Group> groups) {
            callback.onComplete(groups);
        }
    }

    private class CreateEventTask extends AsyncTask<Void, Void, Event> {
        private final CreateEventCallback callback;
        private Class _class;
        private String eventName;
        private String eventPlace;
        private String eventDate;

        public CreateEventTask(Class _class, String eventName, String eventPlace,
                               String eventDate, CreateEventCallback callback) {
            this.callback = callback;
            this._class = _class;
            this.eventName = eventName;
            this.eventPlace = eventPlace;
            this.eventDate = eventDate;
        }

        @Override
        protected Event doInBackground(Void... params) {
            return database.addEventToClass(settings.getString(AUTH_TOKEN, ""),
                    _class, eventName, eventPlace, eventDate);
        }

        @Override
        protected void onPostExecute(Event event) {
            callback.onComplete(event);
        }
    }

    private class GetEventsTask extends AsyncTask<Void, Void, ArrayList<Event>> {
        private final GetEventsCallback callback;
        private Class _class;

        public GetEventsTask(Class _class, GetEventsCallback callback) {
            this.callback = callback;
            this._class = _class;
        }

        @Override
        protected ArrayList<Event> doInBackground(Void... params) {
            return database.getListOfEvents(settings.getString(AUTH_TOKEN, ""), _class);
        }

        @Override
        protected void onPostExecute(ArrayList<Event> events) {
            callback.onComplete(events);
        }
    }

    private class CreateClassTask extends AsyncTask<Void, Void, Class> {
        private final CreateClassCallback callback;
        private Course course;
        private String className;
        private String teacher;

        public CreateClassTask(Course course, String className,
                               String teacher, CreateClassCallback callback) {
            this.callback = callback;
            this.course = course;
            this.className = className;
            this.teacher = teacher;
        }

        @Override
        protected Class doInBackground(Void... params) {
            Class _class = database.createClassToCourse(settings.getString(AUTH_TOKEN, ""), course, className);
            if (_class != null) {
                if (database.attachTeacherToClass(settings.getString(AUTH_TOKEN, ""), _class, teacher)) {
                    _class.setTeacher(teacher);
                }
            }
            return _class;
        }

        @Override
        protected void onPostExecute(Class _class) {
            callback.onComplete(_class);
        }
    }

    private class GetClassesTask extends AsyncTask<Void, Void, ArrayList<Class>> {
        private final GetClassesCallback callback;
        private Course course;

        public GetClassesTask(Course course, GetClassesCallback callback) {
            this.callback = callback;
            this.course = course;
        }

        @Override
        protected ArrayList<Class> doInBackground(Void... params) {
            return database.getListOfClasses(settings.getString(AUTH_TOKEN, ""), course);
        }

        @Override
        protected void onPostExecute(ArrayList<Class> classes) {
            callback.onComplete(classes);
        }
    }

    private class CreateCourseTask extends AsyncTask<Void, Void, Course> {
        private final CreateCourseCallback callback;
        private Subject subject;
        private String courseName;
        private String courseYear;

        public CreateCourseTask(Subject subject, String courseName,
                                String courseYear, CreateCourseCallback callback) {
            this.callback = callback;
            this.subject = subject;
            this.courseName = courseName;
            this.courseYear = courseYear;
        }

        @Override
        protected Course doInBackground(Void... params) {
            return database.createCourseToSubject(
                    settings.getString(AUTH_TOKEN, ""), subject, courseName, courseYear);
        }

        @Override
        protected void onPostExecute(Course course) {
            callback.onComplete(course);
        }
    }

    private class GetCoursesTask extends AsyncTask<Void, Void, ArrayList<Course>> {
        private final GetCoursesCallback callback;
        private Subject subject;

        public GetCoursesTask(Subject subject, GetCoursesCallback callback) {
            this.callback = callback;
            this.subject = subject;
        }

        @Override
        protected ArrayList<Course> doInBackground(Void... params) {
            return database.getListOfCourses(settings.getString(AUTH_TOKEN, ""), subject);
        }

        @Override
        protected void onPostExecute(ArrayList<Course> courses) {
            callback.onComplete(courses);
        }
    }

    private class CreateSubjectTask extends AsyncTask<Void, Void, Subject> {
        private final CreateSubjectCallback callback;
        private String subjectName;

        public CreateSubjectTask(String subjectName, CreateSubjectCallback callback) {
            this.callback = callback;
            this.subjectName = subjectName;
        }

        @Override
        protected Subject doInBackground(Void... params) {
            return database.createSubject(settings.getString(AUTH_TOKEN, ""), subjectName);
        }

        @Override
        protected void onPostExecute(Subject subject) {
            callback.onComplete(subject);
        }
    }

    private class GetSubjectsTask extends AsyncTask<Void, Void, ArrayList<Subject>> {
        private final GetSubjectsCallback callback;

        public GetSubjectsTask(GetSubjectsCallback callback) {
            this.callback = callback;
        }

        @Override
        protected ArrayList<Subject> doInBackground(Void... params) {
            return database.getListOfSubjects(settings.getString(AUTH_TOKEN, ""));
        }

        @Override
        protected void onPostExecute(ArrayList<Subject> subjects) {
            callback.onComplete(subjects);
        }
    }

    private class AddStudentToGroupTask extends AsyncTask<Void, Void, Boolean> {
        private final CompleteCallback callback;
        private User user;
        private Group group;

        public AddStudentToGroupTask(User user, Group group, CompleteCallback callback) {
            this.callback = callback;
            this.user = user;
            this.group = group;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return database.addStudentToGroup(settings.getString(AUTH_TOKEN, ""), user, group);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            callback.onComplete(aBoolean);
        }
    }

    private class DeleteStudentFromGroupTask extends AsyncTask<Void, Void, Boolean> {
        private final CompleteCallback callback;
        private User user;
        private Group group;

        public DeleteStudentFromGroupTask(User user, Group group, CompleteCallback callback) {
            this.callback = callback;
            this.user = user;
            this.group = group;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return database.removeStudentFromGroup(settings.getString(AUTH_TOKEN, ""), user, group);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            callback.onComplete(aBoolean);
        }
    }

    private class AddRepresentativeToGroupTask extends AsyncTask<Void, Void, Boolean> {
        private final CompleteCallback callback;
        private User user;
        private Group group;

        public AddRepresentativeToGroupTask(User user, Group group, CompleteCallback callback) {
            this.callback = callback;
            this.user = user;
            this.group = group;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return database.attachRepresentativeToGroup(settings.getString(AUTH_TOKEN, ""), user, group);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            callback.onComplete(aBoolean);
        }
    }

    private class DeleteRepresentativeFromGroupTask extends AsyncTask<Void, Void, Boolean> {
        private final CompleteCallback callback;
        private User user;
        private Group group;

        public DeleteRepresentativeFromGroupTask(User user, Group group, CompleteCallback callback) {
            this.callback = callback;
            this.user = user;
            this.group = group;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (user.getId().equals(group.getRepresentativeId())) {
                return database.detachRepresentativeFromGroup(settings.getString(AUTH_TOKEN, ""), group);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            callback.onComplete(aBoolean);
        }
    }

    private class GetUserTask extends AsyncTask<Void, Void, User> {
        private final GetUserCallback callback;
        private String fullName;

        public GetUserTask(String fullName, GetUserCallback callback) {
            this.callback = callback;
            this.fullName = fullName;
        }

        @Override
        protected User doInBackground(Void... params) {
            return database.getUser(settings.getString(AUTH_TOKEN, ""), fullName);
        }

        @Override
        protected void onPostExecute(User user) {
            if (!user.getId().isEmpty())
                callback.onComplete(user);
            else callback.onComplete(null);
        }
    }

    private class GetGroupTask extends AsyncTask<Void, Void, Group> {
        private final GetGroupCallback callback;
        private String name;

        public GetGroupTask(String name, GetGroupCallback callback) {
            this.callback = callback;
            this.name = name;
        }

        @Override
        protected Group doInBackground(Void... params) {
            return database.getGroup(settings.getString(AUTH_TOKEN, ""), name);
        }

        @Override
        protected void onPostExecute(Group group) {
            if (!group.getId().isEmpty())
                callback.onComplete(group);
            else
                callback.onComplete(null);
        }
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final CompleteCallback callback;
        private final String email;
        private final String password;

        UserLoginTask(String email, String password, CompleteCallback callback) {
            this.email = email;
            this.password = password;
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String token = database.login(email, password);

            if (token == null) {
                return null;
            }
            if (token.isEmpty()) {
                return false;
            }
            User user = database.getUser(token);
            if (user == null) {
                return null;
            }
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(AUTH_TOKEN, token);
            editor.putString(USERNAME, user.getFullName());
            editor.putString(EMAIL, user.getEmail());
            editor.putString(TYPE, user.getStatus());
            editor.putString(ID, user.getId());
            editor.apply();
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            callback.onComplete(success);
        }
    }

    private class AddUserTask extends AsyncTask<Void, Void, Boolean> {
        private final CompleteCallback callback;
        private User user;

        public AddUserTask(CompleteCallback callback, User user) {
            this.callback = callback;
            this.user = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            User rUser = database.createUser(settings.getString(AUTH_TOKEN, ""),
                    user.getId(), user.getName(), user.getSurname(),
                    user.getEmail(), user.getPassword(), user.getStatus());
            return rUser != null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            callback.onComplete(aBoolean);
        }
    }

}
