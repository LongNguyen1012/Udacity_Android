package com.example.android.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Long Nguyen on 1/12/2017.
 */

public final class InventoryContract {

    private InventoryContract() {};

    public static final String CONTENT_AUTHORITY = "com.example.android.inventory";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_INVENTORY = "inventory";

    public static final class InventoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        public final static String TABLE_NAME = "inventory";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_SALE = "sale";
        public final static String COLUMN_PRICE = "price";
        public final static String COLUMN_CONDITION = "condition";
        public final static String COLUMN_QUANTITY = "quantity";
        public final static String COLUMN_SUPPLIER_CONTACT = "supplier";
        public final static String COLUMN_IMAGE = "image";

        public static final int CONDITION_UNKNOWN = 0;
        public static final int CONDITION_NEW = 1;
        public static final int CONDITION_USED = 2;

        /**
         * Returns whether or not the given condition is {@link #CONDITION_UNKNOWN}, {@link #CONDITION_NEW},
         * or {@link #CONDITION_USED}.
         */
        public static boolean isValidCondition(int condition) {
            if (condition == CONDITION_UNKNOWN || condition == CONDITION_NEW || condition == CONDITION_USED) {
                return true;
            }
            return false;
        }

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of inventory.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

    }
}
