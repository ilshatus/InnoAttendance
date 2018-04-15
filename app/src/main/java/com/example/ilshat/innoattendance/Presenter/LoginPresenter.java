package com.example.ilshat.innoattendance.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.View.Edm.EdmMainActivity;
import com.example.ilshat.innoattendance.View.LoginActivity;
import com.example.ilshat.innoattendance.View.Representative.ReprMainActivity;
import com.example.ilshat.innoattendance.View.Student.StudentMainActivity;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.*;

public class LoginPresenter {
    private final LoginActivity activity;
    private final Model model;
    private final SharedPreferences settings;

    public LoginPresenter(LoginActivity activity) {
        this.activity = activity;
        settings = activity.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        attemptAutoLogin();
    }

    public void attemptLogin() {
        final TextView emailView = activity.getEmailView();
        final TextView passwordView = activity.getPasswordView();

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(activity.getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            activity.showProgress();
            model.userLogin(email, password, new Model.CompleteCallback() {
                @Override
                public void onComplete(final Boolean success) {
                    activity.hideProgress();
                    if (success != null && success) {
                        successLogin();
                    } else {
                        if (success == null) {
                            Toast toast = Toast.makeText(activity.getApplicationContext(),
                                    "Problems with internet connection", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            passwordView.setError(activity.getString(R.string.error_incorrect_password));
                            passwordView.requestFocus();
                        }
                    }
                }
            });
        }
    }

    private void attemptAutoLogin() {
        if (!settings.getString(AUTH_TOKEN, "").isEmpty()) {
            successLogin();
        }
    }

    private void successLogin() {
        String userType = settings.getString(TYPE, "");
        if (userType.equals("edm")) {
            Intent intent = new Intent(activity, EdmMainActivity.class);
            activity.startActivity(intent);
        } else if (userType.equals("representative")) {
            Intent intent = new Intent(activity, ReprMainActivity.class);
            activity.startActivity(intent);
        } else if (userType.equals("student")) {
            Intent intent = new Intent(activity, StudentMainActivity.class);
            activity.startActivity(intent);
        }
    }

}
