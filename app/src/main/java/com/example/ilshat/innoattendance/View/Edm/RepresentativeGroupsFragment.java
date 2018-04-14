package com.example.ilshat.innoattendance.View.Edm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ilshat.innoattendance.Presenter.Edm.RepresentativeGroupsPresenter;
import com.example.ilshat.innoattendance.R;


public class RepresentativeGroupsFragment extends Fragment {
    private RepresentativeGroupsPresenter presenter;
    private ProgressDialog progressDialog;

    private TextView representativeName;
    private TextView groupName;

    public RepresentativeGroupsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.representative_groups, container, false);
        getActivity().setTitle(getArguments().getString("title"));
        presenter = new RepresentativeGroupsPresenter(this);
        representativeName = (TextView) rootView.findViewById(R.id.representative_full_name);
        groupName = (TextView) rootView.findViewById(R.id.group);

        representativeName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                presenter.findRepresentative();
                return false;
            }
        });

        groupName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                presenter.findGroup();
                return false;
            }
        });

        Button addButton = (Button) rootView.findViewById(R.id.add_group);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addToGroup();
            }
        });
        Button deleteButton = (Button) rootView.findViewById(R.id.delete_group);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteFromGroup();
            }
        });
        return rootView;
    }


    public TextView getRepresentativeName() {
        return representativeName;
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
