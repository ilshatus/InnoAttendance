package com.example.ilshat.innoattendance.Presenter.Edm.UserManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.RepositoryModel.User;
import com.example.ilshat.innoattendance.View.Edm.UserManagement.CreateUserFragment;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;
import static com.example.ilshat.innoattendance.RepositoryModel.Settings.DATABASE_LINK;

public class CreateUserPresenter {
    private CreateUserFragment fragment;
    private Model model;


    public CreateUserPresenter(CreateUserFragment fragment) {
        this.fragment = fragment;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
    }

    public void addUser() {
        final TextView userId = fragment.getUserId();
        final TextView userName = fragment.getUserName();
        final TextView userSurname = fragment.getUserSurname();
        final Spinner userStatus = fragment.getUserStatus();
        final TextView userEmail = fragment.getUserEmail();
        final TextView userPassword = fragment.getUserPassword();

        String sUserId = userId.getText().toString();
        String sUserName = userName.getText().toString();
        String sUserSurname = userSurname.getText().toString();
        String sUserEmail = userEmail.getText().toString();
        String sUserStatus = userStatus.getSelectedItem().toString();
        String sUserPassword = userPassword.getText().toString();

        View focus = null;
        String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);

        if (sUserId.isEmpty()) {
            focus = userId;
            userId.setError(fieldIsRequired);
        } else if (sUserName.isEmpty()) {
            focus = userName;
            userName.setError(fieldIsRequired);
        } else if (sUserSurname.isEmpty()) {
            focus = userSurname;
            userSurname.setError(fieldIsRequired);
        } else if (sUserEmail.isEmpty()) {
            focus = userEmail;
            userEmail.setError(fieldIsRequired);
        }

        if (focus == null) {

            User user = new User(sUserId, sUserName, sUserSurname, sUserEmail, sUserStatus);
            user.setPassword(sUserPassword);
            fragment.showProgress();
            model.addUser(user, new Model.CompleteCallback() {
                @Override
                public void onComplete(Boolean success) {
                    fragment.hideProgress();
                    userId.setText("");
                    userName.setText("");
                    userSurname.setText("");
                    userEmail.setText("");
                    userPassword.setText("");
                    userStatus.setSelection(0);
                    userId.requestFocus();

                    if (success != null && success) {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "User created", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Some error happened", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
            });
        } else {
            focus.requestFocus();
        }
    }
}
