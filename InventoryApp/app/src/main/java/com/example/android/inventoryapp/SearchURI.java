package com.example.android.inventoryapp;

import android.content.UriMatcher;

/**
 * Created by Ryan on 7/8/2017.
 */

public final class SearchURI {
    public static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int PRODUCTS = 100;
    public static final int PRODUCT_ID = 101;

    static
    {
        sURIMatcher.addURI("com.example.android.inventoryapp", "products", PRODUCTS);
        sURIMatcher.addURI("com.example.android.inventoryapp", "products/#", PRODUCT_ID);
    }
}
