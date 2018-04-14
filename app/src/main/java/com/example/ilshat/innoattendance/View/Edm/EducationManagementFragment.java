package com.example.ilshat.innoattendance.View.Edm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.View.ListItemArrayAdapter;

public class EducationManagementFragment extends android.support.v4.app.Fragment{
    private final String[] labels = new String[]{
            "Subject management",
            "Course management",
            "Class management"
    };
    private final int[] images = new int[] {
            R.drawable.ic_edit,
            R.drawable.ic_edit,
            R.drawable.ic_edit
    };

    public EducationManagementFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_menu, container, false);
        getActivity().setTitle(getArguments().getString("title"));
        ListItemArrayAdapter adapter = new ListItemArrayAdapter(getActivity(), labels, images);
        ListView listView = (ListView) rootView.findViewById(R.id.list_menu_view);
        listView.setAdapter(adapter);
        return rootView;
    }
}
