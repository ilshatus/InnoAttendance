package com.example.ilshat.innoattendance.View.Representative;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ilshat.innoattendance.Presenter.Representative.AttendanceManagementStudentsPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Attendance;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.View.AttendanceListItemAdapter;

import java.util.ArrayList;

public class AttendanceManagementStudentsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String title;
    private Event event;
    private Group group;

    private AttendanceManagementStudentsPresenter presenter;
    private ProgressDialog progressDialog;
    private ListView listView;
    private AttendanceListItemAdapter adapter;

    public AttendanceManagementStudentsFragment() {
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
        listView.setOnItemClickListener(this);

        presenter = new AttendanceManagementStudentsPresenter(this, attendances, group, event);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.markStudent(position);
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
