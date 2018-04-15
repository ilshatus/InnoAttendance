package com.example.ilshat.innoattendance.Presenter.Representative;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.View.Representative.ReprMainActivity;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;

public class ReprMainPresenter {
    private final ReprMainActivity activity;
    private final Model model;

    public ReprMainPresenter(ReprMainActivity activity) {
        this.activity = activity;
        SharedPreferences settings = activity.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        setHeader();
    }

    public void logOut() {
        model.userLogout(new Model.CompleteCallback() {
            @Override
            public void onComplete(Boolean success) {
                activity.finish();
            }
        });
    }

    private void setHeader() {
        final View view = activity.getHeaderView();
        model.getHeaderInfo(new Model.GetHeaderInfoCallback() {
            @Override
            public void onComplete(String userName, String email) {
                TextView textView = (TextView) view.findViewById(R.id.nav_header_name);
                textView.setText(userName);
                textView = (TextView) view.findViewById(R.id.nav_header_email);
                textView.setText(email);
            }
        });
    }
}
