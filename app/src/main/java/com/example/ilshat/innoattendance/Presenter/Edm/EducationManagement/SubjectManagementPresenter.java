package com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.RepositoryModel.Subject;
import com.example.ilshat.innoattendance.View.Edm.EducationManagement.SubjectManagementFragment;

import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;
import static com.example.ilshat.innoattendance.RepositoryModel.Settings.DATABASE_LINK;


public class SubjectManagementPresenter {
    private final SubjectManagementFragment fragment;
    private Model model;
    private ArrayList<Subject> subjects;

    public SubjectManagementPresenter(SubjectManagementFragment fragment, ArrayList<Subject> subjects) {
        this.fragment = fragment;
        this.subjects = subjects;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        getSubjects();
    }

    private void getSubjects() {
        fragment.showProgress();
        model.getSubjects(new Model.GetSubjectsCallback() {
            @Override
            public void onComplete(ArrayList<Subject> _subjects) {
                fragment.hideProgress();
                subjects.addAll(_subjects);
                fragment.updateAdapter();
            }
        });
    }

    public void createSubject() {
        final TextView subjectName = fragment.getSubjectName();
        String sSubjectName = subjectName.getText().toString();
        String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);
        if (sSubjectName.isEmpty()) {
            subjectName.setError(fieldIsRequired);
        } else {
            fragment.showProgress();
            model.createSubject(sSubjectName, new Model.CreateSubjectCallback() {
                @Override
                public void onComplete(Subject subject) {
                    fragment.hideProgress();
                    fragment.hideCreateView();
                    subjectName.setText("");
                    if (subject != null) {
                        subjects.add(0, subject);
                        fragment.updateAdapter();
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Subject added", Toast.LENGTH_SHORT);
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
