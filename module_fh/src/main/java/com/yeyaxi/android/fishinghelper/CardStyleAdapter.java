package com.yeyaxi.android.fishinghelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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

    private int reqWidth;
    private int reqHeight;
    private FishViewHolder holder = null;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public CardStyleAdapter(Context context, int layout, List list) {
        super(context, layout, list);
//        super(context, layout, c, from, to, flags);
        this.layout = layout;
        this.context = context;
//        this.cursor = c;
        this.list = list;
        this.inflater = LayoutInflater.from(context);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_action_picture)
                .showImageOnFail(R.drawable.ic_action_warning)
                .cacheOnDisc(true)
                .cacheInMemory(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .imageDownloader(new SQLiteImageLoader(context))
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {

            view = inflater.inflate(layout, parent, false);
            holder = new FishViewHolder();

            holder.title = (TextView) view.findViewById(R.id.textView_cell_title);
            holder.descr = (TextView) view.findViewById(R.id.textView_cell_descr);
            holder.img = (ImageView) view.findViewById(R.id.imageView_fishImg);

            view.setTag(holder);

        } else {
            holder = (FishViewHolder)view.getTag();
        }

        Fish fish = (Fish)list.get(position);
        holder.title.setText(fish.getFishName());

//        reqWidth = holder.img.getWidth();
//        reqHeight = holder.img.getHeight();

//        if (fish.getImgByteArray() != null) {
//            holder.img.setImageBitmap(decodeThumbnailFromBlob(
//                    fish.getImgByteArray(), holder.img.getWidth(), holder.img.getHeight()));
////            new LoadThumbnailToList().execute(fish.getImgByteArray());
//        }
        imageLoader.displayImage("db://" + position, holder.img, options);

        return view;
    }

//    private class LoadThumbnailToList extends AsyncTask<byte[], Void, Bitmap> {
//        @Override
//        protected Bitmap doInBackground(byte[]... params) {
//            Bitmap bitmap = decodeThumbnailFromBlob(
//                    params[0], holder.img.getWidth(), holder.img.getHeight());
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bm) {
//            holder.img.setImageBitmap(bm);
//        }
//    }
//    AsyncTask<byte[], Void, Bitmap> loadThumbnailToList = new AsyncTask<byte[], Void, Bitmap>() {
//        @Override
//        protected Bitmap doInBackground(byte[]... params) {
//            Bitmap bitmap = decodeThumbnailFromBlob(
//                    params[0], holder.img.getWidth(), holder.img.getHeight());
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bm) {
//            holder.img.setImageBitmap(bm);
//        }
//    };
//
//    private Bitmap decodeThumbnailFromBlob(byte[] imgBytes, int reqWidth, int reqHeight) {
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length, options);
//
//        options.inSampleSize = AddRecordFragment.calculateInSampleSize(options, reqWidth, reqHeight);
//
//        options.inJustDecodeBounds = false;
//
//        return BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length, options);
//    }

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
