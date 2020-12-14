package com.example.fragmenttest;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dialog extends AppCompatDialogFragment {
    private AutoCompleteTextView autoCompleteTextView;
    private AutoCompleteTextView autoCompleteTextView2;
    private static SQLiteDatabase mDatabase;
    private TextView tview_weight;
    private TextView tview_price;
    private double total_pluse;
    private double total_minuse;
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        PaymentDBHelper dbHelper = new PaymentDBHelper(getContext());
        mDatabase = dbHelper.getWritableDatabase();

        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView_Payment);
        autoCompleteTextView2 = view.findViewById(R.id.autoCompleteTextView_Payment_VID);
        tview_weight=view.findViewById(R.id.edit_text_weight);
        tview_price=view.findViewById(R.id.edit_text_price);


        String[] category= getResources().getStringArray(R.array.categories_purchaise) ;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, category);
        autoCompleteTextView.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Регистрация покупки")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        String category = String.valueOf(autoCompleteTextView.getText());
                        String vid  = String.valueOf(autoCompleteTextView2.getText());
                        String weight = String.valueOf(tview_weight.getText());
                        String date = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(Calendar.getInstance().getTime());
                            if(vid=="Чипсы" || vid=="Lay`s" || vid=="LAYS" || vid=="чипсы"  )
                                Toast.makeText(getContext(), "Черезмерное употребление чипсов вредит вашему здровью", Toast.LENGTH_SHORT).show();
                            if(vid=="Яблоко" || vid=="яблоки" || vid=="ЯБЛОКО" || vid=="ЯБЛОКИ"  )
                                Toast.makeText(getContext(), "В вашем организме стало больше жира — 0,17 г, белки — 0,26 г, углеводы — 13,81 г, вода — 85,56 г, зола — 0,19 г.", Toast.LENGTH_SHORT).show();
                            if(vid=="Банан" || vid=="БАНАН" || vid=="банан" || vid=="Бананы" || vid=="БАНАНЫ" )
                                Toast.makeText(getContext(), "Калории, ккал:  95\n" +
                                        "Белки, г:  1.5\n" +
                                        "Жиры, г:  0.2\n" +
                                        "Углеводы, г:  21.8", Toast.LENGTH_SHORT).show();



                        if(category.isEmpty() || vid.isEmpty()  )
                        {
                            Toast.makeText(getContext(), "Вы не заполнили одно из полей", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Double price = Double.valueOf(String.valueOf(tview_price.getText()));
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(PaymentContract.PaymentEntry.COLUMN_CATEGORY, category);
                            contentValues.put(PaymentContract.PaymentEntry.COLUMN_PRICE, price);
                            contentValues.put(PaymentContract.PaymentEntry.COLUMN_SUBCATEGORY, vid);
                            contentValues.put(PaymentContract.PaymentEntry.COLUMN_WEIGHT, weight);
                            contentValues.put(PaymentContract.PaymentEntry.COLUMN_DATE, date);

                            
                            mDatabase.insert(PaymentContract.PaymentEntry.TABLE_Payment, null, contentValues);
                            SetBalance();





                            autoCompleteTextView.getText().clear();
                            autoCompleteTextView2.getText().clear();
                        }
                        





                    }
                });

        return builder.create();
    }
    public void SetBalance() {
        GroceryDBHelper dbHelper_r = new GroceryDBHelper(getContext());
        SQLiteDatabase mDatabase_r = dbHelper_r.getReadableDatabase();

        Cursor cursor = mDatabase_r.rawQuery("SELECT SUM(" + GroceryContract.GroceryEntry.COLUMN_BALANCE + ") as Total FROM " + GroceryContract.GroceryEntry.TABLE_Grocy, null);

        if (cursor.moveToFirst()) {

            total_pluse = cursor.getInt(cursor.getColumnIndex("Total"));

        }
        PaymentDBHelper dbHelper = new PaymentDBHelper(getContext());
        SQLiteDatabase mDatabase2 = dbHelper.getWritableDatabase();

        Cursor cursor2 = mDatabase2.rawQuery("SELECT SUM(" + PaymentContract.PaymentEntry.COLUMN_PRICE + ") as Total FROM " + PaymentContract.PaymentEntry.TABLE_Payment, null);

        if (cursor2.moveToFirst()) {

            total_minuse = cursor2.getInt(cursor2.getColumnIndex("Total"));
        }
        BalanceFragment.textview_balance.setText(total_pluse-total_minuse + " " + "BYN");



    }


}