package com.example.ilshat.innoattendance.View.Edm.UserManagement;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ilshat.innoattendance.Presenter.Edm.UserManagement.CreateUserPresenter;
import com.example.ilshat.innoattendance.R;


public class CreateUserFragment extends Fragment {

    private CreateUserPresenter presenter;

    private TextView userId;
    private TextView userName;
    private TextView userSurname;
    private Spinner userStatus;
    private TextView userEmail;
    private TextView userPassword;

    private ProgressDialog progressDialog;

    public CreateUserFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_user, container, false);
        getActivity().setTitle(getArguments().getString("title"));

        presenter = new CreateUserPresenter(this);

        userId = (TextView) rootView.findViewById(R.id.user_id);
        userName = (TextView) rootView.findViewById(R.id.user_name);
        userSurname = (TextView) rootView.findViewById(R.id.user_surname);
        userStatus = (Spinner) rootView.findViewById(R.id.user_status);
        userEmail = (TextView) rootView.findViewById(R.id.user_email);
        userPassword = (TextView) rootView.findViewById(R.id.user_password) ;
        userStatus.setSelection(0);

        Button button = (Button) rootView.findViewById(R.id.user_create_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addUser();
            }
        });
        return rootView;
    }

    public CreateUserPresenter getPresenter() {
        return presenter;
    }

    public TextView getUserId() {
        return userId;
    }

    public TextView getUserName() {
        return userName;
    }

    public TextView getUserSurname() {
        return userSurname;
    }

    public Spinner getUserStatus() {
        return userStatus;
    }

    public TextView getUserEmail() {
        return userEmail;
    }

    public TextView getUserPassword() {
        return userPassword;
    }

    public void showProgress() {
        progressDialog = ProgressDialog.show(getContext(), "", "Please wait");
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
