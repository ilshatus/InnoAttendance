package com.example.ilshat.innoattendance.Presenter.Representative;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.View.Representative.AttendanceManagementEventsFragment;
import com.example.ilshat.innoattendance.View.Representative.AttendanceManagementFragment;

import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;


public class AttendanceManagementEventsPresenter {
    private final AttendanceManagementEventsFragment fragment;
    private Model model;
    private ArrayList<Event> events;
    private Group group;

    public AttendanceManagementEventsPresenter(AttendanceManagementEventsFragment fragment, ArrayList<Event> events, Group group) {
        this.fragment = fragment;
        this.events = events;
        this.group = group;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        getEvents();
    }

    private void getEvents() {
        fragment.showProgress();
        model.getEvents(group, new Model.GetEventsCallback() {
            @Override
            public void onComplete(ArrayList<Event> _events) {
                fragment.hideProgress();
                events.addAll(_events);
                fragment.updateAdapter();
            }
        });
    }
}
