package com.example.fragmenttest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.fragmenttest.Dialog_balance.*;

public class BalanceFragment extends Fragment  {
    @Nullable

    private Button button_addpayment;
    public static TextView textview_balance ;
    private Button button_addbalance;
    private SQLiteDatabase mDatabase;
    private double total_pluse;
    private double total_minuse;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_balance, container, false);
        GroceryDBHelper dbHelper =new GroceryDBHelper(getContext());
        mDatabase = dbHelper.getReadableDatabase();
        button_addbalance = rootView.findViewById(R.id.button_addbalance);
        button_addpayment = rootView.findViewById(R.id.button_addpayment);
        textview_balance = rootView.findViewById(R.id.textview_balance);
        textview_balance.setText(SetBalance() + " " + "BYN");

        button_addpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        button_addpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog_payment();
            }
        });
        button_addbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog_balance();
            }
        });


        return rootView;
    }
    public double SetBalance() {
        GroceryDBHelper dbHelper_r = new GroceryDBHelper(getContext());
        SQLiteDatabase mDatabase_r = dbHelper_r.getReadableDatabase();

        Cursor cursor = mDatabase_r.rawQuery("SELECT SUM(" + GroceryContract.GroceryEntry.COLUMN_BALANCE + ") as Total FROM " + GroceryContract.GroceryEntry.TABLE_Grocy, null);

        if (cursor.moveToFirst()) {

            total_pluse = cursor.getInt(cursor.getColumnIndex("Total"));

        }
        PaymentDBHelper dbHelper = new PaymentDBHelper(getContext());
        SQLiteDatabase mDatabase2 = dbHelper.getReadableDatabase();

        Cursor cursor2 = mDatabase2.rawQuery("SELECT SUM(" + PaymentContract.PaymentEntry.COLUMN_PRICE + ") as Total FROM " + PaymentContract.PaymentEntry.TABLE_Payment, null);

        if (cursor2.moveToFirst()) {

            total_minuse = cursor2.getInt(cursor2.getColumnIndex("Total"));
        }
        return total_pluse-total_minuse;
    }



    private void OpenDialog_balance() {
        Dialog_balance dialog = new Dialog_balance();
        dialog.show(getFragmentManager(), "Example");

    }

    private void OpenDialog_payment() {
        Dialog dialog = new Dialog();
        dialog.show(getFragmentManager(), "Example");


    }



}
