package com.yeyaxi.android.fishinghelper.dataUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaxi on 14/03/2014.
 */
public class FishingDataOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = FishingDataOpenHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fishingdata";
    private static final String FTS_TABLE_FISH = "fts_fish";

    private static final String KEY_ID = "_id";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_GPS_LAT = "latitude";
    private static final String KEY_GPS_LONG = "longitude";
    private static final String KEY_ANGLER = "angler";
    private static final String KEY_FISH_LENGTH = "fish_length";
    private static final String KEY_FISH_WEIGHT = "fish_weight";
    private static final String KEY_BAIT = "bait";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_NOTE = "note";


    private static final String CREATE_FISH_TABLE =
            "CREATE VIRTUAL TABLE " + FTS_TABLE_FISH + " USING fts3 (" +
                    KEY_ID + " INTEGER PRIMARY KEY," +  //id
                    KEY_TIMESTAMP + " INTEGER," +          //timestamp
                    KEY_GPS_LAT + " FLOAT," +            //latitude
                    KEY_GPS_LONG + " FLOAT," +           //longitude
                    KEY_ANGLER + " TEXT," +             //angler type
                    KEY_FISH_LENGTH + " FLOAT," +        //fish length
                    KEY_FISH_WEIGHT + " FLOAT," +        //fish weight
                    KEY_BAIT + " TEXT," +               //bait type
                    KEY_PHOTO + " BLOB," +              //photo of fish
                    KEY_NOTE + " TEXT);";               //other notes


    public FishingDataOpenHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public FishingDataOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                 int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FISH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the older version
        db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_FISH);
        // re-create the table
        onCreate(db);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    /**
     * CRUD Operations
     */

    /**
     * Create
     * @param fish
     */
    public void addFish(Fish fish) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, fish.getId());
        values.put(KEY_TIMESTAMP, fish.getTimeStamp());
        values.put(KEY_GPS_LAT, fish.getLatitude());
        values.put(KEY_GPS_LONG, fish.getLongitude());
        values.put(KEY_ANGLER, fish.getAngler());
        values.put(KEY_FISH_LENGTH, fish.getFishLength());
        values.put(KEY_FISH_WEIGHT, fish.getFishWeight());
        values.put(KEY_BAIT, fish.getBait());
        //TODO Use the ImgPath to retrieve photo
//        values.put(KEY_PHOTO, fish.getImgPath());
        values.put(KEY_NOTE, fish.getNote());

        db.insert(FTS_TABLE_FISH, null, values);
        db.close();
    }

    /**
     * Read
     * @param id
     * @return
     */
    public Fish getFish(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Fish fish = null;

        try {
            // SELECT * FROM TABLE_FISH WHERE KEY_ID=id;
            Cursor cursor = db.query(
                FTS_TABLE_FISH, null, KEY_ID + "=" + String.valueOf(id), null, null, null, null);

            cursor.moveToFirst();

            //TODO check cursor.getString(8)
            fish = new Fish (Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                    Float.parseFloat(cursor.getString(2)), Float.parseFloat(cursor.getString(3)),
                    cursor.getString(4), Float.parseFloat(cursor.getString(5)),
                    Float.parseFloat(cursor.getString(6)), cursor.getString(7),
                    cursor.getString(8), cursor.getString(9));

        } catch (NullPointerException npe) {
            // Cursor is null
            npe.printStackTrace();
            Log.e(TAG, "Something wrong with database query, cursor returns null");
        }

        return fish;
    }

    /**
     * Read all records
     * @return list of all records
     */
    public List<Fish> getAllFish() {
        List<Fish> fishList = new ArrayList<Fish>();

        String selectQuery = "SELECT * FROM " + FTS_TABLE_FISH;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst() == true) {
            do {
                Fish fish = new Fish();
                fish.setId(Integer.parseInt(cursor.getString(0)));
                fish.setTimeStamp(Integer.parseInt(cursor.getString(1)));
                fish.setLatitude(Float.parseFloat(cursor.getString(2)));
                fish.setLongitude(Float.parseFloat(cursor.getString(3)));
                fish.setAngler(cursor.getString(4));
                fish.setFishLength(Float.parseFloat(cursor.getString(5)));
                fish.setFishWeight(Float.parseFloat(cursor.getString(6)));
                fish.setBait(cursor.getString(7));
                //TODO Fix the Image issue
//                fish.setImgPath(cursor.getString(8));
                fish.setNote(cursor.getString(9));

                fishList.add(fish);
            } while (cursor.moveToNext() == true);
        }
        return fishList;
    }

    /**
     * Get the total number of entries
     * @return
     */
    public int getFishCount() {

        String countQuery = "SELECT  * FROM " + FTS_TABLE_FISH;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();

    }

    /**
     * Update
     * @param fish
     * @return
     */
    public int updateFish(Fish fish) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIMESTAMP, fish.getTimeStamp());
        values.put(KEY_GPS_LAT, fish.getLatitude());
        values.put(KEY_GPS_LONG, fish.getLongitude());
        values.put(KEY_ANGLER, fish.getAngler());
        values.put(KEY_FISH_LENGTH, fish.getFishLength());
        values.put(KEY_FISH_WEIGHT, fish.getFishWeight());
        values.put(KEY_BAIT, fish.getBait());
        //TODO Use the ImgPath to retrieve photo
//        values.put(KEY_PHOTO, fish.getImgPath());
        values.put(KEY_NOTE, fish.getNote());

        int result = db.update(FTS_TABLE_FISH, values, KEY_ID + " = " + fish.getId(), null);

        return result;
    }

    /**
     * Delete
     * @param fish
     */
    public void deleteFish(Fish fish) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(FTS_TABLE_FISH, KEY_ID + "=" + fish.getId(), null);

        db.close();

    }


}
