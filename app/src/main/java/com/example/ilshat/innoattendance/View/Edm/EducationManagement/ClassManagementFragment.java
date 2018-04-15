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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement.ClassManagementPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Class;
import com.example.ilshat.innoattendance.RepositoryModel.Course;
import com.example.ilshat.innoattendance.View.Edm.EdmMainActivity;
import com.example.ilshat.innoattendance.View.OnBackPressedListener;

import java.util.ArrayList;

public class ClassManagementFragment extends Fragment implements OnBackPressedListener {
    private Toolbar toolbar;
    private Drawable navIconInitial;
    private String title;

    private TextView className;
    private TextView teacher;
    private ClassManagementPresenter presenter;
    private ProgressDialog progressDialog;

    private ListView listView;
    private FloatingActionButton fab;
    private View createClassView;
    private ArrayAdapter<Class> adapter;
    private Course course;

    public ClassManagementFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.class_management, container, false);
        title = getArguments().getString("title");
        getActivity().setTitle(title);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        navIconInitial = toolbar.getNavigationIcon();
        createClassView = rootView.findViewById(R.id.class_create_layout);

        listView = (ListView) rootView.findViewById(R.id.classes_list);
        ArrayList<Class> classes = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, classes);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        fab = (FloatingActionButton) rootView.findViewById(R.id.class_create_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateView();
            }
        });

        className = (TextView) createClassView.findViewById(R.id.class_name);
        teacher = (TextView) createClassView.findViewById(R.id.teacher);
        Button createSubject = (Button) createClassView.findViewById(R.id.class_create_btn);
        createSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createClass();
            }
        });

        presenter = new ClassManagementPresenter(this, classes, course);

        return rootView;
    }

    public TextView getClassName() {
        return className;
    }

    public TextView getTeacher() {
        return teacher;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_class_man, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_class:
                break;
            case R.id.edit_class:
                break;
            case R.id.events:
                try {
                    EventManagementFragment fragment = new EventManagementFragment();
                    fragment.setClass(adapter.getItem(info.position));
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
                break;
            case R.id.groups:
                try {
                    GroupManagementFragment fragment = new GroupManagementFragment();
                    fragment.setClass(adapter.getItem(info.position));
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
                break;
        }
        return true;
    }

    @Override
    public boolean onBackPressed() {
        if (createClassView.getVisibility() == View.VISIBLE) {
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
        createClassView.setVisibility(View.VISIBLE);
        getActivity().setTitle("Create class");
    }

    public void hideCreateView() {
        toolbar.setNavigationIcon(navIconInitial);
        fab.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        createClassView.setVisibility(View.GONE);
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
