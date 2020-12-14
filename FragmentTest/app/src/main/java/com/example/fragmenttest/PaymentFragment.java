package com.example.fragmenttest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentFragment extends Fragment {
    private SQLiteDatabase database;
    private   PaymentAdapter mAdapter;
    public PaymentFragment() {
    }

    private RecyclerView recyclerView ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate( R.layout.fragment_payment, container, false);
        PaymentDBHelper dbHelper = new PaymentDBHelper(getContext());
        database = dbHelper.getReadableDatabase();
        recyclerView =view.findViewById(R.id.recycleview_paument);
        mAdapter = new PaymentAdapter(getContext(), getAllItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAdapter.swapCursor(getAllItems());
        recyclerView.setAdapter(mAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long)viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        return  view;

    }
    private Cursor getAllItems() {
        return database.query(
                PaymentContract.PaymentEntry.TABLE_Payment,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
    public void removeItem(long id)
    {
        database.delete(PaymentContract.PaymentEntry.TABLE_Payment,
                PaymentContract.PaymentEntry._ID + "=" + id,null);
        mAdapter.swapCursor(getAllItems());
    }
}
