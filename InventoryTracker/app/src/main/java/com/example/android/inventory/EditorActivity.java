/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.inventory;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.inventory.data.InventoryContract;
import com.example.android.inventory.data.InventoryContract.InventoryEntry;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.R.attr.id;
import static com.example.android.inventory.InventoryCursorAdapter.GET_FROM_GALLERY;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FloatingActionButton fab;

    private FloatingActionButton fabimg;

    private ImageView mImage;

    private Button increase;

    private Button decrease;

    private EditText mNameEditText;

    private EditText mSaleEditText;

    private EditText mPhoneEditText;

    private EditText mQuantityEditText;

    private Spinner mConditionSpinner;

    private EditText mPriceEditText;

    private int mCondition = 0;

    private static final int EXISTING_INVENTORY_LOADER = 0;

    public static final int GET_FROM_GALLERY_NEW_ITEM = 1;

    private Uri imgUpdate;

    private byte[] byteArray;

    private Uri mCurrentInventoryUri;

    private boolean mInventoryHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mInventoryHasChanged = true;
            return false;
        }
    };

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteInventory();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deleteInventory() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentInventoryUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentInventoryUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentInventoryUri, null, null);

            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_item_successful),
                        Toast.LENGTH_SHORT).show();
            }

            // Close the activity
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        mCurrentInventoryUri = intent.getData();

        // Find all relevant views that we will need to read user input from

        mNameEditText = (EditText) findViewById(R.id.edit_item_name);
        mSaleEditText = (EditText) findViewById(R.id.edit_item_sale);
        mQuantityEditText = (EditText) findViewById(R.id.edit_text_item_quantity);
        mPhoneEditText = (EditText) findViewById(R.id.edit_item_phone);
        mPriceEditText = (EditText) findViewById(R.id.edit_item_price);
        mConditionSpinner = (Spinner) findViewById(R.id.spinner_gender);
        fabimg = (FloatingActionButton) findViewById(R.id.fab_add_image);
        mImage = (ImageView) findViewById(R.id.item_image);

        mNameEditText.setOnTouchListener(mTouchListener);
        mSaleEditText.setOnTouchListener(mTouchListener);
        mPhoneEditText.setOnTouchListener(mTouchListener);
        mPriceEditText.setOnTouchListener(mTouchListener);
        mConditionSpinner.setOnTouchListener(mTouchListener);
        mQuantityEditText.setOnTouchListener(mTouchListener);
        fabimg.setOnTouchListener(mTouchListener);

        setupSpinner();

        if (mCurrentInventoryUri == null) {
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a pet that hasn't been created yet.)
            invalidateOptionsMenu();
            setTitle(getString(R.string.editor_activity_title_new_item));

            increase = (Button) findViewById(R.id.increase_button);
            decrease = (Button) findViewById(R.id.decrease_button);
            fab = (FloatingActionButton) findViewById(R.id.fab_order);

            fab.setVisibility(View.INVISIBLE);
            decrease.setVisibility(View.GONE);
            increase.setVisibility(View.GONE);

        } else {
            setTitle(getString(R.string.editor_activity_title_edit_item));
            getLoaderManager().initLoader(EXISTING_INVENTORY_LOADER, null, this);

            increase = (Button) findViewById(R.id.increase_button);
            decrease = (Button) findViewById(R.id.decrease_button);
            fab = (FloatingActionButton) findViewById(R.id.fab_order);

            // Setup FAB to open EditorActivity
            fab.setVisibility(View.VISIBLE);
            decrease.setVisibility(View.VISIBLE);
            increase.setVisibility(View.VISIBLE);
        }

        fabimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgUpdate = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                        GET_FROM_GALLERY_NEW_ITEM);
            }
        });
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter conditionSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_condition_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        conditionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mConditionSpinner.setAdapter(conditionSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mConditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.condition_new))) {
                        mCondition = InventoryEntry.CONDITION_NEW; // Male
                    } else if (selection.equals(getString(R.string.condition_used))) {
                        mCondition = InventoryEntry.CONDITION_USED; // Female
                    } else {
                        mCondition = InventoryContract.InventoryEntry.CONDITION_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCondition = 0; // Unknown
            }
        });
    }

    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mInventoryHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentInventoryUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    private void saveInventory(Uri mCurrnentInventoryUri) {

        String nameString = mNameEditText.getText().toString().trim();
        String saleString = mSaleEditText.getText().toString().trim();
        String phoneString = mPhoneEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();

        if (mCurrnentInventoryUri == null ||
                TextUtils.isEmpty(nameString) || TextUtils.isEmpty(saleString) ||
                TextUtils.isEmpty(priceString) || TextUtils.isEmpty(quantityString) ||
                TextUtils.isEmpty(phoneString) || mImage.getDrawable() == null ||
                mCondition == InventoryEntry.CONDITION_UNKNOWN) {return;}

        if (mImage.getDrawable() != null) {
            mImage.setDrawingCacheEnabled(true);
            mImage.buildDrawingCache();
            Bitmap bm = mImage.getDrawingCache();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_NAME, nameString);
        values.put(InventoryEntry.COLUMN_SALE, saleString);
        values.put(InventoryEntry.COLUMN_SUPPLIER_CONTACT, phoneString);
        values.put(InventoryEntry.COLUMN_PRICE, priceString);
        values.put(InventoryEntry.COLUMN_IMAGE, byteArray);
        values.put(InventoryEntry.COLUMN_CONDITION, mCondition);

        // If the quantity is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int quantity = 0;
        if (!TextUtils.isEmpty(quantityString)) {
            quantity = Integer.parseInt(quantityString);
        }
        values.put(InventoryEntry.COLUMN_QUANTITY, quantity);

        if (mCurrnentInventoryUri == null) {

            Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);
            // Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentInventoryUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentInventoryUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrnentInventoryUri, values, null, null);
            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Do nothing for now
                saveInventory(mCurrentInventoryUri);
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mInventoryHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_NAME,
                InventoryEntry.COLUMN_SALE,
                InventoryEntry.COLUMN_SUPPLIER_CONTACT,
                InventoryEntry.COLUMN_PRICE,
                InventoryEntry.COLUMN_CONDITION,
                InventoryEntry.COLUMN_IMAGE,
                InventoryEntry.COLUMN_QUANTITY};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentInventoryUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_NAME);
            int saleColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SALE);
            int phoneColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_CONTACT);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
            int conditionColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_CONDITION);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);
            int imgColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_IMAGE);

            // Extract out the value from the Cursor for the given column index
            final long id = cursor.getLong(idColumnIndex);
            String name = cursor.getString(nameColumnIndex);
            final String phone = cursor.getString(phoneColumnIndex);
            int sale = cursor.getInt(saleColumnIndex);
            Double price = cursor.getDouble(priceColumnIndex);
            int condition = cursor.getInt(conditionColumnIndex);
            final int quantity = cursor.getInt(quantityColumnIndex);
            byte[] imgBlob = cursor.getBlob(imgColumnIndex);

            // Update the views on the screen with the values from the database
            mNameEditText.setText(name);
            mPhoneEditText.setText(phone);
            mSaleEditText.setText(Integer.toString(sale));
            mPriceEditText.setText(Double.toString(price));
            mQuantityEditText.setText(Integer.toString(quantity));

            if (imgBlob != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(imgBlob, 0, imgBlob.length);
                mImage.setImageBitmap(bmp);
            }

            // Gender is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 is Unknown, 1 is Male, 2 is Female).
            // Then call setSelection() so that option is displayed on screen as the current selection.
            switch (condition) {
                case InventoryEntry.CONDITION_NEW:
                    mConditionSpinner.setSelection(1);
                    break;
                case InventoryEntry.CONDITION_USED:
                    mConditionSpinner.setSelection(2);
                    break;
                default:
                    mConditionSpinner.setSelection(0);
                    break;
            }

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
            });

            fabimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgUpdate = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);
                    startActivityForResult(new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                            GET_FROM_GALLERY);
                }
            });

            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int newquantity =  quantity + 1;

                    Uri newUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);

                    ContentValues values = new ContentValues();
                    values.put(InventoryEntry.COLUMN_QUANTITY, newquantity);

                    getContentResolver().update(newUri, values, null, null);
                }
            });

            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int newquantity =  quantity - 1;

                    Uri newUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);

                    ContentValues values = new ContentValues();
                    values.put(InventoryEntry.COLUMN_QUANTITY, newquantity);

                    getContentResolver().update(newUri, values, null, null);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                byte[] imgdata = getBitmapAsByteArray(bitmap);

                ContentValues values = new ContentValues();
                values.put(InventoryEntry.COLUMN_IMAGE, imgdata);

                getContentResolver().update(imgUpdate, values, null, null);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(requestCode==GET_FROM_GALLERY_NEW_ITEM && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                byte[] imgdata = getBitmapAsByteArray(bitmap);

                if (imgdata != null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length);
                    mImage.setImageBitmap(bmp);
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
