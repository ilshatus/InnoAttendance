package com.example.ilshat.innoattendance.View.Edm.EducationManagement;


import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement.SubjectManagementPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Subject;
import com.example.ilshat.innoattendance.View.Edm.EdmMainActivity;
import com.example.ilshat.innoattendance.View.OnBackPressedListener;

import java.util.ArrayList;

public class SubjectManagementFragment extends Fragment implements OnBackPressedListener {
    private Toolbar toolbar;
    private Drawable navIconInitial;
    private String title;

    private TextView subjectName;
    private SubjectManagementPresenter presenter;
    private ProgressDialog progressDialog;

    private ListView listView;
    private FloatingActionButton fab;
    private View createSubjectView;
    private ArrayAdapter<Subject> adapter;

    public SubjectManagementFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.subject_management, container, false);
        title = getArguments().getString("title");
        getActivity().setTitle(title);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        navIconInitial = toolbar.getNavigationIcon();

        createSubjectView = rootView.findViewById(R.id.subject_create_layout);

        listView = (ListView) rootView.findViewById(R.id.subjects_list);
        ArrayList<Subject> subjects = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, subjects);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        fab = (FloatingActionButton) rootView.findViewById(R.id.subject_create_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateView();
            }
        });

        subjectName = (TextView) createSubjectView.findViewById(R.id.subject_name);
        Button createSubject = (Button) createSubjectView.findViewById(R.id.subject_create_btn);
        createSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createSubject();
            }
        });
        presenter = new SubjectManagementPresenter(this, subjects);


        return rootView;
    }

    public TextView getSubjectName() {
        return subjectName;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_subject_man, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_subject:
                break;
            case R.id.rename_subject:
                break;
            case R.id.courses:
                try {
                    CourseManagementFragment fragment = new CourseManagementFragment();
                    fragment.setSubject(adapter.getItem(info.position));
                    Bundle args = new Bundle();
                    String tag = item.getTitle().toString();
                    args.putString("title", tag);
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_main, fragment, tag)
                            .addToBackStack(tag)
                            .commit();
                } catch (Exception e) {
                    Log.v(EdmMainActivity.class.getName(), e.getMessage());
                }
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        return true;
    }

    @Override
    public boolean onBackPressed() {
        if (createSubjectView.getVisibility() == View.VISIBLE) {
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
        createSubjectView.setVisibility(View.VISIBLE);
        getActivity().setTitle("Create subject");
    }

    public void hideCreateView() {
        toolbar.setNavigationIcon(navIconInitial);
        fab.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        createSubjectView.setVisibility(View.GONE);
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
