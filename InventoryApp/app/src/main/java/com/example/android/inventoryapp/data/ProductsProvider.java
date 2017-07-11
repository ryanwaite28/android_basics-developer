package com.example.android.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.inventoryapp.SearchURI;

import static com.example.android.inventoryapp.SearchURI.sURIMatcher;
import static com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

/**
 * Created by Ryan on 7/7/2017.
 */

public class ProductsProvider extends ContentProvider {

    public static final String LOG_TAG = ProductsProvider.class.getSimpleName();

    // Database Helper Object
    private DatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper( getContext() );
        return true;
    }
    @Override
    public String getType(Uri uri) {
        int match = sURIMatcher.match(uri);
        switch (match)
        {
            case SearchURI.PRODUCTS:
                return InventoryEntry.CONTENT_LIST_TYPE;

            case SearchURI.PRODUCT_ID:
                return InventoryEntry.CONTENT_LIST_TYPE;

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    // CRUD Methods ( CREATE | READ | UPDATE | DESTROY )
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri newURI;
        long _id = db.insert(InventoryEntry.TABLE_NAME, null, contentValues);
        newURI = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, _id);
        Log.v("NEW URI --- ", String.valueOf(newURI));

        getContext().getContentResolver().notifyChange(uri, null);
        return newURI;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        int match = SearchURI.sURIMatcher.match(uri);
        Log.v("Uri is --- ", String.valueOf(uri));
        Log.v("Match is --- ", String.valueOf(match));
        switch (match){
            case SearchURI.PRODUCTS:
                cursor = db.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case SearchURI.PRODUCT_ID:
                selection = InventoryEntry.COLUMN_PRODUCT_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(InventoryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String projection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated = db.update(InventoryEntry.TABLE_NAME, contentValues, projection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;
        final int match = sURIMatcher.match(uri);

        switch (match) {
            case SearchURI.PRODUCTS:
                // Delete all from products
                rowsDeleted = db.delete(InventoryEntry.TABLE_NAME, null, null);
                break;
            case SearchURI.PRODUCT_ID:
                // Delete a single row given by the ID in the URI
                rowsDeleted = db.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;


    }

}
