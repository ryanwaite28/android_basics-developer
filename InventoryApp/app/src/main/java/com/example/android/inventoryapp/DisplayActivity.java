package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.inventoryapp.data.InventoryContract;

public class DisplayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PRODUCT_LOADER = 1;

    ProductCursorAdapter productCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ListView listView = (ListView) findViewById(R.id.main_list_view);
        productCursorAdapter = new ProductCursorAdapter(this, null);
        listView.setAdapter(productCursorAdapter);
        listView.setEmptyView(findViewById(R.id.empty_list_item));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long _id) {
                // Each item has click listener. Set intent with item's data
                Intent intent = new Intent(DisplayActivity.this, EditorActivity.class);
                Uri currentURI = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, _id);
                Cursor cursor = (Cursor) productCursorAdapter.getItem(position);

                Log.v("cursor --- ", String.valueOf(cursor));

                int productID = cursor.getInt(cursor.getColumnIndex( InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID ));
                String productName = cursor.getString(cursor.getColumnIndex( InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME ));
                String productImg = cursor.getString(cursor.getColumnIndex( InventoryContract.InventoryEntry.COLUMN_PRODUCT_IMG_PATH ));
                String productDesc = cursor.getString(cursor.getColumnIndex( InventoryContract.InventoryEntry.COLUMN_PRODUCT_DESC ));
                int productPrice = cursor.getInt(cursor.getColumnIndex( InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE ));
                int productStock = cursor.getInt(cursor.getColumnIndex( InventoryContract.InventoryEntry.COLUMN_PRODUCT_STOCK ));

                intent.setData(currentURI);
                intent.putExtra("productid", productID);
                intent.putExtra("productname", productName);
                intent.putExtra("productdesc", productDesc);
                intent.putExtra("productimgpath", productImg);
                intent.putExtra("productprice", String.valueOf(productPrice));
                intent.putExtra("productstock", String.valueOf(productStock));
                startActivity( intent );
            }
        });

        // Start Loader
        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Set the columns wanted
        String[] projection = {
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_DESC,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_IMG_PATH,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_STOCK
        };

        return new CursorLoader(DisplayActivity.this,
                InventoryContract.InventoryEntry.CONTENT_URI,
                projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        productCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productCursorAdapter.swapCursor(null);
    }

    public void addProductView(View view) {
        Intent intent = new Intent(DisplayActivity.this, EditorActivity.class);
        startActivity(intent );
    }

    public void stockDecrement(View view) {
        // Decrease Stock and update the database
        Log.v("Product ID --- ", String.valueOf( view.getTag(R.id.productid) ));
        Log.v("Product Stock --- ", String.valueOf( view.getTag(R.id.productstock) ));

        int productStock = (int)view.getTag(R.id.productstock);
        if(productStock == 0) {
            // Negative numbers are not allowed
            Log.v("Decrement Action --- ", "Forbidden");
            return;
        }
        int updatedCount = -- productStock;

        ContentValues mUpdateValues = new ContentValues();
        mUpdateValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_STOCK, updatedCount);
        Uri thisProductURI = (Uri)view.getTag(R.id.producturi);

        String selection = InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = { String.valueOf( view.getTag(R.id.productid) ) };
        int results = getContentResolver().update(thisProductURI, mUpdateValues, selection, selectionArgs);
        Log.v("Update Successful --- ", "Results: " + String.valueOf( results ));
    }

    public void stockIncrement(View view) {
        // Increase Stock and update the database
        Log.v("Product ID --- ", String.valueOf( view.getTag(R.id.productid) ));
        Log.v("Product Stock --- ", String.valueOf( view.getTag(R.id.productstock) ));

        int productStock = (int)view.getTag(R.id.productstock);
        int updatedCount = ++ productStock;

        ContentValues mUpdateValues = new ContentValues();
        mUpdateValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_STOCK, updatedCount);
        Uri thisProductURI = (Uri)view.getTag(R.id.producturi);

        String selection = InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = { String.valueOf( view.getTag(R.id.productid) ) };
        int results = getContentResolver().update(thisProductURI, mUpdateValues, selection, selectionArgs);
        Log.v("Update Successful --- ", "Results: " + String.valueOf( results ));
    }

    public void seeDetails(View view) {;

        // Set Data to intent
        Intent intent = new Intent(DisplayActivity.this, DetailsActivity.class);
        intent.setData((Uri)view.getTag(R.id.producturi));
        intent.putExtra("id", (int)view.getTag(R.id.productid));
        intent.putExtra("name", String.valueOf( view.getTag(R.id.productname)));
        intent.putExtra("stock", (int)view.getTag(R.id.productstock));
        intent.putExtra("price", (int)view.getTag(R.id.productprice));
        intent.putExtra("desc", String.valueOf( view.getTag(R.id.productdesc)));
        intent.putExtra("img", String.valueOf( view.getTag(R.id.productimg)));
        startActivity( intent );

    }
}
