package com.yeyaxi.android.fishinghelper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yaxi on 20/03/2014.
 */
public class CardStyleCursorAdapter extends SimpleCursorAdapter {

    private Context context;
    private int layout;
    private Cursor cursor;
    private LayoutInflater inflater;


    public CardStyleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.layout = layout;
        this.context = context;
        this.cursor = c;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);


    }
}
