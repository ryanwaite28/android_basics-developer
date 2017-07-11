package com.example.android.tourguideapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.R.attr.category;

public class SpotViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_view);

        // Get data from previous view
        final String spot_Name = getIntent().getExtras().getString("spotName");
        String spot_Type = getIntent().getExtras().getString("spotType");
        String spot_Address = getIntent().getExtras().getString("spotAddress");
        String spot_City = getIntent().getExtras().getString("spotCity");
        String spot_State = getIntent().getExtras().getString("spotState");
        int spot_ZipCode = Integer.parseInt(getIntent().getExtras().getString("spotZipCode"));
        String spot_WebLink = getIntent().getExtras().getString("spotWebLink");
        String spot_PhoneNumber = getIntent().getExtras().getString("spotPhoneNumber");
        int spot_ImgResID = Integer.parseInt(getIntent().getExtras().getString("spotImgResID"));
        double spot_Lat = Double.parseDouble(getIntent().getExtras().getString("spotLat"));
        double spot_Lng = Double.parseDouble(getIntent().getExtras().getString("spotLng"));
        final Uri spot_URI = Uri.parse(getIntent().getExtras().getString("spotURI"));
        final String spot_Raw = getIntent().getExtras().getString("spotRaw");

        setTitle(spot_Name);

        //

        ImageView spotIcon = (ImageView) findViewById(R.id.spot_icon);
        TextView spotName = (TextView) findViewById(R.id.spot_name);
        TextView spotAddress = (TextView) findViewById(R.id.spot_address);
        TextView spotCity = (TextView) findViewById(R.id.spot_city);
        TextView spotState = (TextView) findViewById(R.id.spot_state);
        TextView spotZipCode = (TextView) findViewById(R.id.spot_zipcode);
        TextView spotWebLink = (TextView) findViewById(R.id.spot_weblink);
        TextView spotPhoneNumber = (TextView) findViewById(R.id.spot_phone);

        Button maps_btn = (Button) findViewById(R.id.maps_btn);
        Button share_email_btn = (Button) findViewById(R.id.share_email_btn);
        Button share_text_btn = (Button) findViewById(R.id.share_text_btn);

        //

        spotIcon.setImageResource( spot_ImgResID );
        spotName.setText( spot_Name );
        spotAddress.setText( spot_Address );
        spotCity.setText( spot_City );
        spotState.setText( spot_State );
        spotZipCode.setText( String.valueOf(spot_ZipCode) );
        spotWebLink.setText( spot_WebLink );
        spotPhoneNumber.setText( spot_PhoneNumber );

        maps_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(spot_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        share_email_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));  // This ensures only Email apps respond
                intent.putExtra(Intent.EXTRA_SUBJECT, "Visit: " + spot_Name);
                intent.putExtra(Intent.EXTRA_TEXT, spot_Raw);
                // intent.putExtra(Intent.EXTRA_STREAM, attachment);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        share_text_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
                intent.putExtra("sms_body", spot_Raw);
                // intent.putExtra(Intent.EXTRA_STREAM, attachment);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }

}
