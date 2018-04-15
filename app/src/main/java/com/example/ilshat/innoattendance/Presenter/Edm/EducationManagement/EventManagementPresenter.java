package com.example.ilshat.innoattendance.Presenter.Edm.EducationManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilshat.innoattendance.Model.Model;
import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Class;
import com.example.ilshat.innoattendance.RepositoryModel.Database;
import com.example.ilshat.innoattendance.RepositoryModel.Event;
import com.example.ilshat.innoattendance.View.Edm.EducationManagement.EventManagementFragment;

import java.util.ArrayList;

import static com.example.ilshat.innoattendance.RepositoryModel.Settings.AUTH_PREFERENCES;
import static com.example.ilshat.innoattendance.RepositoryModel.Settings.DATABASE_LINK;


public class EventManagementPresenter {
    private final EventManagementFragment fragment;
    private Model model;
    private ArrayList<Event> events;
    private Class _class;

    public EventManagementPresenter(EventManagementFragment fragment, ArrayList<Event> events, Class _class) {
        this.fragment = fragment;
        this.events = events;
        this._class = _class;
        SharedPreferences settings = fragment.getActivity().getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        model = new Model(settings);
        getEvents();
    }

    private void getEvents() {
        fragment.showProgress();
        model.getEvents(_class, new Model.GetEventsCallback() {
            @Override
            public void onComplete(ArrayList<Event> _events) {
                fragment.hideProgress();
                events.addAll(_events);
                fragment.updateAdapter();
            }
        });
    }

    public void createEvent() {
        final TextView eventName = fragment.getEventName();
        final TextView eventPlace = fragment.getEventPlace();
        final TextView eventDate = fragment.getEventDate();
        String sEventName = eventName.getText().toString();
        String sEventPlace = eventPlace.getText().toString();
        String sEventDate = eventDate.getText().toString();

        String fieldIsRequired = fragment.getActivity().getString(R.string.error_field_required);


        if (sEventName.isEmpty()) {
            eventName.setError(fieldIsRequired);
            eventName.requestFocus();
        } else if (sEventPlace.isEmpty()){
            eventPlace.setError(fieldIsRequired);
            eventPlace.requestFocus();
        } else if (sEventDate.isEmpty()) {
            eventDate.setError(fieldIsRequired);
            eventDate.requestFocus();
        } else {
            fragment.showProgress();
            model.createEvent(_class, sEventName, sEventPlace, sEventDate,
                    new Model.CreateEventCallback() {
                @Override
                public void onComplete(Event event) {
                    fragment.hideProgress();
                    fragment.hideCreateView();
                    eventName.setText("");
                    eventPlace.setText("");
                    eventDate.setText("");
                    eventName.requestFocus();
                    if (event != null) {
                        events.add(0, event);
                        fragment.updateAdapter();
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Event added", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(fragment.getContext(),
                                "Some error happened", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
    }
}
