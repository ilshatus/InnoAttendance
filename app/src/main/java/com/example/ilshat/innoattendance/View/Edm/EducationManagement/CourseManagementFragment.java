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

import com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement.CourseManagementPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Course;
import com.example.ilshat.innoattendance.RepositoryModel.Subject;
import com.example.ilshat.innoattendance.View.Edm.EdmMainActivity;
import com.example.ilshat.innoattendance.View.OnBackPressedListener;

import java.util.ArrayList;

public class CourseManagementFragment extends Fragment implements OnBackPressedListener {
    private Toolbar toolbar;
    private Drawable navIconInitial;
    private String title;

    private TextView courseName;
    private TextView courseYear;
    private CourseManagementPresenter presenter;
    private ProgressDialog progressDialog;

    private ListView listView;
    private FloatingActionButton fab;
    private View createCourseView;
    private ArrayAdapter<Course> adapter;
    private Subject subject;

    public CourseManagementFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.course_management, container, false);
        title = getArguments().getString("title");
        getActivity().setTitle(title);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        navIconInitial = toolbar.getNavigationIcon();
        createCourseView = rootView.findViewById(R.id.course_create_layout);

        listView = (ListView) rootView.findViewById(R.id.courses_list);
        ArrayList<Course> courses = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, courses);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        fab = (FloatingActionButton) rootView.findViewById(R.id.course_create_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateView();
            }
        });

        courseName = (TextView) createCourseView.findViewById(R.id.course_name);
        courseYear = (TextView) createCourseView.findViewById(R.id.course_year);
        Button createSubject = (Button) createCourseView.findViewById(R.id.course_create_btn);
        createSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createCourse();
            }
        });

        presenter = new CourseManagementPresenter(this, courses, subject);

        return rootView;
    }

    public TextView getCourseName() {
        return courseName;
    }

    public TextView getCourseYear() {
        return courseYear;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_course_man, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_course:
                break;
            case R.id.edit_course:
                break;
            case R.id.classes:
                try {
                    ClassManagementFragment fragment = new ClassManagementFragment();
                    fragment.setCourse(adapter.getItem(info.position));
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
        return true;
    }

    @Override
    public boolean onBackPressed() {
        if (createCourseView.getVisibility() == View.VISIBLE) {
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
        createCourseView.setVisibility(View.VISIBLE);
        getActivity().setTitle("Create course");
    }

    public void hideCreateView() {
        toolbar.setNavigationIcon(navIconInitial);
        fab.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        createCourseView.setVisibility(View.GONE);
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
