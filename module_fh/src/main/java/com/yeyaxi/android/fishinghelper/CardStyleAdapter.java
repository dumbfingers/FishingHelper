package com.yeyaxi.android.fishinghelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yaxi on 20/03/2014.
 */
public class CardStyleAdapter extends ArrayAdapter {

    private Context context;
    private int layout;
//    private Cursor cursor;
    private LayoutInflater inflater;
    private List list;


    public CardStyleAdapter(Context context, int layout, List list) {
        super(context, layout, list);
//        super(context, layout, c, from, to, flags);
        this.layout = layout;
        this.context = context;
//        this.cursor = c;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FishViewHolder holder = null;

        if (convertView == null) {

            convertView = inflater.inflate(layout, parent, false);
            holder = new FishViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.textView_cell_title);
            holder.descr = (TextView) convertView.findViewById(R.id.textView_cell_descr);
            holder.img = (ImageView) convertView.findViewById(R.id.imageView_fishImg);

            convertView.setTag(holder);

        } else {
            holder = (FishViewHolder)convertView.getTag();
        }

        Fish fish = (Fish)list.get(position);
        holder.title.setText(fish.getFishName());
//        holder.img.set
        return convertView;
    }


    static class FishViewHolder {
        TextView title;
        TextView descr;
        ImageView img;
    }
//    @Override
//    public View newView (Context context, Cursor cursor, ViewGroup parent) {
//        View v = inflater.inflate(R.layout.cell_listview, parent, false);
//        return v;
//    }

//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        super.bindView(view, context, cursor);
//
//        // get data from db and set to the UI
//        long timestamp = cursor.getLong(
//                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_TIMESTAMP));
//
//        String name = cursor.getString(
//                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_FISH_NAME));
//
//        float latitude = cursor.getFloat(
//                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_GPS_LAT));
//
//        float longitude = cursor.getFloat(
//                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_GPS_LONG));
//
////        String angler = cursor.getString(
////                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_ANGLER));
//
//        //length
//        float length = cursor.getFloat(
//                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_FISH_LENGTH));
//
//        //weight
//        float weight = cursor.getFloat(
//                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_FISH_WEIGHT));
//        //bait
//        String bait = cursor.getString(
//                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_BAIT));
//
//        //img
//        // image path string, so we can set up to load image
//        byte[] imgByteArray = cursor.getBlob(
//                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_PHOTO));
//
//        //note
//        String note = cursor.getString(
//                cursor.getColumnIndexOrThrow(FishingDataOpenHelper.KEY_NOTE));
//
//        TextView title = (TextView) view.findViewById(R.id.textView_cell_title);
//        TextView descr = (TextView) view.findViewById(R.id.textView_cell_descr);
//
//        // set photo
//        ImageView imgView = (ImageView) view.findViewById(R.id.imageView_fishImg);
//    }
}
