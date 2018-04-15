package com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Course;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.RepositoryModel.Subject;
import com.example.ilshat.innoattendance.View.Edm.EducationManagement.CourseManagementFragment;

import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;
import static com.example.ilshat.innoattendance.RepositoryModel.Settings.DATABASE_LINK;


public class CourseManagementPresenter {
    private final CourseManagementFragment fragment;
    private Model model;
    private ArrayList<Course> courses;
    private Subject subject;

    public CourseManagementPresenter(CourseManagementFragment fragment, ArrayList<Course> courses, Subject subject) {
        this.fragment = fragment;
        this.courses = courses;
        this.subject = subject;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        getCourses();
    }

    private void getCourses() {
        fragment.showProgress();
        model.getCourses(subject, new Model.GetCoursesCallback() {
            @Override
            public void onComplete(ArrayList<Course> _courses) {
                fragment.hideProgress();
                courses.addAll(_courses);
                fragment.updateAdapter();
            }
        });
    }

    public void createCourse() {
        final TextView courseName = fragment.getCourseName();
        final TextView courseYear = fragment.getCourseYear();
        String sCourseName = courseName.getText().toString();
        String sCourseYear = courseYear.getText().toString();
        String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);


        if (sCourseName.isEmpty()) {
            courseName.setError(fieldIsRequired);
            courseName.requestFocus();
        } else if (sCourseYear.isEmpty()){
            courseYear.setError(fieldIsRequired);
            courseName.requestFocus();
        } else {
            fragment.showProgress();
            model.createCourse(subject, sCourseName, sCourseYear, new Model.CreateCourseCallback() {
                @Override
                public void onComplete(Course course) {
                    fragment.hideProgress();
                    fragment.hideCreateView();
                    courseName.setText("");
                    courseYear.setText("");
                    courseName.requestFocus();
                    if (course != null) {
                        courses.add(0, course);
                        fragment.updateAdapter();
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Course added", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Some error happened", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
    }

}
