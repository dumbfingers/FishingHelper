package com.yeyaxi.android.fishinghelper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View v = inflater.inflate(R.layout.cell_listview, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        // get data from db and set to the UI
        int timestamp = cursor.getInt(
                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_TIMESTAMP));

        float latitude = cursor.getFloat(
                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_GPS_LAT));

        float longitude = cursor.getFloat(
                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_GPS_LONG));

        String angler = cursor.getString(
                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_ANGLER));

        //length
        float length = cursor.getFloat(
                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_FISH_LENGTH));

        //weight
        float weight = cursor.getFloat(
                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_FISH_WEIGHT));
        //bait
        String bait = cursor.getString(
                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_BAIT));

        //img
        //TODO image path string, so we can set up to load image

        //note
        String note = cursor.getString(
                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_NOTE));

        TextView title = (TextView) view.findViewById(R.id.textView_cell_title);
        TextView descr = (TextView) view.findViewById(R.id.textView_cell_descr);

        //TODO set photo
    }
}
