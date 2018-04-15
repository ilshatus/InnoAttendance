package com.example.ilshat.innoattendance.Presenter.Edm.UserManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.RepositoryModel.User;
import com.example.ilshat.innoattendance.View.Edm.UserManagement.RepresentativeGroupsFragment;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;
import static com.example.ilshat.innoattendance.RepositoryModel.Settings.DATABASE_LINK;


public class RepresentativeGroupsPresenter {
    private RepresentativeGroupsFragment fragment;
    private Model model;
    private User representative = null;
    private Group group = null;

    public RepresentativeGroupsPresenter(RepresentativeGroupsFragment fragment) {
        this.fragment = fragment;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
    }

    public void setRepresentative(User representative) {
        this.representative = representative;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void findRepresentative() {
        final TextView representativeName = fragment.getRepresentativeName();
        String sRepresentativeName = representativeName.getText().toString();
        final String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);
        final String notFound = fragment.getActivity().getString(R.string.error_not_found);
        setRepresentative(null);
        if (sRepresentativeName.isEmpty()) {
            representativeName.setError(fieldIsRequired);
        } else {
            model.getUser(sRepresentativeName, new Model.GetUserCallback() {
                @Override
                public void onComplete(User user) {
                    if (user != null) {
                        setRepresentative(user);
                    } else {
                        representativeName.setError(notFound);
                    }
                }
            });
        }
    }

    public void findGroup() {
        final TextView groupName = fragment.getGroupName();
        String sGroupName = groupName.getText().toString();
        final String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);
        final String notFound = fragment.getActivity().getString(R.string.error_not_found);
        setGroup(null);
        if (sGroupName.isEmpty()) {
            groupName.setError(fieldIsRequired);
        } else {
            model.getGroup(sGroupName, new Model.GetGroupCallback() {
                @Override
                public void onComplete(Group group) {
                    if (group == null) {
                        groupName.setError(notFound);
                    } else {
                        setGroup(group);
                    }
                }
            });
        }
    }

    public void addToGroup() {
        if (representative == null || group == null) {
            findGroup();
            findRepresentative();
        }
        if (representative != null && group != null) {
            fragment.showProgress();
            model.addRepresentativeToGroup(representative, group, new Model.CompleteCallback() {
                @Override
                public void onComplete(Boolean success) {
                    fragment.hideProgress();
                    TextView groupName = fragment.getGroupName();
                    groupName.setText("");
                    groupName.requestFocus();
                    group = null;
                    if (success != null && success) {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Group assigned", Toast.LENGTH_SHORT);
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

    public void deleteFromGroup() {
        if (representative == null || group == null) {
            findGroup();
            findRepresentative();
        }
        if (representative != null && group != null) {
            fragment.showProgress();
            model.deleteRepresentativeFromGroup(representative, group, new Model.CompleteCallback() {
                @Override
                public void onComplete(Boolean success) {
                    fragment.hideProgress();
                    TextView groupName = fragment.getGroupName();
                    groupName.setText("");
                    groupName.requestFocus();
                    group = null;
                    if (success != null && success) {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Group de-assigned", Toast.LENGTH_SHORT);
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
