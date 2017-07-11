package com.example.android.tourguideapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("--- First Check", "Admit One");

        //

        TextView restaurants_btn = (TextView)findViewById(R.id.restaurants_btn);
        TextView shopping_btn = (TextView)findViewById(R.id.shopping_btn);
        TextView venues_btn = (TextView)findViewById(R.id.venues_btn);
        TextView museums_btn = (TextView)findViewById(R.id.museums_btn);

        restaurants_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, SpotsActivity.class);
                intent.putExtra("category", "Restaurants");
                startActivity(intent);
            }
        });
        shopping_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, SpotsActivity.class);
                intent.putExtra("category", "Shopping");
                startActivity(intent);
            }
        });
        venues_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, SpotsActivity.class);
                intent.putExtra("category", "Venues");
                startActivity(intent);
            }
        });
        museums_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, SpotsActivity.class);
                intent.putExtra("category", "Museums");
                startActivity(intent);
            }
        });

    }
}
