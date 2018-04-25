package com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.View.Edm.EducationManagement.CreateGroupFragment;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;

public class CreateGroupPresenter {
    private CreateGroupFragment fragment;
    private Model model;


    public CreateGroupPresenter(CreateGroupFragment fragment) {
        this.fragment = fragment;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
    }

    public void createGroup() {
        final TextView groupName = fragment.getGroupName();
        String sGroupName = groupName.getText().toString();

        String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);

        if (sGroupName.isEmpty()) {
            groupName.setError(fieldIsRequired);
            groupName.requestFocus();
        } else {
            fragment.showProgress();
            model.createGroup(sGroupName, new Model.CreateGroupCallback() {
                @Override
                public void onComplete(Group group) {
                    fragment.hideProgress();
                    groupName.setText("");
                    groupName.requestFocus();
                    if (group != null) {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Group created", Toast.LENGTH_SHORT);
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
