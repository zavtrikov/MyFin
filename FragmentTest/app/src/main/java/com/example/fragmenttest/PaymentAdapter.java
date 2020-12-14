package com.example.fragmenttest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.GroceryViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public PaymentAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView countText;
        public TextView date;

        public GroceryViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.text_view_balance_pm);
            countText = itemView.findViewById(R.id.text_view_balance_comment);
            date = itemView.findViewById(R.id.textview_date);
        }
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycleview_item, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(PaymentContract.PaymentEntry.COLUMN_CATEGORY));
        String amount = mCursor.getString(mCursor.getColumnIndex(PaymentContract.PaymentEntry.COLUMN_PRICE));
        String str_date = mCursor.getString(mCursor.getColumnIndex(PaymentContract.PaymentEntry.COLUMN_DATE));
        long id = mCursor.getLong(mCursor.getColumnIndex(PaymentContract.PaymentEntry._ID));

        holder.nameText.setText(name);
        holder.date.setText(str_date);
        holder.countText.setText(String.valueOf(amount) +" " + "BYN");
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}