package com.example.android.inventoryapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_order);

        // Get Info from Intent and setup view
        productURI = getIntent().getData();
        productID = getIntent().getExtras().getInt("id");
        productName = getIntent().getExtras().getString("name");
        productDesc = getIntent().getExtras().getString("desc");
        productImgPath = getIntent().getExtras().getString("img");
        productImg = Uri.parse(getIntent().getExtras().getString("img"));
        productPrice = getIntent().getExtras().getInt("price");
        productStock = getIntent().getExtras().getInt("stock");

        setTitle(getResources().getString(R.string.orderLabel) + " " + productName);

        ImageView productImage = (ImageView)findViewById(R.id.order_product_img);
        TextView productNameView = (TextView) findViewById(R.id.order_product_name);
        TextView productDescView = (TextView) findViewById(R.id.order_product_desc);
        TextView productPriceView = (TextView) findViewById(R.id.order_product_price);
        TextView productStockView = (TextView) findViewById(R.id.order_product_stock_available);

        productImage.setImageURI(productImg);
        productNameView.setText(productName);
        productDescView.setText(productDesc);
        productPriceView.setText(getResources().getString(R.string.currency_sign) + String.valueOf(productPrice));
        productStockView.setText(getResources().getString(R.string.stock_available) + " " + productStock);

    }

    public void placeOrder(View view){
        EditText productStockView = (EditText)findViewById(R.id.edit_stock_order_amount);
        String amountString = productStockView.getText().toString();
        if(TextUtils.isEmpty(amountString)) {
            return;
        }
        int amountInput = Integer.parseInt(productStockView.getText().toString());
        if(amountInput <= 0) {
            Toast.makeText(this, getResources().getString(R.string.minimumError), Toast.LENGTH_LONG).show();
            return;
        }
        else if(amountInput > productStock) {
            Toast.makeText(this, getResources().getString(R.string.maximunError) + " " + productStock, Toast.LENGTH_LONG).show();
            return;
        }

        // Send Order Conf (Email Intent)
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));  // This ensures only Email apps respond
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.orderConf));
        intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.thanks) + " " + productName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
