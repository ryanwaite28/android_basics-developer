package com.example.android.reportcardapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ryan on 7/3/2017.
 */

public class ClassAdapter extends ArrayAdapter<SchoolClass> {

    public ClassAdapter(Activity context, ArrayList<SchoolClass> classList) {
        super(context, 0, classList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);

        SchoolClass current_class = getItem(position);

        View list_item_view = convertView;
        if(list_item_view == null) {
            list_item_view = LayoutInflater.from(getContext()).inflate(R.layout.class_item, parent, false);
        }

        TextView class_name = (TextView) list_item_view.findViewById(R.id.class_name);
        TextView class_grade = (TextView) list_item_view.findViewById(R.id.class_grade);

        class_name.setText( current_class.getName() );
        class_grade.setText( String.valueOf( current_class.getGrade() + "%" ) );

        return list_item_view;

    }
}
