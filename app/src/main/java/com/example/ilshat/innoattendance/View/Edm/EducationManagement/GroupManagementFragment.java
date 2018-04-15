package com.example.ilshat.innoattendance.View.Edm.EducationManagement;


import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement.GroupManagementPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Class;
import com.example.ilshat.innoattendance.RepositoryModel.Group;
import com.example.ilshat.innoattendance.View.OnBackPressedListener;

import java.util.ArrayList;

public class GroupManagementFragment extends Fragment implements OnBackPressedListener {
    private Toolbar toolbar;
    private Drawable navIconInitial;
    private String title;

    private TextView groupName;

    private GroupManagementPresenter presenter;
    private ProgressDialog progressDialog;

    private ListView listView;
    private FloatingActionButton fab;
    private View createGroupView;
    private ArrayAdapter<Group> adapter;
    private Class _class;

    public GroupManagementFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.group_management, container, false);
        title = getArguments().getString("title");
        getActivity().setTitle(title);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        navIconInitial = toolbar.getNavigationIcon();
        createGroupView = rootView.findViewById(R.id.group_create_layout);

        listView = (ListView) rootView.findViewById(R.id.groups_list);
        ArrayList<Group> groups = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, groups);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        fab = (FloatingActionButton) rootView.findViewById(R.id.group_create_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateView();
            }
        });

        groupName = (TextView) createGroupView.findViewById(R.id.group_name);

        Button createGroup = (Button) createGroupView.findViewById(R.id.group_create_btn);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createGroup();
            }
        });

        presenter = new GroupManagementPresenter(this, groups, _class);

        return rootView;
    }

    public TextView getGroupName() {
        return groupName;
    }

    public void setClass(Class _class) {
        this._class = _class;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_group_man, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_group:
                break;
        }
        return true;
    }

    @Override
    public boolean onBackPressed() {
        if (createGroupView.getVisibility() == View.VISIBLE) {
            hideCreateView();
            return true;
        }
        return false;
    }

    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void showCreateView() {
        toolbar.setNavigationIcon(R.drawable.ic_close);
        fab.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        createGroupView.setVisibility(View.VISIBLE);
        getActivity().setTitle("Create group");
    }

    public void hideCreateView() {
        toolbar.setNavigationIcon(navIconInitial);
        fab.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        createGroupView.setVisibility(View.GONE);
        getActivity().setTitle(title);
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
