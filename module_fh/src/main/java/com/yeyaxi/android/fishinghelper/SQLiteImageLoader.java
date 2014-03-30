package com.yeyaxi.android.fishinghelper;

import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yaxi on 30/03/2014.
 */
public class SQLiteImageLoader extends BaseImageDownloader {

    private static final String SCHEME_DB = "db";
    private static final String DB_URI_PREFIX = SCHEME_DB + "://";
    private static final String TAG = SQLiteImageLoader.class.getSimpleName();

    public SQLiteImageLoader(Context context) {
        super(context);
    }

    @Override
    protected InputStream getStreamFromOtherSource(String imageUri, Object extra) throws IOException {
        if (imageUri.startsWith(DB_URI_PREFIX)) {
            String path = imageUri.substring(DB_URI_PREFIX.length());

            // Your logic to retreive needed data from DB
            FishingDataOpenHelper db = new FishingDataOpenHelper(context);
            Fish fish = db.getFish(Integer.parseInt(path));
            byte[] imageData = fish.getImgByteArray();

            Log.d(TAG, fish.getFishName() + ", " + imageData);

            return new ByteArrayInputStream(imageData);
        } else {
            return super.getStreamFromOtherSource(imageUri, extra);
        }
    }

}
