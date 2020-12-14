package com.example.fragmenttest;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dialog_balance extends AppCompatDialogFragment {

    private EditText autoCompleteTextView;
    private AutoCompleteTextView autoCompleteTextView2;
    private static SQLiteDatabase mDatabase;
    private static SQLiteDatabase mDatabase2;
    private double total_pluse;
    private double total_minuse;



    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_balance, null);

        GroceryDBHelper dbHelper = new GroceryDBHelper(getContext());
        mDatabase = dbHelper.getWritableDatabase();


        autoCompleteTextView = view.findViewById(R.id.edit_text_balance);
        autoCompleteTextView2 = view.findViewById(R.id.autoCompleteTextView2);

        String[] category = getResources().getStringArray(R.array.categories);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, category);
        autoCompleteTextView2.setAdapter(adapter);



        builder.setView(view)
                .setTitle("Зачисление денедных средств")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        String commect = String.valueOf(autoCompleteTextView2.getText());
                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(Calendar.getInstance().getTime());

                        String BYN=" BYN";

                        if(String.valueOf(autoCompleteTextView2.getText())==BYN || commect.isEmpty() || String.valueOf(autoCompleteTextView2.getText())==null)
                        {
                            Toast.makeText(getContext(), "Вы не заполнили одно из полей", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            if(commect=="степендия"  ||commect=="Степендия")
                            {
                                Toast.makeText(getContext(), "Видимо вы студент...", Toast.LENGTH_SHORT).show();
                            }
                            if(String.valueOf(autoCompleteTextView2.getText())=="Заработная плата"  || String.valueOf(autoCompleteTextView2.getText())=="зароботная плата")
                            {
                                Toast.makeText(getContext(), "Надо будет пометить эту дату в календаре...", Toast.LENGTH_SHORT).show();
                            }
                            if(String.valueOf(autoCompleteTextView2.getText())=="Долг"  || String.valueOf(autoCompleteTextView2.getText())=="долг")
                            {
                                Toast.makeText(getContext(), "Хорошо , что вам вернули долг...", Toast.LENGTH_SHORT).show();
                            }
                            if(String.valueOf(autoCompleteTextView2.getText())=="Премия"  || String.valueOf(autoCompleteTextView2.getText())=="премия")
                            {
                                Toast.makeText(getContext(), "Кто-то усердно работал)", Toast.LENGTH_SHORT).show();
                            }









                            double balance = Double.valueOf(String.valueOf(autoCompleteTextView.getText()));
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(GroceryContract.GroceryEntry.COLUMN_BALANCE, balance);
                            contentValues.put(GroceryContract.GroceryEntry.COLUMN_B_COMMENT, commect);
                            contentValues.put(GroceryContract.GroceryEntry.COLUMN_B_DATE, timeStamp);

                            mDatabase.insert(GroceryContract.GroceryEntry.TABLE_Grocy, null, contentValues);
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
        mDatabase2 = dbHelper.getWritableDatabase();

        Cursor cursor2 = mDatabase2.rawQuery("SELECT SUM(" + PaymentContract.PaymentEntry.COLUMN_PRICE + ") as Total FROM " + PaymentContract.PaymentEntry.TABLE_Payment, null);

        if (cursor2.moveToFirst()) {

            total_minuse = cursor2.getInt(cursor2.getColumnIndex("Total"));
        }
        BalanceFragment.textview_balance.setText(total_pluse-total_minuse + " " + "BYN");



    }


}