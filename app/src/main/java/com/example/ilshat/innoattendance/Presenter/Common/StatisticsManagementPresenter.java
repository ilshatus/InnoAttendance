package com.example.ilshat.innoattendance.Presenter.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.RepositoryModel.Class;
import com.example.ilshat.innoattendance.RepositoryModel.Course;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.RepositoryModel.Subject;
import com.example.ilshat.innoattendance.View.Common.StatisticsManagementFragment;

import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;

public class StatisticsManagementPresenter {
    private StatisticsManagementFragment fragment;
    private Model model;
    private boolean getSubjectsWork = false;
    private boolean getCoursesWork = false;
    private boolean getClassesWork = false;
    private boolean getGroupsWork = false;
    private boolean getEventsWork = false;

    public StatisticsManagementPresenter(StatisticsManagementFragment fragment) {
        this.fragment = fragment;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        getSubjects();
    }

    private void getSubjects() {
        if (getSubjectsWork) return;
        getSubjectsWork = true;
        fragment.showProgress();
        model.getSubjects(new Model.GetSubjectsCallback() {
            @Override
            public void onComplete(ArrayList<Subject> subjects) {
                fragment.hideProgress();
                getSubjectsWork = false;
                if (subjects != null && !subjects.isEmpty()) {
                    ArrayAdapter<Subject> adapter = fragment.getSubjectsAdapter();
                    adapter.addAll(subjects);
                    adapter.notifyDataSetChanged();
                    getCourses(adapter.getItem(0));
                } else {
                    Toast toast = Toast.makeText(fragment.getContext(),
                            "Subject not found", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });
    }

    public void getCourses(Subject subject) {
        if (getCoursesWork) return;
        getCoursesWork = true;
        fragment.showProgress();
        fragment.showView(fragment.getCourse(), false);
        fragment.showView(fragment.getnClass(), false);
        fragment.showView(fragment.getGroup(), false);
        fragment.showView(fragment.getEvent(), false);
        fragment.showView(fragment.getButton(), false);
        model.getCourses(subject, new Model.GetCoursesCallback() {
            @Override
            public void onComplete(ArrayList<Course> courses) {
                fragment.hideProgress();
                getCoursesWork = false;
                if (courses != null && !courses.isEmpty()) {
                    ArrayAdapter<Course> adapter = fragment.getCoursesAdapter();
                    adapter.clear();
                    adapter.addAll(courses);
                    adapter.notifyDataSetChanged();
                    fragment.showView(fragment.getCourse(), true);
                    getClasses(adapter.getItem(0));
                } else {
                    Toast toast = Toast.makeText(fragment.getContext(),
                            "Courses not found", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public void getClasses(Course course) {
        if (getClassesWork) return;
        getClassesWork = true;
        fragment.showProgress();
        fragment.getnClass().setSelection(-1);
        fragment.showView(fragment.getnClass(), false);
        fragment.showView(fragment.getGroup(), false);
        fragment.showView(fragment.getEvent(), false);
        fragment.showView(fragment.getButton(), false);
        model.getClasses(course, new Model.GetClassesCallback() {
            @Override
            public void onComplete(ArrayList<Class> classes) {
                fragment.hideProgress();
                getClassesWork = false;
                if (classes != null && !classes.isEmpty()) {
                    ArrayAdapter<Class> adapter = fragment.getClassesAdapter();
                    adapter.clear();
                    adapter.addAll(classes);
                    adapter.notifyDataSetChanged();
                    fragment.showView(fragment.getnClass(), true);
                    getGroups(adapter.getItem(0));
                    getEvents(adapter.getItem(0));
                } else {
                    Toast toast = Toast.makeText(fragment.getContext(),
                            "Classes not found", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });
    }

    public void getGroups(Class _class) {
        if (getGroupsWork) return;
        getGroupsWork = false;
        fragment.showProgress();
        fragment.showView(fragment.getGroup(), false);
        fragment.showView(fragment.getButton(), false);
        model.getGroups(_class, new Model.GetGroupsCallback() {
            @Override
            public void onComplete(ArrayList<Group> groups) {
                fragment.hideProgress();
                getGroupsWork = false;
                if (groups != null && !groups.isEmpty()) {
                    ArrayAdapter<Group> adapter = fragment.getGroupsAdapter();
                    adapter.clear();
                    adapter.addAll(groups);
                    adapter.notifyDataSetChanged();
                    fragment.showView(fragment.getGroup(), true);
                    fragment.showButton();
                } else {
                    Toast toast = Toast.makeText(fragment.getContext(),
                            "Groups not found", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });
    }

    public void getEvents(Class _class) {
        if (getEventsWork) return;
        getEventsWork = true;
        fragment.showProgress();
        fragment.showView(fragment.getEvent(), false);
        fragment.showView(fragment.getButton(), false);
        model.getEvents(_class, new Model.GetEventsCallback() {
            @Override
            public void onComplete(ArrayList<Event> events) {
                fragment.hideProgress();
                getEventsWork = false;
                if (events != null && !events.isEmpty()) {
                    ArrayAdapter<Event> adapter = fragment.getEventsAdapter();
                    adapter.clear();
                    adapter.addAll(events);
                    adapter.notifyDataSetChanged();
                    fragment.showView(fragment.getEvent(), true);
                    fragment.showButton();
                } else {
                    Toast toast = Toast.makeText(fragment.getContext(),
                            "Events not found", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
