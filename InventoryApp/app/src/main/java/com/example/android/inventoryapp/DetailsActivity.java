package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract;

public class DetailsActivity extends AppCompatActivity {

    // declare variables
    Uri productURI;
    int productID;
    String productName;
    String productDesc;
    String productImgPath;
    Uri productImg;
    int productPrice;
    int productStock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // collect data from intent
        productURI = getIntent().getData();
        productID = getIntent().getExtras().getInt("id");
        productName = getIntent().getExtras().getString("name");
        productDesc = getIntent().getExtras().getString("desc");
        productImgPath = getIntent().getExtras().getString("img");
        productImg = Uri.parse(getIntent().getExtras().getString("img"));
        productPrice = getIntent().getExtras().getInt("price");
        productStock = getIntent().getExtras().getInt("stock");

        // get views
        ImageView productImage = (ImageView)findViewById(R.id.product_img);
        TextView productNameView = (TextView) findViewById(R.id.product_name);
        TextView productDescView = (TextView) findViewById(R.id.product_desc);
        TextView productPriceView = (TextView) findViewById(R.id.product_price);
        TextView productStockView = (TextView) findViewById(R.id.product_stock);
        Button decreaseBtn = (Button)findViewById(R.id.decrease_stock);

        // set views
        productImage.setImageURI(productImg);
        productNameView.setText(productName);
        productDescView.setText(productDesc);
        productPriceView.setText(getResources().getString(R.string.currency_sign) + String.valueOf(productPrice));
        productStockView.setText(getResources().getString(R.string.stockprefix) + " " + String.valueOf(productStock));

        if(productStock > 0) {
            decreaseBtn.setText(getResources().getString(R.string.sale));
        }
        else {
            decreaseBtn.setText(getResources().getString(R.string.out_of_stock));
        }

    }

    public void stockDecrement(View view) {
        // if out of stock, forbid order
        Button decreaseBtn = (Button)findViewById(R.id.decrease_stock);

        if(productStock <= 0) {
            return;
        }

        int updatedCount = -- productStock;

        ContentValues mUpdateValues = new ContentValues();
        mUpdateValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_STOCK, updatedCount);
        Uri thisProductURI = productURI;

        String selection = InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = { String.valueOf( productID ) };
        int results = getContentResolver().update(thisProductURI, mUpdateValues, selection, selectionArgs);
        Log.v("Update Successful --- ", "Results: " + String.valueOf( results ));

        productStock = updatedCount;
        TextView productStockView = (TextView) findViewById(R.id.product_stock);
        productStockView.setText(getResources().getString(R.string.stockprefix) + " " + String.valueOf(productStock));
        if(productStock > 0) {
            decreaseBtn.setText(getResources().getString(R.string.sale));
        }
        else {
            decreaseBtn.setText(getResources().getString(R.string.out_of_stock));
        }
    }

    public void stockIncrement(View view) {
        // Increase Stock and update the database
        Button decreaseBtn = (Button)findViewById(R.id.decrease_stock);

        int updatedCount = ++ productStock;

        ContentValues mUpdateValues = new ContentValues();
        mUpdateValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_STOCK, updatedCount);
        Uri thisProductURI = productURI;

        String selection = InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = { String.valueOf( productID ) };
        int results = getContentResolver().update(thisProductURI, mUpdateValues, selection, selectionArgs);
        Log.v("Update Successful --- ", "Results: " + String.valueOf( results ));

        productStock = updatedCount;
        TextView productStockView = (TextView) findViewById(R.id.product_stock);
        productStockView.setText(getResources().getString(R.string.stockprefix) + " " + String.valueOf(productStock));
        if(productStock > 0) {
            decreaseBtn.setText(getResources().getString(R.string.sale));
        }
    }

    public void destroy(View view){
        // Select Confirm to confirm action
        RadioButton confBtn = (RadioButton)findViewById(R.id.confirm_radio);
        if(!confBtn.isChecked()){
            Toast.makeText(this, getResources().getString(R.string.conf_msg), Toast.LENGTH_LONG).show();
            return;
        }

        String selection = InventoryContract.InventoryEntry.COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = { String.valueOf( productID ) };

        int results = getContentResolver().delete(productURI, selection, selectionArgs);
        Log.v("Delete --- ", "Working:" + String.valueOf(results));

        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }

    public void orderProduct(View view) {
        // if out of stock, forbid order
        if(productStock <= 0) {
            Toast.makeText(this, getResources().getString(R.string.stockError), Toast.LENGTH_LONG).show();
            return;
        }

        // Set Data to intent
        Intent intent = new Intent(DetailsActivity.this, OrderActivity.class);
        intent.setData(this.productURI);
        intent.putExtra("id", productID);
        intent.putExtra("name", productName);
        intent.putExtra("stock", productStock);
        intent.putExtra("price", productPrice);
        intent.putExtra("desc", productDesc);
        intent.putExtra("img", productImgPath);
        startActivity( intent );

    }
}
