package com.example.android.reportcardapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

//

public class ReportCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_card);

        //

        Student ryan_waite = new com.example.android.reportcardapp.Student("Ryan Waite, M.", 20, "University of Maryland - UMD");

        ryan_waite.reportCard.classes.add( new com.example.android.reportcardapp.SchoolClass("Calculus", 82.5) );
        ryan_waite.reportCard.classes.add( new com.example.android.reportcardapp.SchoolClass("English Literature", 78.9) );
        ryan_waite.reportCard.classes.add( new com.example.android.reportcardapp.SchoolClass("Intro to Programming", 95.3) );
        ryan_waite.reportCard.classes.add( new com.example.android.reportcardapp.SchoolClass("History", 71.6) );
        ryan_waite.reportCard.classes.add( new com.example.android.reportcardapp.SchoolClass("French 101", 89.1) );
        ryan_waite.reportCard.classes.add( new com.example.android.reportcardapp.SchoolClass("Health Foundation", 98.4) );
        ryan_waite.reportCard.classes.add( new com.example.android.reportcardapp.SchoolClass("Natural Sciences", 81.3) );
        ryan_waite.reportCard.classes.add( new com.example.android.reportcardapp.SchoolClass("Behavioral/Social Sciences", 79.6) );
        ryan_waite.reportCard.classes.add( new com.example.android.reportcardapp.SchoolClass("Humanities", 78.0) );

        ClassAdapter classesAdapter = new ClassAdapter(ReportCardActivity.this, ryan_waite.reportCard.classes);

        ListView main_view = (ListView)findViewById(R.id.main_list_view);
        main_view.setAdapter(classesAdapter);

    }
}
