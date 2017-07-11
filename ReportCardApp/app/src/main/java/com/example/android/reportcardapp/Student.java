/**
 * Created by Ryan on 7/3/2017.
 */

package com.example.android.reportcardapp;

public class Student {
    String name;
    int age;
    String school;
    Boolean eligible;
    com.example.android.reportcardapp.ReportCard reportCard;

    public Student(String name, int age, String school) {
        this.name = name;
        this.age = age;
        this.school = school;
        this.reportCard = new com.example.android.reportcardapp.ReportCard();
    }

    public Boolean getEligibility() {
        return this.eligible;
    }
    public void setEligibility(Boolean eligible) {
        this.eligible = eligible;
    }

    public com.example.android.reportcardapp.ReportCard getReportCard() {
        return this.reportCard;
    }
    public void setReportCard(com.example.android.reportcardapp.ReportCard reportCard) {
        this.reportCard = reportCard;
    }

    public void addClass(com.example.android.reportcardapp.SchoolClass schoolClass) {
        if(this.reportCard.classes.indexOf(schoolClass) == -1) {
            this.reportCard.classes.add(schoolClass);
        }
    }
    public void removeClass(com.example.android.reportcardapp.SchoolClass schoolClass) {
        if(this.reportCard.classes.indexOf(schoolClass) != -1) {
            this.reportCard.classes.remove(schoolClass);
        }
    }
}