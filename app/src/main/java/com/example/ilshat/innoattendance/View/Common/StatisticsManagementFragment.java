package com.example.ilshat.innoattendance.View.Common;


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
import android.widget.Button;
import android.widget.Spinner;

import com.example.ilshat.innoattendance.Presenter.Common.StatisticsManagementPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Class;
import com.example.ilshat.innoattendance.RepositoryModel.Course;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.RepositoryModel.Subject;
import com.example.ilshat.innoattendance.View.Edm.EdmMainActivity;
import com.example.ilshat.innoattendance.View.Representative.AttendanceManagementStudentsFragment;
import com.example.ilshat.innoattendance.View.Representative.ReprMainActivity;
import com.example.ilshat.innoattendance.View.Student.StudentMainActivity;

import java.util.ArrayList;

public class StatisticsManagementFragment extends Fragment {
    private String title;

    private Spinner subject;
    private Spinner course;
    private Spinner nClass;
    private Spinner group;
    private Spinner event;

    private Button button;

    private ArrayAdapter<Subject> subjectsAdapter;
    private ArrayAdapter<Course> coursesAdapter;
    private ArrayAdapter<Class> classesAdapter;
    private ArrayAdapter<Group> groupsAdapter;
    private ArrayAdapter<Event> eventsAdapter;

    private ProgressDialog progressDialog;
    private StatisticsManagementPresenter presenter;

    public StatisticsManagementFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.statistics_management, container, false);
        title = getArguments().getString("title");
        getActivity().setTitle(title);

        subject = (Spinner) rootView.findViewById(R.id.subject);
        course = (Spinner) rootView.findViewById(R.id.course);
        nClass = (Spinner) rootView.findViewById(R.id.nclass);
        group = (Spinner) rootView.findViewById(R.id.group);
        event = (Spinner) rootView.findViewById(R.id.event);

        subjectsAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, new ArrayList<Subject>());
        coursesAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, new ArrayList<Course>());
        classesAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, new ArrayList<Class>());
        groupsAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, new ArrayList<Group>());
        eventsAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, new ArrayList<Event>());

        subject.setAdapter(subjectsAdapter);
        course.setAdapter(coursesAdapter);
        nClass.setAdapter(classesAdapter);
        group.setAdapter(groupsAdapter);
        event.setAdapter(eventsAdapter);

        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.getCourses(subjectsAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.getClasses(coursesAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.getGroups(classesAdapter.getItem(position));
                presenter.getEvents(classesAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        button = (Button) rootView.findViewById(R.id.show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int position1 = group.getSelectedItemPosition();
                    int position2 = event.getSelectedItemPosition();
                    StatisticsManagementStudentsFragment fragment = new StatisticsManagementStudentsFragment();
                    fragment.setGroup(groupsAdapter.getItem(position1));
                    fragment.setEvent(eventsAdapter.getItem(position2));
                    Bundle args = new Bundle();
                    String tag = "Attendance";
                    args.putString("title", tag);
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_main, fragment, tag)
                            .addToBackStack(tag)
                            .commit();

                    if (getActivity() instanceof ReprMainActivity) {
                        ((ReprMainActivity) getActivity()).setupToolbarNavigation();
                    } else if (getActivity() instanceof StudentMainActivity) {
                        ((StudentMainActivity) getActivity()).setupToolbarNavigation();
                    } else {
                        ((EdmMainActivity) getActivity()).setupToolbarNavigation();
                    }
                } catch (Exception e) {
                    Log.v(EdmMainActivity.class.getName(), e.getMessage());
                }
            }
        });

        presenter = new StatisticsManagementPresenter(this);

        return rootView;
    }

    public ArrayAdapter<Subject> getSubjectsAdapter() {
        return subjectsAdapter;
    }

    public ArrayAdapter<Course> getCoursesAdapter() {
        return coursesAdapter;
    }

    public ArrayAdapter<Class> getClassesAdapter() {
        return classesAdapter;
    }

    public ArrayAdapter<Group> getGroupsAdapter() {
        return groupsAdapter;
    }

    public ArrayAdapter<Event> getEventsAdapter() {
        return eventsAdapter;
    }

    public Spinner getSubject() {
        return subject;
    }

    public Spinner getCourse() {
        return course;
    }

    public Spinner getnClass() {
        return nClass;
    }

    public Spinner getGroup() {
        return group;
    }

    public Spinner getEvent() {
        return event;
    }

    public Button getButton() {
        return button;
    }

    public void showView(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    public void showButton() {
        if (getGroup().getVisibility() == View.VISIBLE &&
                getEvent().getVisibility() == View.VISIBLE)
            showView(getButton(), true);
    }


    public void showProgress() {
        hideProgress();
        progressDialog = ProgressDialog.show(getContext(), "", "Please wait");
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
