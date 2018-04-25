package com.example.ilshat.innoattendance.View.Representative;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ilshat.innoattendance.Presenter.Representative.AttendanceManagementEventsPresenter;
import com.example.ilshat.innoattendance.Presenter.Representative.AttendanceManagementPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.View.Edm.EdmMainActivity;

import java.util.ArrayList;


public class AttendanceManagementEventsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String title;

    private AttendanceManagementEventsPresenter presenter;
    private ProgressDialog progressDialog;
    private Group group;

    private ListView listView;
    private ArrayAdapter<Event> adapter;

    public AttendanceManagementEventsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.attendance_management_events, container, false);
        title = getArguments().getString("title");
        getActivity().setTitle(title);

        listView = (ListView) rootView.findViewById(R.id.events_list);
        ArrayList<Event> events = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, events);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(this);

        presenter = new AttendanceManagementEventsPresenter(this, events, group);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            AttendanceManagementStudentsFragment fragment = new AttendanceManagementStudentsFragment();
            fragment.setEvent(adapter.getItem(position));
            fragment.setGroup(group);
            Bundle args = new Bundle();
            String tag = "Attendance";
            args.putString("title", tag);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        } catch (Exception e) {
            Log.v(EdmMainActivity.class.getName(), e.getMessage());
        }
    }

    public void setGroup(Group group) {
        this.group = group;
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
