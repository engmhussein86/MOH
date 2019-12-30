package com.moh.app.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by pc-3 on 8/30/2016.
 */
public class NewsTable {

    // Database table
    public static final String TABLE_NEWS = "NEWS";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_POST_DATE = "post_date";
    public static final String COLUMN_GUID = "guid";
    public static final String COLUMN_POST_TITLE = "post_title";
    public static final String COLUMN_POST_CONTENT = "post_content";
    public static final String COLUMN_IMAGE_URL = "image_url";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NEWS
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_POST_DATE + " text not null, "
            + COLUMN_GUID + " text not null,"
            + COLUMN_POST_TITLE + " text not null,"
            + COLUMN_POST_CONTENT + " text not null,"
            + COLUMN_IMAGE_URL + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(NewsTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        onCreate(database);
    }


}
