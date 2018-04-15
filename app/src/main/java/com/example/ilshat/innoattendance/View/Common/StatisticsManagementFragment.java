package com.example.ilshat.innoattendance.View.Common;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ilshat.innoattendance.R;

public class StatisticsManagementFragment extends Fragment {
    public StatisticsManagementFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.statistics_management, container, false);
        getActivity().setTitle(getArguments().getString("title"));
        return rootView;
    }
}
