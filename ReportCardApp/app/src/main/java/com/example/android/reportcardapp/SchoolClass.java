/**
 * Created by Ryan on 7/3/2017.
 */

package com.example.android.reportcardapp;

public class SchoolClass {
    String name;
    double grade;

    public SchoolClass(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }

    public void setGrate(double grade) {
        this.grade = grade;
    }
    public double getGrade() {
        return this.grade;
    }
    public String getName() {
        return this.name;
    }
}