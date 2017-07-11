package com.example.android.reportcardapp;

import java.util.ArrayList;

/**
 * Created by Ryan on 7/3/2017.
 */

public class ReportCard {
    ArrayList<com.example.android.reportcardapp.SchoolClass> classes = new ArrayList<SchoolClass>();

    @Override
    public String toString() {
        String msg = "";
        for(com.example.android.reportcardapp.SchoolClass schoolClass: this.classes) {
            msg += "Class Name: " + schoolClass.name + ", Class Grade: " + schoolClass.grade + ";\n";
        }
        return msg;
    }
}