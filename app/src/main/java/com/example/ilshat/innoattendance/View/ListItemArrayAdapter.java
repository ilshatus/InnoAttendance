package com.example.ilshat.innoattendance.View;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilshat.innoattendance.R;

public class ListItemArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] labels;
    private final int[] images;

    public ListItemArrayAdapter(Activity context, String[] values, int[] images) {
        super(context, R.layout.list_item, R.id.list_item_label, values);
        this.context = context;
        this.labels = values;
        this.images = images;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.list_item_label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_item_icon);
        textView.setText(labels[position]);
        imageView.setImageResource(images[position]);
        return rowView;
    }
}
