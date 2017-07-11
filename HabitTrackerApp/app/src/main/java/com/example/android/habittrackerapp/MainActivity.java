package com.example.android.habittrackerapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.habittrackerapp.data.DatabaseHelper;
import com.example.android.habittrackerapp.data.HabitContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertIntoDatabase("Test Action", 1);
        readDatabase(1);
        Log.v("Database --- ", "Admit One");
    }

    // Insert Method
    public void insertIntoDatabase(String action, int daily_occurrences){
        // Start Database connection
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Make ContentValues Object
        ContentValues values = new ContentValues();

        // Insert data from method input parameters
        values.put(HabitContract.HabitEntry.COLUMN_ACTION, action);
        values.put(HabitContract.HabitEntry.COLUMN_DAILY_OCCURRENCES, daily_occurrences);
        db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        db.close();
    }

    // Read Method
    public Cursor readDatabase(int id){
        // Start Database connection
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Table Fields to query
        String[] projection = {
                HabitContract.HabitEntry.COLUMN_ID,
                HabitContract.HabitEntry.COLUMN_ACTION,
                HabitContract.HabitEntry.COLUMN_DAILY_OCCURRENCES
        };

        // Where clause
        String selection = HabitContract.HabitEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        // Get & return Cursor Object from query
        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        return cursor;
    }
}
