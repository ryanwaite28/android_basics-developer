package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Ryan on 7/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "products.db";
    public static final int DATABASE_VERSION = 4;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute the SQL statement
        Log.v("Create Table String", InventoryContract.InventoryEntry.CREATE_TABLE);
        db.execSQL(InventoryContract.InventoryEntry.CREATE_TABLE);
        Log.v("message --- ", "database created");
    }

    // Called when database is upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Called when there are changes
        db.execSQL("DROP DATABASE IF EXISTS products");
        Log.v("message --- ", "database dropped");
    }

}