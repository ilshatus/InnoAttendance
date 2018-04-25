package com.example.ilshat.innoattendance.Presenter.Common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.RepositoryModel.Attendance;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.View.Common.StatisticsManagementStudentsFragment;

import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;

public class StatisticsManagementsStudentsPresenter {
    private StatisticsManagementStudentsFragment fragment;
    private ArrayList<Attendance> attendances;
    private Event event;
    private Group group;
    private Model model;

    public StatisticsManagementsStudentsPresenter(StatisticsManagementStudentsFragment fragment, ArrayList<Attendance> attendances, Group group, Event event) {
        this.fragment = fragment;
        this.attendances = attendances;
        this.group = group;
        this.event = event;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        getAttendances();
    }

    private void getAttendances() {
        fragment.showProgress();
        model.getAttendances(group, event, new Model.GetAttendancesCallback() {
            @Override
            public void onComplete(ArrayList<Attendance> _attendances) {
                fragment.hideProgress();
                attendances.addAll(_attendances);
                fragment.updateAdapter();
            }
        });
    }

}
