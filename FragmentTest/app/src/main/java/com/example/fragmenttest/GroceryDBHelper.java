package com.example.fragmenttest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.fragmenttest.GroceryContract.*;
import androidx.annotation.Nullable;

public class GroceryDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "grocerylist.db";
    public static final int DATABASE_VERSION = 6;


    public GroceryDBHelper( Context context)

    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE " +
                GroceryEntry.TABLE_Grocy + " (" +
                GroceryEntry.COLUMN_BALANCE + " REAL NOT NULL, " +
                GroceryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GroceryEntry.COLUMN_B_DATE + " TEXT, " +
                GroceryEntry.COLUMN_B_COMMENT + " TEXT  ) " ;

        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + GroceryEntry.TABLE_Grocy);
        onCreate(db);


    }
}
