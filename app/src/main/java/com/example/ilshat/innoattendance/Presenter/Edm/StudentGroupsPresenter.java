package com.example.ilshat.innoattendance.Presenter.Edm;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.RepositoryModel.User;
import com.example.ilshat.innoattendance.View.Edm.StudentGroupsFragment;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;
import static com.example.ilshat.innoattendance.RepositoryModel.Settings.DATABASE_LINK;


public class StudentGroupsPresenter {
    private StudentGroupsFragment fragment;
    private Model model;
    private User student = null;
    private Group group = null;

    public StudentGroupsPresenter(StudentGroupsFragment fragment) {
        this.fragment = fragment;
        Database database = new Database(DATABASE_LINK);
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(database, settings);
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void findStudent() {
        final TextView studentName = fragment.getStudentName();
        String sStudentName = studentName.getText().toString();
        final String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);
        final String notFound = fragment.getActivity().getString(R.string.error_not_found);
        setStudent(null);
        if (sStudentName.isEmpty()) {
            studentName.setError(fieldIsRequired);
        } else {
            model.getUser(sStudentName, new Model.GetUserCallback() {
                @Override
                public void onComplete(User user) {
                    if (user == null) {
                        studentName.setError(notFound);
                    } else {
                        setStudent(user);
                    }
                }
            });
        }
    }

    public void findGroup() {
        final TextView groupName = fragment.getGroupName();
        String sGroupName = groupName.getText().toString();
        final String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);
        final String notFound = fragment.getActivity().getString(R.string.error_not_found);
        setGroup(null);
        if (sGroupName.isEmpty()) {
            groupName.setError(fieldIsRequired);
        } else {
            model.getGroup(sGroupName, new Model.GetGroupCallback() {
                @Override
                public void onComplete(Group group) {
                    if (group == null) {
                        groupName.setError(notFound);
                    } else {
                        setGroup(group);
                    }
                }
            });
        }
    }

    public void addToGroup() {
        if (student == null || group == null) {
            findGroup();
            findStudent();
        }
        if (student != null && group != null) {
            fragment.showProgress();
            model.addStudentToGroup(student, group, new Model.CompleteCallback() {
                @Override
                public void onComplete(Boolean success) {
                    fragment.hideProgress();
                    TextView groupName = fragment.getGroupName();
                    groupName.setText("");
                    groupName.requestFocus();
                    group = null;
                    if (success != null && success) {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Group added", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Some error happened", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
    }

    public void deleteFromGroup() {
        if (student == null || group == null) {
            findGroup();
            findStudent();
        }
        if (student != null && group != null) {
            fragment.showProgress();
            model.deleteStudentFromGroup(student, group, new Model.CompleteCallback() {
                @Override
                public void onComplete(Boolean success) {
                    fragment.hideProgress();
                    TextView groupName = fragment.getGroupName();
                    groupName.setText("");
                    groupName.requestFocus();
                    group = null;
                    if (success != null && success) {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Group deleted", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Some error happened", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
            });
        }
    }
}
