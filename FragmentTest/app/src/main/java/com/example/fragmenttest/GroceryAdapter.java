package com.example.fragmenttest;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public GroceryAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView countText;
        public TextView dateText;

        public GroceryViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.text_view_balance_pm);
            countText = itemView.findViewById(R.id.text_view_balance_comment);
            dateText = itemView.findViewById(R.id.textview_date);
        }
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycleview_balance_item, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String name = mCursor.getString((mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_BALANCE)));
        String amount = mCursor.getString(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_B_COMMENT));
        String date = mCursor.getString(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_B_DATE));
        name += " BYN";
        long id = mCursor.getLong(mCursor.getColumnIndex(GroceryContract.GroceryEntry._ID));

        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));
        holder.dateText.setText(date);
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