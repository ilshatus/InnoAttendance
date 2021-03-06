package com.example.ilshat.innoattendance.View.Representative;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement.SubjectManagementPresenter;
import com.example.ilshat.innoattendance.Presenter.Representative.AttendanceManagementPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.RepositoryModel.Subject;
import com.example.ilshat.innoattendance.View.Edm.EdmMainActivity;
import com.example.ilshat.innoattendance.View.Edm.EducationManagement.CourseManagementFragment;

import java.util.ArrayList;


public class AttendanceManagementFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String title;

    private AttendanceManagementPresenter presenter;
    private ProgressDialog progressDialog;

    private ListView listView;
    private ArrayAdapter<Group> adapter;

    public AttendanceManagementFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.attendance_management_groups, container, false);
        title = getArguments().getString("title");
        getActivity().setTitle(title);

        listView = (ListView) rootView.findViewById(R.id.groups_list);
        ArrayList<Group> groups = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, groups);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        presenter = new AttendanceManagementPresenter(this, groups);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            AttendanceManagementEventsFragment fragment = new AttendanceManagementEventsFragment();
            fragment.setGroup(adapter.getItem(position));
            Bundle args = new Bundle();
            String tag = "Events";
            args.putString("title", tag);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, fragment, tag)
                    .addToBackStack(tag)
                    .commit();

            ReprMainActivity mainActivity = (ReprMainActivity) getActivity();
            mainActivity.setupToolbarNavigation();
        } catch (Exception e) {
            Log.v(EdmMainActivity.class.getName(), e.getMessage());
        }
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
