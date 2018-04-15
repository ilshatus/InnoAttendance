package com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Class;
import com.example.ilshat.innoattendance.RepositoryModel.Course;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.View.Edm.EducationManagement.ClassManagementFragment;

import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;
import static com.example.ilshat.innoattendance.RepositoryModel.Settings.DATABASE_LINK;

public class ClassManagementPresenter {
    private final ClassManagementFragment fragment;
    private Model model;
    private ArrayList<Class> classes;
    private Course course;

    public ClassManagementPresenter(ClassManagementFragment fragment, ArrayList<Class> classes, Course course) {
        this.fragment = fragment;
        this.classes = classes;
        this.course = course;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        getClasses();
    }

    private void getClasses() {
        fragment.showProgress();
        model.getClasses(course, new Model.GetClassesCallback() {
            @Override
            public void onComplete(ArrayList<Class> _classes) {
                fragment.hideProgress();
                classes.addAll(_classes);
                fragment.updateAdapter();
            }
        });
    }

    public void createClass() {
        final TextView className = fragment.getClassName();
        final TextView teacher = fragment.getTeacher();
        String sClassName = className.getText().toString();
        String sTeacher = teacher.getText().toString();
        String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);


        if (sClassName.isEmpty()) {
            className.setError(fieldIsRequired);
            className.requestFocus();
        } else if (sTeacher.isEmpty()){
            teacher.setError(fieldIsRequired);
            teacher.requestFocus();
        } else {
            fragment.showProgress();
            model.createClass(course, sClassName, sTeacher, new Model.CreateClassCallback() {
                @Override
                public void onComplete(Class _class) {
                    fragment.hideProgress();
                    fragment.hideCreateView();
                    className.setText("");
                    teacher.setText("");
                    className.requestFocus();
                    if (_class != null) {
                        classes.add(0, _class);
                        fragment.updateAdapter();
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Class added", Toast.LENGTH_SHORT);
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
