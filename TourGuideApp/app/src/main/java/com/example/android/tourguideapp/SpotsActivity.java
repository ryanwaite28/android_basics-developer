package com.example.android.tourguideapp;

// Import Statements

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.id.message;
import static com.example.android.tourguideapp.R.id.maps_btn;
import static com.example.android.tourguideapp.R.id.share_btn;

// Custom Adapter

class SpotAdapter extends ArrayAdapter<Spot> {

    public SpotAdapter(Activity context, ArrayList<Spot> spotList) {
        super(context, 0, spotList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Spot current_spot = getItem(position);

        View list_item_view = convertView;
        if(list_item_view == null) {
            list_item_view = LayoutInflater.from(getContext()).inflate(R.layout.spot_item, parent, false);
        }

        ImageView spotIcon = (ImageView) list_item_view.findViewById(R.id.spot_icon);
        TextView spotName = (TextView) list_item_view.findViewById(R.id.spot_name);
        Button view_info_btn = (Button) list_item_view.findViewById(R.id.view_info_btn);

        spotIcon.setImageResource( current_spot.imgResourceID );
        spotName.setText( current_spot.name );
        view_info_btn.setTag(current_spot);

        return list_item_view;

    }
}

// Sports Activity

public class SpotsActivity extends AppCompatActivity {

    ArrayList<Spot> spots_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spots);

        String category = getIntent().getExtras().getString("category");
        setTitle(category);
        Log.v("Category --- ", category);

