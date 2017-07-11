package com.example.android.inventoryapp;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditorActivity extends AppCompatActivity {

    private Uri currentURI = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageInput = null;
    File photo = null;
    Uri mImgURI = null;
    String imgPath;
    String state;

    public void sendCameraIntent(View view) {
        // Check if permission is denied; if so, ask for it
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }
        else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Callback from taking camera picture
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageInput = (Bitmap) extras.get("data");
            ImageView preview = (ImageView)findViewById(R.id.img_preview);
            preview.setImageBitmap(imageInput);
            Log.v("Image data --- ", String.valueOf( extras.get("data") ));
            TextView imageStatus = (TextView) findViewById(R.id.image_check);
            imageStatus.setVisibility(View.VISIBLE);
            this.mImgURI = data.getData();

        }
    }

    private File createImageFile(Bitmap image) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File directory = this.getFilesDir();

        // Save Bitmap
        this.photo = new File(directory, imageFileName + ".png");
        this.photo.createNewFile();
        FileOutputStream fos = new FileOutputStream(this.photo);
        image.compress(Bitmap.CompressFormat.PNG, 90, fos);
        fos.close();

        // Save a file: path for use with ACTION_VIEW intents
        this.imgPath = this.photo.getAbsolutePath();
        return this.photo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Get data and set view accordingly
        Intent intent = getIntent();
        if(intent.getData() == null) {
            state = "add";
            setTitle("Add a Product");
            Button editBtn = (Button)findViewById(R.id.edit_Product);
            Button deleteBtn = (Button)findViewById(R.id.delete_Product);

            editBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
        }
        else {
            state = "edit";
            setTitle("Edit Product");
            Button addBtn = (Button)findViewById(R.id.add_Product);
            addBtn.setVisibility(View.GONE);
            currentURI = intent.getData();

            EditText productNameView = (EditText)findViewById(R.id.product_name);
            EditText productDescView = (EditText)findViewById(R.id.product_desc);
            EditText productPriceView = (EditText)findViewById(R.id.product_price);
            EditText productStockView = (EditText)findViewById(R.id.product_stock);

            productNameView.setText( getIntent().getExtras().getString("productname") );
            productDescView.setText( getIntent().getExtras().getString("productdesc") );
            productPriceView.setText( getIntent().getExtras().getString("productprice") );
            productStockView.setText( getIntent().getExtras().getString("productstock") );
        }

    }

    public void insert(View view) {
        // Select Confirm to confirm action
        RadioButton confBtn = (RadioButton)findViewById(R.id.confirm_radio);
        if(!confBtn.isChecked()){
            Toast.makeText(this, getResources().getString(R.string.conf_msg), Toast.LENGTH_LONG).show();
            return;
        }

        EditText productNameView = (EditText)findViewById(R.id.product_name);
        EditText productDescView = (EditText)findViewById(R.id.product_desc);
        EditText productPriceView = (EditText)findViewById(R.id.product_price);
        EditText productStockView = (EditText)findViewById(R.id.product_stock);

        String productName = productNameView.getText().toString();
        String productDesc = productDescView.getText().toString();

        String productStock_p = productStockView.getText().toString();
        int productStock = 0;

        String productPrice_p = productPriceView.getText().toString();
        int productPrice = 1;

        if( TextUtils.isEmpty( productName ) || productName.length() < 3 ) {
            Toast.makeText(this, getResources().getString(R.string.nameCheck), Toast.LENGTH_LONG).show();
            return;
        }
        if( TextUtils.isEmpty( productDesc ) ) {
            Toast.makeText(this, getResources().getString(R.string.descCheck), Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty( productStock_p )) {
            Toast.makeText(this, getResources().getString(R.string.stockCheck), Toast.LENGTH_LONG).show();
            return;
        }
        else if(!TextUtils.isEmpty( productStock_p )) {
            productStock = Integer.parseInt( productStockView.getText().toString() );
        }

        if(TextUtils.isEmpty( productPrice_p )) {
            Toast.makeText(this, getResources().getString(R.string.priceCheck), Toast.LENGTH_LONG).show();
            return;
        }
        else if(!TextUtils.isEmpty( productPrice_p )) {
            Log.v("not ---", "Empty");
            productPrice = Integer.parseInt( productPriceView.getText().toString() );
        }

        Log.v("price", String.valueOf(productPrice));

        ContentValues mNewValues = new ContentValues();
        mNewValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, productName);
        mNewValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_DESC, productDesc);
        mNewValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE, productPrice);
        mNewValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_STOCK, productStock);

        File img = null;
        try {
            // New products needs an image
            if(imageInput != null) {
                img = createImageFile(imageInput);
                if(img != null) {
                    mNewValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_IMG_PATH, this.imgPath);
                    Log.v("Status Call --- ", "Image Saved" + " | " + this.imgPath + " | " + this.mImgURI);
                }
                else {
                    Toast.makeText(this, getResources().getString(R.string.imgCheck), Toast.LENGTH_LONG).show();
                    return;
                }
            }
            else {
                Toast.makeText(this, getResources().getString(R.string.imgCheck), Toast.LENGTH_LONG).show();
                return;
            }

        }
        catch (Exception e){
            Log.v("Image Error --- ", String.valueOf(e));
        }

        Uri newURI = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, mNewValues);
        Log.v("Insert --- ", "Working");

        Intent intent = new Intent(EditorActivity.this, DisplayActivity.class);
        startActivity(intent);

    }

    public void update(View view) {
        // Select Confirm to confirm action
        RadioButton confBtn = (RadioButton)findViewById(R.id.confirm_radio);
        if(!confBtn.isChecked()){
            Toast.makeText(this, getResources().getString(R.string.conf_msg), Toast.LENGTH_LONG).show();
            return;
        }

        EditText productNameView = (EditText)findViewById(R.id.product_name);
        EditText productDescView = (EditText)findViewById(R.id.product_desc);
        EditText productPriceView = (EditText)findViewById(R.id.product_price);
        EditText productStockView = (EditText)findViewById(R.id.product_stock);

        String productName = productNameView.getText().toString();
        String productDesc = productDescView.getText().toString();

        String productStock_p = productStockView.getText().toString();
        int productStock = 0;

        String productPrice_p = productPriceView.getText().toString();
        int productPrice = 1;

        if( TextUtils.isEmpty( productName ) || productName.length() < 3 ) {
            Toast.makeText(this, getResources().getString(R.string.nameCheck), Toast.LENGTH_LONG).show();
            return;
        }
        if( TextUtils.isEmpty( productDesc ) ) {
            Toast.makeText(this, getResources().getString(R.string.descCheck), Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty( productStock_p )) {
            Toast.makeText(this, getResources().getString(R.string.stockCheck), Toast.LENGTH_LONG).show();
            return;
        }
        else if(!TextUtils.isEmpty( productStock_p )) {
            productStock = Integer.parseInt( productStockView.getText().toString() );
        }

        if(TextUtils.isEmpty( productPrice_p )) {
            Toast.makeText(this, getResources().getString(R.string.priceCheck), Toast.LENGTH_LONG).show();
            return;
        }
        else if(!TextUtils.isEmpty( productPrice_p )) {
            Log.v("not ---", "Empty");
            productPrice = Integer.parseInt( productPriceView.getText().toString() );
        }

        Log.v("price", String.valueOf(productPrice));

        ContentValues mUpdateValues = new ContentValues();
        mUpdateValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, productName);
        mUpdateValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_DESC, productDesc);
        mUpdateValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE, productPrice);
        mUpdateValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_STOCK, productStock);
        File img = null;
        try {
            // editing products don't need to update their image
            if(imageInput != null) {
                img = createImageFile(imageInput);
                if(img != null) {
                    mUpdateValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_IMG_PATH, this.imgPath);
                    Log.v("Status Call --- ", "Image Saved" + " | " + this.imgPath + " | " + this.mImgURI);
                }
                else {
                    Toast.makeText(this, getResources().getString(R.string.imgCheck), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        catch (Exception e){
            Log.v("Image Error --- ", String.valueOf(e));
        }

        String selection = InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(ContentUris.parseId( currentURI ) ) };

        int results = getContentResolver().update(currentURI, mUpdateValues, selection, selectionArgs);
        Log.v("Update --- ", "Working; Results --- " + results);

        Intent intent = new Intent(EditorActivity.this, DisplayActivity.class);
        startActivity(intent);

    }

    public void destroy(View view){
        // Select Confirm to confirm action
        RadioButton confBtn = (RadioButton)findViewById(R.id.confirm_radio);
        if(!confBtn.isChecked()){
            //Toast.makeText(this, getResources().getString(R.string.conf_msg), Toast.LENGTH_LONG).show();
            return;
        }

        String selection = InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(ContentUris.parseId( currentURI ) ) };

        int results = getContentResolver().delete(currentURI, selection, selectionArgs);
        Log.v("Delete --- ", "Working:" + String.valueOf(results));

        Intent intent = new Intent(EditorActivity.this, DisplayActivity.class);
        startActivity(intent);
    }
}
