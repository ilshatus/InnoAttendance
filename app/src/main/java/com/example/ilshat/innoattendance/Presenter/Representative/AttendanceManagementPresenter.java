package com.example.ilshat.innoattendance.Presenter.Representative;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.View.Representative.AttendanceManagementFragment;

import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;

public class AttendanceManagementPresenter {
    private final AttendanceManagementFragment fragment;
    private Model model;
    private ArrayList<Group> groups;

    public AttendanceManagementPresenter(AttendanceManagementFragment fragment, ArrayList<Group> groups) {
        this.fragment = fragment;
        this.groups = groups;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        getGroups();
    }

    private void getGroups() {
        fragment.showProgress();
        model.getGroups(new Model.GetGroupsCallback() {
            @Override
            public void onComplete(ArrayList<Group> _groups) {
                fragment.hideProgress();
                groups.addAll(_groups);
                fragment.updateAdapter();
            }
        });
    }
}
