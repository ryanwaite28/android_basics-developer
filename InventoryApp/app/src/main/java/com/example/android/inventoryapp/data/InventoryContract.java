package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ryan on 7/7/2017.
 */

public final class InventoryContract {
    public InventoryContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class InventoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(InventoryContract.BASE_CONTENT_URI, InventoryEntry.TABLE_NAME);
        public static final int STOCK_MIN = 0;
        public static final int PRICE_MIN = 1;
        public static final String TABLE_NAME = "products"; // Same as PATH

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String COLUMN_PRODUCT_ID = "_id";
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_PRODUCT_DESC = "product_description";
        public static final String COLUMN_PRODUCT_PRICE = "product_price";
        public static final String COLUMN_PRODUCT_IMG_PATH = "product_img_path";
        public static final String COLUMN_PRODUCT_STOCK = "product_stock";

        public static final String CREATE_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME + " ("
                + InventoryEntry.COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL UNIQUE, "
                + InventoryEntry.COLUMN_PRODUCT_DESC + " TEXT NOT NULL, "
                + InventoryEntry.COLUMN_PRODUCT_IMG_PATH + " TEXT DEFAULT '', "
                + InventoryEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL CHECK (" + InventoryEntry.COLUMN_PRODUCT_PRICE + " >= " + PRICE_MIN + "), "
                + InventoryEntry.COLUMN_PRODUCT_STOCK + " INTEGER NOT NULL CHECK (" + InventoryEntry.COLUMN_PRODUCT_STOCK + " >= " + STOCK_MIN + "));";
    }
}
