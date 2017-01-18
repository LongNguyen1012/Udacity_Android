package com.example.android.inventory;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.renderscript.Double2;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventory.CatalogActivity;
import com.example.android.inventory.data.InventoryContract;
import com.example.android.inventory.data.InventoryContract.InventoryEntry;

import static android.R.attr.id;
import static com.example.android.inventory.R.id.fab;
import static com.example.android.inventory.R.id.price;
import static com.example.android.inventory.R.id.quantity;
import static com.example.android.inventory.R.id.sold;

/**
 * {@link InventoryCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class InventoryCursorAdapter extends CursorAdapter {

    public static final int GET_FROM_GALLERY = 3;

    /**
     * Constructs a new {@link InventoryCursorAdapter}.
     *
     */
    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        final TextView IdTextView = (TextView) view.findViewById(R.id.itemId);
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView pricetextView = (TextView) view.findViewById(R.id.price);
        final TextView soldtextView = (TextView) view.findViewById(R.id.sold);
        ImageView imgView = (ImageView) view.findViewById(R.id.img);

        int IDColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_NAME);
        int saleColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SALE);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);
        int imgColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_IMAGE);

        long itemId = cursor.getLong(IDColumnIndex);
        final String itemName = cursor.getString(nameColumnIndex);
        int sold = cursor.getInt(saleColumnIndex);
        Double price = cursor.getDouble(priceColumnIndex);
        int quantity = cursor.getInt(quantityColumnIndex);
        byte[] img = cursor.getBlob(imgColumnIndex);

        String idString = "ID : " + Long.toString(itemId);
        String soldString = "Sold : " + Integer.toString(sold);
        String quantityString = "Quantity : " + Integer.toString(quantity);
        String priceString = "Price : " + Double.toString(price);
        if (img != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
            imgView.setImageBitmap(bmp);
        }

        IdTextView.setText(idString);
        nameTextView.setText(itemName);
        soldtextView.setText(soldString);
        quantityTextView.setText(quantityString);
        pricetextView.setText(priceString);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_track_sale);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemId = IdTextView.getText().toString();
                String sold = soldtextView.getText().toString();
                String quantity = quantityTextView.getText().toString();

                itemId = itemId.substring(5, itemId.length());
                long Id = Long.parseLong(itemId);
                sold = sold.substring(7, sold.length());
                quantity = quantity.substring(11, quantity.length());

                int updatedsold = Integer.parseInt(sold) + 1;
                int updatedquantity = Integer.parseInt(quantity) - 1;

                Uri newUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, Id);

                ContentValues values = new ContentValues();
                values.put(InventoryEntry.COLUMN_SALE, updatedsold);
                values.put(InventoryEntry.COLUMN_QUANTITY, updatedquantity);

                context.getContentResolver().update(newUri, values, null, null);
            }
        });
    }
}