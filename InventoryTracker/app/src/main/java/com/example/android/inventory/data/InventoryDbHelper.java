package com.example.android.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.inventory.data.InventoryContract.InventoryEntry;

/**
 * Created by Long Nguyen on 1/13/2017.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public InventoryDbHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME + "(" +
                InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InventoryEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                InventoryEntry.COLUMN_SALE + " INTEGER NOT NULL, " +
                InventoryEntry.COLUMN_PRICE + " DOUBLE NOT NULL, " +
                InventoryEntry.COLUMN_CONDITION + " INTEGER NOT NULL, " +
                InventoryEntry.COLUMN_SUPPLIER_CONTACT + " TEXT NOT NULL, " +
                InventoryEntry.COLUMN_IMAGE + " BLOB, " +
                InventoryEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
