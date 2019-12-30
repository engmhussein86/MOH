package com.moh.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.moh.app.models.NewsClass;

import java.util.ArrayList;

/**
 * Created by pc-3 on 8/30/2016.
 */
public class NewsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MOH.db";
    private static final int DATABASE_VERSION = 1;

    public NewsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        NewsTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        NewsTable.onUpgrade(database, oldVersion, newVersion);
    }

    // Adding new news
    public void addNews(NewsClass news) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        //cv.put(NewsTable.COLUMN_ID, NULL);
        cv.put(NewsTable.COLUMN_GUID, news.getGuid());
        cv.put(NewsTable.COLUMN_IMAGE_URL, news.getImage_url());
        cv.put(NewsTable.COLUMN_POST_CONTENT, news.getPost_content());
        cv.put(NewsTable.COLUMN_POST_DATE, news.getPost_date());
        cv.put(NewsTable.COLUMN_POST_TITLE, news.getPost_title());

        db.insert(NewsTable.TABLE_NEWS, null, cv);

        db.close();
    }

    ;

    // Getting single news
    public NewsClass getNews(int id) {
        return null;
    }

    ;

    // Getting All news
    public ArrayList<NewsClass> getAllNews() {
        ArrayList<NewsClass> all_news = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();


        Cursor c = db.query(NewsTable.TABLE_NEWS, null, null, null, null, null, NewsTable.COLUMN_POST_DATE + " DESC");

        if (c.moveToFirst()) {
            do {
                NewsClass news = new NewsClass();
                news.setId(c.getInt(c.getColumnIndex(NewsTable.COLUMN_ID)));
                news.setGuid(c.getString(c.getColumnIndex(NewsTable.COLUMN_GUID)));
                news.setImage_url(c.getString(c.getColumnIndex(NewsTable.COLUMN_IMAGE_URL)));
                news.setPost_content(c.getString(c.getColumnIndex(NewsTable.COLUMN_POST_CONTENT)));
                news.setPost_date(c.getString(c.getColumnIndex(NewsTable.COLUMN_POST_DATE)));
                news.setPost_title(c.getString(c.getColumnIndex(NewsTable.COLUMN_POST_TITLE)));

                all_news.add(news);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return all_news;
    }

    ;

    // Getting contacts Count
    public int getContactsCount() {
        return 0;
    }

    ;

    // Updating single contact
    public int updateContact(NewsClass news) {
        return 0;
    }

    ;

    // Deleting single news
    public void deleteNews(int id) {

        SQLiteDatabase db = getWritableDatabase();
        if (id != -1)
            db.delete(NewsTable.TABLE_NEWS, NewsTable.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        else
            db.delete(NewsTable.TABLE_NEWS, null, null);

    }

    ;


}