        init(String.valueOf(category));

    }

    // Methods For Building Category Lists

    public void init(String category) {
        Log.v("init", "Building.....");
        if( category.equals("Restaurants") ) {
            Log.v("Building... ", category);
            build_restaurants();
        }
        else if( category.equals("Shopping") ) {
            Log.v("Building... ", category);
            build_shopping();
        }
        else if( category.equals("Venues") ) {
            Log.v("Building... ", category);
            build_venues();
        }
        else if( category.equals("Museums") ) {
            Log.v("Building... ", category);
            build_museums();
        }
        else {
            Log.v("Init Results", "Not Building...");
        }
    }

    public void build_restaurants() {
        spots_list = new ArrayList<Spot>();

        int img_one_id = getResources().getIdentifier("vapiano" , "drawable", getPackageName());
        int img_two_id = getResources().getIdentifier("bbap" , "drawable", getPackageName());
        int img_three_id = getResources().getIdentifier("luke" , "drawable", getPackageName());
        int img_four_id = getResources().getIdentifier("mastro" , "drawable", getPackageName());

        String spot_one_name = getResources().getString(R.string.rs_one_name);
        String spot_one_type = getResources().getString(R.string.rs_one_type);
        String spot_one_street = getResources().getString(R.string.rs_one_street);
        String spot_one_city = getResources().getString(R.string.rs_one_city);
        String spot_one_state = getResources().getString(R.string.rs_one_state);
        int spot_one_zipcode = Integer.parseInt(getResources().getString(R.string.rs_one_zipcode));
        String spot_one_link = getResources().getString(R.string.rs_one_link);
        String spot_one_phone = getResources().getString(R.string.rs_one_phone);

        String spot_two_name = getResources().getString(R.string.rs_two_name);
        String spot_two_type = getResources().getString(R.string.rs_two_type);
        String spot_two_street = getResources().getString(R.string.rs_two_street);
        String spot_two_city = getResources().getString(R.string.rs_two_city);
        String spot_two_state = getResources().getString(R.string.rs_two_state);
        int spot_two_zipcode = Integer.parseInt(getResources().getString(R.string.rs_two_zipcode));
        String spot_two_link = getResources().getString(R.string.rs_two_link);
        String spot_two_phone = getResources().getString(R.string.rs_two_phone);

        String spot_three_name = getResources().getString(R.string.rs_three_name);
        String spot_three_type = getResources().getString(R.string.rs_three_type);
        String spot_three_street = getResources().getString(R.string.rs_three_street);
        String spot_three_city = getResources().getString(R.string.rs_three_city);
        String spot_three_state = getResources().getString(R.string.rs_three_state);
        int spot_three_zipcode = Integer.parseInt(getResources().getString(R.string.rs_three_zipcode));
        String spot_three_link = getResources().getString(R.string.rs_three_link);
        String spot_three_phone = getResources().getString(R.string.rs_three_phone);

        String spot_four_name = getResources().getString(R.string.rs_four_name);
        String spot_four_type = getResources().getString(R.string.rs_four_type);
        String spot_four_street = getResources().getString(R.string.rs_four_street);
        String spot_four_city = getResources().getString(R.string.rs_four_city);
        String spot_four_state = getResources().getString(R.string.rs_four_state);
        int spot_four_zipcode = Integer.parseInt(getResources().getString(R.string.rs_four_zipcode));
        String spot_four_link = getResources().getString(R.string.rs_four_link);
        String spot_four_phone = getResources().getString(R.string.rs_four_phone);

        spots_list.add(new Spot(SpotsActivity.this, spot_one_name, spot_one_type, spot_one_street, spot_one_city, spot_one_state, spot_one_zipcode, 38.917998, -77.0366935, spot_one_link, spot_one_phone, img_one_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_two_name, spot_two_type, spot_two_street, spot_two_city, spot_two_state, spot_two_zipcode, 38.917998, -77.0366935, spot_two_link, spot_two_phone, img_two_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_three_name, spot_three_type, spot_three_street, spot_three_city, spot_three_state, spot_three_zipcode, 38.917998, -77.0366935, spot_three_link, spot_three_phone, img_three_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_four_name, spot_four_type, spot_four_street, spot_four_city, spot_four_state, spot_four_zipcode, 38.917998, -77.0366935, spot_four_link, spot_four_phone, img_four_id));

        SpotAdapter spotAdapter = new SpotAdapter(SpotsActivity.this, spots_list);
        ListView main_view = (ListView)findViewById(R.id.main_list_view);
        main_view.setAdapter(spotAdapter);
    }

    public void build_shopping() {
        spots_list = new ArrayList<Spot>();

        int img_one_id = getResources().getIdentifier("citycenterdc" , "drawable", getPackageName());
        int img_two_id = getResources().getIdentifier("satc" , "drawable", getPackageName());
        int img_three_id = getResources().getIdentifier("dtss" , "drawable", getPackageName());
        int img_four_id = getResources().getIdentifier("par" , "drawable", getPackageName());

        String spot_one_name = getResources().getString(R.string.ss_one_name);
        String spot_one_type = getResources().getString(R.string.ss_one_type);
        String spot_one_street = getResources().getString(R.string.ss_one_street);
        String spot_one_city = getResources().getString(R.string.ss_one_city);
        String spot_one_state = getResources().getString(R.string.ss_one_state);
        int spot_one_zipcode = Integer.parseInt(getResources().getString(R.string.ss_one_zipcode));
        String spot_one_link = getResources().getString(R.string.ss_one_link);
        String spot_one_phone = getResources().getString(R.string.ss_one_phone);

        String spot_two_name = getResources().getString(R.string.ss_two_name);
        String spot_two_type = getResources().getString(R.string.ss_two_type);
        String spot_two_street = getResources().getString(R.string.ss_two_street);
        String spot_two_city = getResources().getString(R.string.ss_two_city);
        String spot_two_state = getResources().getString(R.string.ss_two_state);
        int spot_two_zipcode = Integer.parseInt(getResources().getString(R.string.ss_two_zipcode));
        String spot_two_link = getResources().getString(R.string.ss_two_link);
        String spot_two_phone = getResources().getString(R.string.ss_two_phone);

        String spot_three_name = getResources().getString(R.string.ss_three_name);
        String spot_three_type = getResources().getString(R.string.ss_three_type);
        String spot_three_street = getResources().getString(R.string.ss_three_street);
        String spot_three_city = getResources().getString(R.string.ss_three_city);
        String spot_three_state = getResources().getString(R.string.ss_three_state);
        int spot_three_zipcode = Integer.parseInt(getResources().getString(R.string.ss_three_zipcode));
        String spot_three_link = getResources().getString(R.string.ss_three_link);
        String spot_three_phone = getResources().getString(R.string.ss_three_phone);

        String spot_four_name = getResources().getString(R.string.ss_four_name);
        String spot_four_type = getResources().getString(R.string.ss_four_type);
        String spot_four_street = getResources().getString(R.string.ss_four_street);
        String spot_four_city = getResources().getString(R.string.ss_four_city);
        String spot_four_state = getResources().getString(R.string.ss_four_state);
        int spot_four_zipcode = Integer.parseInt(getResources().getString(R.string.ss_four_zipcode));
        String spot_four_link = getResources().getString(R.string.ss_four_link);
        String spot_four_phone = getResources().getString(R.string.ss_four_phone);

        spots_list.add(new Spot(SpotsActivity.this, spot_one_name, spot_one_type, spot_one_street, spot_one_city, spot_one_state, spot_one_zipcode, 38.917998, -77.0366935, spot_one_link, spot_one_phone, img_one_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_two_name, spot_two_type, spot_two_street, spot_two_city, spot_two_state, spot_two_zipcode, 38.917998, -77.0366935, spot_two_link, spot_two_phone, img_two_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_three_name, spot_three_type, spot_three_street, spot_three_city, spot_three_state, spot_three_zipcode, 38.917998, -77.0366935, spot_three_link, spot_three_phone, img_three_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_four_name, spot_four_type, spot_four_street, spot_four_city, spot_four_state, spot_four_zipcode, 38.917998, -77.0366935, spot_four_link, spot_four_phone, img_four_id));

        SpotAdapter spotAdapter = new SpotAdapter(SpotsActivity.this, spots_list);
        ListView main_view = (ListView)findViewById(R.id.main_list_view);
        main_view.setAdapter(spotAdapter);
    }

    public void build_venues() {
        spots_list = new ArrayList<Spot>();

        int img_one_id = getResources().getIdentifier("ampbystrathmore" , "drawable", getPackageName());
        int img_two_id = getResources().getIdentifier("jamminjava" , "drawable", getPackageName());
        int img_three_id = getResources().getIdentifier("fillmore" , "drawable", getPackageName());
        int img_four_id = getResources().getIdentifier("usmc" , "drawable", getPackageName());

        String spot_one_name = getResources().getString(R.string.vs_one_name);
        String spot_one_type = getResources().getString(R.string.vs_one_type);
        String spot_one_street = getResources().getString(R.string.vs_one_street);
        String spot_one_city = getResources().getString(R.string.vs_one_city);
        String spot_one_state = getResources().getString(R.string.vs_one_state);
        int spot_one_zipcode = Integer.parseInt(getResources().getString(R.string.vs_one_zipcode));
        String spot_one_link = getResources().getString(R.string.vs_one_link);
        String spot_one_phone = getResources().getString(R.string.vs_one_phone);

        String spot_two_name = getResources().getString(R.string.vs_two_name);
        String spot_two_type = getResources().getString(R.string.vs_two_type);
        String spot_two_street = getResources().getString(R.string.vs_two_street);
        String spot_two_city = getResources().getString(R.string.vs_two_city);
        String spot_two_state = getResources().getString(R.string.vs_two_state);
        int spot_two_zipcode = Integer.parseInt(getResources().getString(R.string.vs_two_zipcode));
        String spot_two_link = getResources().getString(R.string.vs_two_link);
        String spot_two_phone = getResources().getString(R.string.vs_two_phone);

        String spot_three_name = getResources().getString(R.string.vs_three_name);
        String spot_three_type = getResources().getString(R.string.vs_three_type);
        String spot_three_street = getResources().getString(R.string.vs_three_street);
        String spot_three_city = getResources().getString(R.string.vs_three_city);
        String spot_three_state = getResources().getString(R.string.vs_three_state);
        int spot_three_zipcode = Integer.parseInt(getResources().getString(R.string.vs_three_zipcode));
        String spot_three_link = getResources().getString(R.string.vs_three_link);
        String spot_three_phone = getResources().getString(R.string.vs_three_phone);

        String spot_four_name = getResources().getString(R.string.vs_four_name);
        String spot_four_type = getResources().getString(R.string.vs_four_type);
        String spot_four_street = getResources().getString(R.string.vs_four_street);
        String spot_four_city = getResources().getString(R.string.vs_four_city);
        String spot_four_state = getResources().getString(R.string.vs_four_state);
        int spot_four_zipcode = Integer.parseInt(getResources().getString(R.string.vs_four_zipcode));
        String spot_four_link = getResources().getString(R.string.vs_four_link);
        String spot_four_phone = getResources().getString(R.string.vs_four_phone);

        spots_list.add(new Spot(SpotsActivity.this, spot_one_name, spot_one_type, spot_one_street, spot_one_city, spot_one_state, spot_one_zipcode, 38.917998, -77.0366935, spot_one_link, spot_one_phone, img_one_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_two_name, spot_two_type, spot_two_street, spot_two_city, spot_two_state, spot_two_zipcode, 38.917998, -77.0366935, spot_two_link, spot_two_phone, img_two_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_three_name, spot_three_type, spot_three_street, spot_three_city, spot_three_state, spot_three_zipcode, 38.917998, -77.0366935, spot_three_link, spot_three_phone, img_three_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_four_name, spot_four_type, spot_four_street, spot_four_city, spot_four_state, spot_four_zipcode, 38.917998, -77.0366935, spot_four_link, spot_four_phone, img_four_id));

        SpotAdapter spotAdapter = new SpotAdapter(SpotsActivity.this, spots_list);
        ListView main_view = (ListView)findViewById(R.id.main_list_view);
        main_view.setAdapter(spotAdapter);
    }

    public void build_museums() {
        spots_list = new ArrayList<Spot>();

        int img_one_id = getResources().getIdentifier("nmotai" , "drawable", getPackageName());
        int img_two_id = getResources().getIdentifier("nmaerosky" , "drawable", getPackageName());
        int img_three_id = getResources().getIdentifier("nmblackppl" , "drawable", getPackageName());
        int img_four_id = getResources().getIdentifier("nmnature" , "drawable", getPackageName());

        String spot_one_name = getResources().getString(R.string.ms_one_name);
        String spot_one_type = getResources().getString(R.string.ms_one_type);
        String spot_one_street = getResources().getString(R.string.ms_one_street);
        String spot_one_city = getResources().getString(R.string.ms_one_city);
        String spot_one_state = getResources().getString(R.string.ms_one_state);
        int spot_one_zipcode = Integer.parseInt(getResources().getString(R.string.ms_one_zipcode));
        String spot_one_link = getResources().getString(R.string.ms_one_link);
        String spot_one_phone = getResources().getString(R.string.ms_one_phone);

        String spot_two_name = getResources().getString(R.string.ms_two_name);
        String spot_two_type = getResources().getString(R.string.ms_two_type);
        String spot_two_street = getResources().getString(R.string.ms_two_street);
        String spot_two_city = getResources().getString(R.string.ms_two_city);
        String spot_two_state = getResources().getString(R.string.ms_two_state);
        int spot_two_zipcode = Integer.parseInt(getResources().getString(R.string.ms_two_zipcode));
        String spot_two_link = getResources().getString(R.string.ms_two_link);
        String spot_two_phone = getResources().getString(R.string.ms_two_phone);

        String spot_three_name = getResources().getString(R.string.ms_three_name);
        String spot_three_type = getResources().getString(R.string.ms_three_type);
        String spot_three_street = getResources().getString(R.string.ms_three_street);
        String spot_three_city = getResources().getString(R.string.ms_three_city);
        String spot_three_state = getResources().getString(R.string.ms_three_state);
        int spot_three_zipcode = Integer.parseInt(getResources().getString(R.string.ms_three_zipcode));
        String spot_three_link = getResources().getString(R.string.ms_three_link);
        String spot_three_phone = getResources().getString(R.string.ms_three_phone);

        String spot_four_name = getResources().getString(R.string.ms_four_name);
        String spot_four_type = getResources().getString(R.string.ms_four_type);
        String spot_four_street = getResources().getString(R.string.ms_four_street);
        String spot_four_city = getResources().getString(R.string.ms_four_city);
        String spot_four_state = getResources().getString(R.string.ms_four_state);
        int spot_four_zipcode = Integer.parseInt(getResources().getString(R.string.ms_four_zipcode));
        String spot_four_link = getResources().getString(R.string.ms_four_link);
        String spot_four_phone = getResources().getString(R.string.ms_four_phone);

        spots_list.add(new Spot(SpotsActivity.this, spot_one_name, spot_one_type, spot_one_street, spot_one_city, spot_one_state, spot_one_zipcode, 38.917998, -77.0366935, spot_one_link, spot_one_phone, img_one_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_two_name, spot_two_type, spot_two_street, spot_two_city, spot_two_state, spot_two_zipcode, 38.917998, -77.0366935, spot_two_link, spot_two_phone, img_two_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_three_name, spot_three_type, spot_three_street, spot_three_city, spot_three_state, spot_three_zipcode, 38.917998, -77.0366935, spot_three_link, spot_three_phone, img_three_id));
        spots_list.add(new Spot(SpotsActivity.this, spot_four_name, spot_four_type, spot_four_street, spot_four_city, spot_four_state, spot_four_zipcode, 38.917998, -77.0366935, spot_four_link, spot_four_phone, img_four_id));

        SpotAdapter spotAdapter = new SpotAdapter(SpotsActivity.this, spots_list);
        ListView main_view = (ListView)findViewById(R.id.main_list_view);
        main_view.setAdapter(spotAdapter);
    }

    // Spot Intentions Methods

    public void viewSpotInfo(View view) {
        Spot spot = (Spot)view.getTag();
        Log.v("Spot --- ", spot.name);

        Intent intent = new Intent(SpotsActivity.this, SpotViewActivity.class);

        intent.putExtra("spotName", spot.name);
        intent.putExtra("spotType", spot.type);
        intent.putExtra("spotAddress", spot.streetAddress);
        intent.putExtra("spotCity", spot.city);
        intent.putExtra("spotState", spot.state);
        intent.putExtra("spotZipCode", String.valueOf(spot.zipCode));
        intent.putExtra("spotWebLink", spot.webLink);
        intent.putExtra("spotPhoneNumber", spot.phoneNumber);
        intent.putExtra("spotImgResID", String.valueOf(spot.imgResourceID));
        intent.putExtra("spotLat", String.valueOf(spot.lat));
        intent.putExtra("spotLng", String.valueOf(spot.lng));
        intent.putExtra("spotURI", String.valueOf(spot.uri));
        intent.putExtra("spotRaw", spot.toString());

        startActivity(intent);

    }
}
