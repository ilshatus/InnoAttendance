package com.example.ilshat.innoattendance.Model;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.RepositoryModel.User;
import com.example.ilshat.innoattendance.RepositoryModel.Group;


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

    private final Database database;
    private final SharedPreferences settings;

    public Model(Database database, SharedPreferences settings) {
        this.database = database;
        this.settings = settings;
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
