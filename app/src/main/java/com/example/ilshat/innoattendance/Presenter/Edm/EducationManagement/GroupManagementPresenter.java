package com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Class;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.View.Edm.EducationManagement.EventManagementFragment;
import com.example.ilshat.innoattendance.View.Edm.EducationManagement.GroupManagementFragment;

import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;
import static com.example.ilshat.innoattendance.RepositoryModel.Settings.DATABASE_LINK;

public class GroupManagementPresenter {
    private final GroupManagementFragment fragment;
    private Model model;
    private ArrayList<Group> groups;
    private Class _class;

    public GroupManagementPresenter(GroupManagementFragment fragment, ArrayList<Group> groups, Class _class) {
        this.fragment = fragment;
        this.groups = groups;
        this._class = _class;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        getGroups();
    }

    private void getGroups() {
        fragment.showProgress();
        model.getGroups(_class, new Model.GetGroupsCallback() {
            @Override
            public void onComplete(ArrayList<Group> _groups) {
                fragment.hideProgress();
                groups.addAll(_groups);
                fragment.updateAdapter();
            }
        });
    }

    public void createGroup() {
        final TextView groupName = fragment.getGroupName();
        String sGroupName = groupName.getText().toString();

        String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);


        if (sGroupName.isEmpty()) {
            groupName.setError(fieldIsRequired);
        } else {
            fragment.showProgress();
            model.createGroup(_class, sGroupName,
                    new Model.CreateGroupCallback() {
                        @Override
                        public void onComplete(Group group) {
                            fragment.hideProgress();
                            fragment.hideCreateView();
                            groupName.setText("");
                            if (group != null) {
                                groups.add(0, group);
                                fragment.updateAdapter();
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
}
