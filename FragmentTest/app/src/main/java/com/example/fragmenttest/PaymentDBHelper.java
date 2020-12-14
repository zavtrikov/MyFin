package com.example.fragmenttest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fragmenttest.GroceryContract.GroceryEntry;

public class PaymentDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "paymentlist2.db";
    public static final int DATABASE_VERSION = 4;


    public PaymentDBHelper(Context context)

    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE " +
                PaymentContract.PaymentEntry.TABLE_Payment + " (" +
                PaymentContract.PaymentEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                PaymentContract.PaymentEntry.COLUMN_SUBCATEGORY + " TEXT NOT NULL, " +
                PaymentContract.PaymentEntry.COLUMN_PRICE + " REAL NOT NULL, " +
                PaymentContract.PaymentEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                PaymentContract.PaymentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PaymentContract.PaymentEntry.COLUMN_WEIGHT + " TEXT  ) " ;

        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + GroceryEntry.TABLE_Grocy);
        onCreate(db);


    }
}
