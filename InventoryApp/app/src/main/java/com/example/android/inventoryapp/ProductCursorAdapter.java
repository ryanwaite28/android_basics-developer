package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryContract;

import java.io.File;

/**
 * Created by Ryan on 7/8/2017.
 */

public class ProductCursorAdapter extends CursorAdapter {

    private Context context;

    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get Views
        ImageView productImage = (ImageView)view.findViewById(R.id.product_img);
        TextView productNameView = (TextView) view.findViewById(R.id.product_name);
        TextView productStockView = (TextView) view.findViewById(R.id.product_stock);
        TextView productPriceView = (TextView) view.findViewById(R.id.product_price);
        Button decreaseBtn = (Button)view.findViewById(R.id.decrease_stock);
        Button increaseBtn = (Button)view.findViewById(R.id.increase_stock);
        Button detailsBtn = (Button)view.findViewById(R.id.product_details);

        // get data
        int productID = cursor.getInt(cursor.getColumnIndexOrThrow( InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID ));
        String imgPath = cursor.getString(cursor.getColumnIndexOrThrow( InventoryContract.InventoryEntry.COLUMN_PRODUCT_IMG_PATH ));
        String productName = cursor.getString(cursor.getColumnIndexOrThrow( InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME ));
        String productDesc = cursor.getString(cursor.getColumnIndexOrThrow( InventoryContract.InventoryEntry.COLUMN_PRODUCT_DESC ));
        int productPrice = cursor.getInt(cursor.getColumnIndexOrThrow( InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE ));
        int productStock = cursor.getInt(cursor.getColumnIndexOrThrow( InventoryContract.InventoryEntry.COLUMN_PRODUCT_STOCK ));
        Uri uri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI,
                cursor.getLong(cursor.getColumnIndexOrThrow( InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID )));
        File imgFile = new File(imgPath);
        Uri imgURI = Uri.fromFile(imgFile);

        // set image if product has one
        if(imgFile.exists()) {
            Log.v("image file" , String.valueOf(imgFile));
            productImage.setImageURI(imgURI);
            Log.v("Image does exist", "file | " + String.valueOf(imgURI));
        }
        else {
            Log.v("bitmap --- ", "Could not find file | " + imgPath);
        }

        // set decrease button text, based on stock
        if(productStock > 0) {
            decreaseBtn.setText(context.getResources().getString(R.string.sale));
        }
        else {
            decreaseBtn.setText(context.getResources().getString(R.string.out_of_stock));
        }

        // Set data tags to buttons
        decreaseBtn.setTag(R.id.productid, productID);
        decreaseBtn.setTag(R.id.productstock, productStock);
        decreaseBtn.setTag(R.id.productname, productName);
        decreaseBtn.setTag(R.id.productprice, productPrice);
        decreaseBtn.setTag(R.id.productdesc, productDesc);
        decreaseBtn.setTag(R.id.productimg, imgURI);
        decreaseBtn.setTag(R.id.producturi, uri);

        increaseBtn.setTag(R.id.productid, productID);
        increaseBtn.setTag(R.id.productstock, productStock);
        increaseBtn.setTag(R.id.productname, productName);
        increaseBtn.setTag(R.id.productprice, productPrice);
        increaseBtn.setTag(R.id.productdesc, productDesc);
        increaseBtn.setTag(R.id.productimg, imgURI);
        increaseBtn.setTag(R.id.producturi, uri);

        detailsBtn.setTag(R.id.productid, productID);
        detailsBtn.setTag(R.id.productstock, productStock);
        detailsBtn.setTag(R.id.productname, productName);
        detailsBtn.setTag(R.id.productprice, productPrice);
        detailsBtn.setTag(R.id.productdesc, productDesc);
        detailsBtn.setTag(R.id.productimg, imgURI);
        detailsBtn.setTag(R.id.producturi, uri);

        // Populate fields with extracted properties
        productNameView.setText(productName);
        productStockView.setText(context.getResources().getString(R.string.stockprefix) + " " + String.valueOf(productStock));
        productPriceView.setText(context.getResources().getString(R.string.currency_sign) + String.valueOf(productPrice));
    }
}
