package com.example.android.tourguideapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import static android.R.attr.start;

/**
 * Created by Ryan on 7/3/2017.
 */

public class Spot {
    Activity context;
    String name;
    String type;
    String streetAddress;
    String city;
    String state;
    int zipCode;
    double lat;
    double lng;
    String webLink;
    String phoneNumber;
    int imgResourceID;
    Uri uri;

    public Spot(Activity context, String name, String type, String streetAddress, String city, String state, int zipCode, double lat, double lng, String webLink, String phoneNumber, int imgResourceID) {
        this.context = context;
        this.name = name;
        this.type = type;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.lat = lat;
        this.lng = lng;
        this.webLink = webLink;
        this.phoneNumber = phoneNumber;
        this.imgResourceID = imgResourceID;
        this.uri = Uri.parse( "geo:" + this.lat + "," + this.lng );
    }

    @Override
    public String toString() {
        String string = "";

        string += "Name: " + this.name + "\n";
        string += "Type: " + this.type + "\n";
        string += "Address: " + this.streetAddress + "\n";
        string += "City: " + this.city + "\n";
        string += "State: " + this.state + "\n";
        string += "Zip Code: " + this.zipCode + "\n";
        string += "Latitude: " + this.lat + "\n";
        string += "Longitude: " + this.lng + "\n";
        string += "Web Link: " + this.webLink + "\n";
        string += "Phone Number: " + this.phoneNumber + "\n";

        return string;
    }

}
