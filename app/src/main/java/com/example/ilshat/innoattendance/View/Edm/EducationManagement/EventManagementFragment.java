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

import com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement.EventManagementPresenter;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Class;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.View.OnBackPressedListener;

import java.util.ArrayList;

public class EventManagementFragment extends Fragment implements OnBackPressedListener {
    private Toolbar toolbar;
    private Drawable navIconInitial;
    private String title;

    private TextView eventName;
    private TextView eventPlace;
    private TextView eventDate;

    private EventManagementPresenter presenter;
    private ProgressDialog progressDialog;

    private ListView listView;
    private FloatingActionButton fab;
    private View createEventView;
    private ArrayAdapter<Event> adapter;
    private Class _class;

    public EventManagementFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_management, container, false);
        title = getArguments().getString("title");
        getActivity().setTitle(title);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        navIconInitial = toolbar.getNavigationIcon();
        createEventView = rootView.findViewById(R.id.event_create_layout);

        listView = (ListView) rootView.findViewById(R.id.events_list);
        ArrayList<Event> events = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, events);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        fab = (FloatingActionButton) rootView.findViewById(R.id.event_create_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateView();
            }
        });

        eventName = (TextView) createEventView.findViewById(R.id.event_name);
        eventPlace = (TextView) createEventView.findViewById(R.id.event_place);
        eventDate = (TextView) createEventView.findViewById(R.id.event_date);

        Button createSubject = (Button) createEventView.findViewById(R.id.event_create_btn);
        createSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createEvent();
            }
        });

        presenter = new EventManagementPresenter(this, events, _class);

        return rootView;
    }

    public TextView getEventName() {
        return eventName;
    }

    public TextView getEventPlace() {
        return eventPlace;
    }

    public TextView getEventDate() {
        return eventDate;
    }

    public void setClass(Class _class) {
        this._class = _class;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_event_man, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_event:
                break;
        }
        return true;
    }

    @Override
    public boolean onBackPressed() {
        if (createEventView.getVisibility() == View.VISIBLE) {
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
        createEventView.setVisibility(View.VISIBLE);
        getActivity().setTitle("Create event");
    }

    public void hideCreateView() {
        toolbar.setNavigationIcon(navIconInitial);
        fab.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        createEventView.setVisibility(View.GONE);
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
