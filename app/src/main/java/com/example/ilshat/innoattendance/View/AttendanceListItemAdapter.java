package com.example.ilshat.innoattendance.View;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ilshat.innoattendance.R;
import com.example.ilshat.innoattendance.RepositoryModel.Attendance;

import java.util.ArrayList;

public class AttendanceListItemAdapter extends ArrayAdapter<Attendance> {
    private final ArrayList<Attendance> attendances;

    public AttendanceListItemAdapter(Context context, ArrayList<Attendance> attendances) {
        super(context, R.layout.students_list_item, R.id.student_full_name, attendances);
        this.attendances = attendances;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = super.getView(position, convertView, parent);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.attendance_mark);
        imageView.setImageResource(attendances.get(position).isAttended() ? R.drawable.ic_mark : R.drawable.ic_unmark);
        return itemView;
    }
}
