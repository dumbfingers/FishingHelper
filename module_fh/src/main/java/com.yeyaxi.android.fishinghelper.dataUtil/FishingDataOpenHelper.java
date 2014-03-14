package com.yeyaxi.android.fishinghelper.dataUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yaxi on 14/03/2014.
 */
public class FishingDataOpenHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fishingdata";
    private static final String TABLE_FISH = "dictionary";

    private static final String KEY_ID = "id";
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
            "CREATE TABLE " + TABLE_FISH + " (" +
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

    public FishingDataOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FISH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the older version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FISH);
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

    public void addFish() {

    }
}
