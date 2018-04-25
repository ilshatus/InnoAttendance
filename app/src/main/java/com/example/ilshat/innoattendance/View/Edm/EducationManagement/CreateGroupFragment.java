package com.example.ilshat.innoattendance.View.Edm.EducationManagement;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement.CreateGroupPresenter;
import com.example.ilshat.innoattendance.R;

public class CreateGroupFragment extends Fragment {
    private CreateGroupPresenter presenter;

    private TextView groupName;

    private ProgressDialog progressDialog;

    public CreateGroupFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.group_create, container, false);
        getActivity().setTitle(getArguments().getString("title"));

        presenter = new CreateGroupPresenter(this);

        groupName = (TextView) rootView.findViewById(R.id.group_name);
        Button button = (Button) rootView.findViewById(R.id.group_create_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createGroup();
            }
        });
        return rootView;
    }

    public TextView getGroupName() {
        return groupName;
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
