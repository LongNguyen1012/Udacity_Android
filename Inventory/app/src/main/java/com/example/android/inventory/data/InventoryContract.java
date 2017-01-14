package com.example.android.inventory.data;

import android.provider.BaseColumns;

/**
 * Created by Long Nguyen on 1/12/2017.
 */

public final class InventoryContract {

    private InventoryContract() {};

    public static final class InventoryEntry implements BaseColumns {

        public final static String TABLE_NAME = "inventory";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_INVENTORY_NAME = "name";
        public final static String COLUMN_INVENTORY_PRICE = "price";
        public final static String COLUMN_INVENTORY_COST = "cost";
        public final static String COLUMN_INVENTORY_CONDITION = "condition";
        public final static String COLUMN_INVENTORY_QUANTITY = "quantity";

        public static final int CONDITION_UNKNOWN = 0;
        public static final int CONDITION_NEW = 1;
        public static final int CONDITION_USED = 2;
    }
}
