package com.example.ilshat.innoattendance.View.Edm.UserManagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.View.Edm.EdmMainActivity;
import com.example.ilshat.innoattendance.View.ListItemArrayAdapter;

public class UserManagementFragment extends Fragment
        implements AdapterView.OnItemClickListener {
    private final String[] labels = new String[]{
            "Create new user",
            "Edit user",
            "Student groups",
            "Representative groups"
    };
    private final int[] images = new int[] {
            R.drawable.ic_person_create,
            R.drawable.ic_edit,
            R.drawable.ic_group_add,
            R.drawable.ic_group_add
    };

    public UserManagementFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_menu, container, false);
        getActivity().setTitle(getArguments().getString("title"));
        ListItemArrayAdapter adapter = new ListItemArrayAdapter(getActivity(), labels, images);
        ListView listView = (ListView) rootView.findViewById(R.id.list_menu_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = null;
        Class fragmentClass = null;

        switch (position) {
            case 0:
                fragmentClass = CreateUserFragment.class;
                break;
            case 2:
                fragmentClass = StudentGroupsFragment.class;
                break;
            case 3:
                fragmentClass = RepresentativeGroupsFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            Bundle args = new Bundle();
            args.putString("title", labels[position]);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, fragment, labels[position])
                    .addToBackStack(labels[position])
                    .commit();
            EdmMainActivity mainActivity = (EdmMainActivity) getActivity();
            mainActivity.setupToolbarNavigation();
        } catch (Exception e) {
            Log.v(EdmMainActivity.class.getName(), e.getMessage());
        }
    }
}
