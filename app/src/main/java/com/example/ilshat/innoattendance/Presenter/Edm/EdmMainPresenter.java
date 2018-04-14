package com.example.ilshat.innoattendance.Presenter.Edm;

import android.content.SharedPreferences;

import com.example.ilshat.innoattendance.View.Edm.EdmMainActivity;

public class EdmMainPresenter {
    private final EdmMainActivity view;
    private final SharedPreferences settings;

    public EdmMainPresenter(EdmMainActivity view, SharedPreferences settings) {
        this.view = view;
        this.settings = settings;
    }

    public void logOut() {

    }
}
