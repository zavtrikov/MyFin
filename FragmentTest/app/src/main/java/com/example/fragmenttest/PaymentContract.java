package com.example.fragmenttest;

import android.provider.BaseColumns;

public class PaymentContract {

    private PaymentContract() {
}

public static final class PaymentEntry implements BaseColumns {
    public static final String TABLE_Payment ="paymentListt";
    public static final String COLUMN_CATEGORY="category";
    public static final String COLUMN_SUBCATEGORY="subcategory";
    public static final String COLUMN_WEIGHT="weight";
    public static final String COLUMN_PRICE="price";
    public static final String COLUMN_DATE="date";


}
}