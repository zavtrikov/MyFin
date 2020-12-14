package com.example.fragmenttest;

import android.provider.BaseColumns;

public class GroceryContract {

    private GroceryContract() {
}

public static final class GroceryEntry implements BaseColumns {
    public static final String TABLE_Grocy ="grocyList";
    public static final String COLUMN_BALANCE="balance";
    public static final String COLUMN_B_COMMENT="comment";
    public static final String COLUMN_B_DATE="date";
}
}