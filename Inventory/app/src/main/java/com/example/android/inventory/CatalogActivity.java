package com.example.android.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.inventory.data.InventoryContract.InventoryEntry;
import com.example.android.inventory.data.InventoryDbHelper;

/**
 * Displays list of inventory that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo(loadData());
    }

    private Cursor loadData() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM inventory"
        // to get a Cursor that contains all rows from the inventory table.

        String[] project = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_INVENTORY_NAME,
                InventoryEntry.COLUMN_INVENTORY_COST,
                InventoryEntry.COLUMN_INVENTORY_PRICE,
                InventoryEntry.COLUMN_INVENTORY_CONDITION,
                InventoryEntry.COLUMN_INVENTORY_QUANTITY
        };

        Cursor cursor = db.query(
                InventoryEntry.TABLE_NAME,
                project,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }
    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the inventory database.
     */
    private void displayDatabaseInfo(Cursor cursor) {
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // inventory table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_inventory);
            displayView.setText("Number of rows in inventory database table: " + cursor.getCount() + "\n\n");
            displayView.append(InventoryEntry._ID + "-" +
                            InventoryEntry.COLUMN_INVENTORY_NAME + "-" +
                            InventoryEntry.COLUMN_INVENTORY_COST + "-" +
                            InventoryEntry.COLUMN_INVENTORY_PRICE + "-" +
                            InventoryEntry.COLUMN_INVENTORY_CONDITION + "-" +
                            InventoryEntry.COLUMN_INVENTORY_QUANTITY + "\n");

            int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_NAME);
            int costColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_COST);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_PRICE);
            int conditionColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_CONDITION);
            int quntityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_QUANTITY);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                Double currentCost = cursor.getDouble(costColumnIndex);
                Double currentPrice = cursor.getDouble(priceColumnIndex);
                int currentCondition = cursor.getInt(conditionColumnIndex);
                int currentQuantity = cursor.getInt(quntityColumnIndex);

                displayView.append("\n" + currentID + "-" +
                        currentName + "-" +
                        currentCost + "-" +
                        currentPrice + "-" +
                        currentCondition + "-" +
                        currentQuantity);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    private void insertInventory() {
        mDbHelper = new InventoryDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_INVENTORY_NAME, "Cookie");
        values.put(InventoryEntry.COLUMN_INVENTORY_COST, "6.99");
        values.put(InventoryEntry.COLUMN_INVENTORY_PRICE, "12.99");
        values.put(InventoryEntry.COLUMN_INVENTORY_CONDITION, InventoryEntry.CONDITION_NEW);
        values.put(InventoryEntry.COLUMN_INVENTORY_QUANTITY, 7);

        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);

        Log.v("CatalogActivity", "New row ID " + newRowId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                insertInventory();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}