package com.example.ilshat.innoattendance.View.Common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ilshat.innoattendance.Presenter.Common.StatisticsManagementsStudentsPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Attendance;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.View.AttendanceListItemAdapter;

import java.util.ArrayList;

public class StatisticsManagementStudentsFragment extends Fragment {
    private String title;
    private Event event;
    private Group group;

    private StatisticsManagementsStudentsPresenter presenter;
    private ProgressDialog progressDialog;
    private ListView listView;
    private AttendanceListItemAdapter adapter;

    public StatisticsManagementStudentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.attendance_management_students, container, false);
        title = getArguments().getString("title");
        getActivity().setTitle(title);

        listView = (ListView) rootView.findViewById(R.id.students_list);
        ArrayList<Attendance> attendances = new ArrayList<>();
        adapter = new AttendanceListItemAdapter(getContext(), attendances);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        presenter = new StatisticsManagementsStudentsPresenter(this, attendances, group, event);

        return rootView;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void showProgress() {
        progressDialog = ProgressDialog.show(getContext(), "", "Please wait");
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
