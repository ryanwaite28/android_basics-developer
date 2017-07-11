package com.example.android.inventoryapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.inventoryapp.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initApp();

    }

    public void initApp() {
        // Check if database is workinf before setting up display view
        try {
            Boolean status = checkDatabase();
            if(status == true) {
                ProgressBar bar = (ProgressBar)findViewById(R.id.welcome_progressbar);
                bar.setVisibility(View.GONE);

                try {
                    Thread.sleep(3000);
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    startActivity(intent);
                }
                catch(Exception e) {
                    Log.v("Thread error --- ", String.valueOf(e));
                }
            }
            else {
                TextView msg = (TextView)findViewById(R.id.msg_view);
                ProgressBar bar = (ProgressBar)findViewById(R.id.welcome_progressbar);

                msg.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
            }
        }
        catch(Exception e) {
            Log.v("Thread error --- ", String.valueOf(e));
        }
    }

    public Boolean checkDatabase() {
        // Try to connect to database
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.close();
            Log.v("Status", "Database Loaded Successfully!");

            return true;
        }
        catch(Exception e) {
            Log.v("Error... ", String.valueOf(e));
            return false;
        }
    }
}
