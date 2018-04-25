package com.example.ilshat.innoattendance.Presenter.Representative;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.RepositoryModel.Attendance;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.RepositoryModel.User;
import com.example.ilshat.innoattendance.View.Representative.AttendanceManagementStudentsFragment;

import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;

public class AttendanceManagementStudentsPresenter {
    private AttendanceManagementStudentsFragment fragment;
    private ArrayList<Attendance> attendances;
    private Event event;
    private Group group;
    private Model model;

    public AttendanceManagementStudentsPresenter(AttendanceManagementStudentsFragment fragment, ArrayList<Attendance> attendances, Group group, Event event) {
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

    public void markStudent(int position) {
        final Attendance attendance = attendances.get(position);
        attendance.setAttended(!attendance.isAttended());
        fragment.showProgress();
        model.setAttendance(event, attendance, new Model.CompleteCallback() {
            @Override
            public void onComplete(Boolean success) {
                fragment.hideProgress();
                if (success != null && success) {
                    fragment.updateAdapter();
                } else {
                    attendance.setAttended(!attendance.isAttended());
                    Toast toast = Toast.makeText(fragment.getContext(),
                            "Some error happened", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
