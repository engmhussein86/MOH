package com.moh.app.db;

/**
 * Created by ALMOHANDIS on 9/4/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.moh.app.models.PhoneClass;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneDBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "PhoneDB";

    // Phones table name
    private static final String TABLE_CONTACTS = "phone_tb";

    // Phones Table Columns names
    private static final String PHONE_SID = "phone_sid";
    private static final String PHONE_NO = "phone_number";
    private static final String PHONE_USER = "phone_user";


    public PhoneDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + PHONE_SID + " INTEGER PRIMARY KEY,"
                + PHONE_NO + " text,"
                + PHONE_USER + " text"
                + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Adding new contact
    public void addPhone(PhoneClass phones) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        //cv.put(NewsTable.COLUMN_ID, NULL);
        cv.put(PHONE_SID, Integer.parseInt(phones.getPHONE_SID()));
        cv.put(PHONE_NO, phones.getPHONE_NO());
        cv.put(PHONE_USER, phones.getPHONE_USER());

        db.insert(TABLE_CONTACTS, null, cv);

        db.close();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getting single contact
    public HashMap<String, String> getPhone(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{PHONE_SID,
                        PHONE_NO}, PHONE_SID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        //Log.e("hussein",""+cursor.getCount());
        HashMap<String, String> params = null;
        if (cursor != null) {
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                params = new HashMap<>();
                params.put("PHONE_SID", cursor.getString(0));
                params.put("PHONE_NO", cursor.getString(1));
                //params.put("PHONE_USER", cursor.getString(2));
            }

        }
        return params;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getting All Phones
//    public ArrayList<HashMap<String, String>> getAllPhones() {
//        ArrayList<HashMap<String, String>> contactList = new ArrayList<>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            int x=1;
//            do {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("POS",""+x);
//                params.put("PHONE_SID",cursor.getString(0));
//                params.put("PHONE_NO",cursor.getString(1));
//                //params.put("PHONE_USER", cursor.getString(2));
//                contactList.add(params);
//                ++x;
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return contactList;
//    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Deleting single contact
    public void deletePhone(int id) {

        SQLiteDatabase db = getWritableDatabase();
        if (id != -1)
            db.delete(TABLE_CONTACTS, PHONE_SID + " = ?", new String[]{String.valueOf(id)});
        else
            db.delete(TABLE_CONTACTS, null, null);

        db.close();

    }

    ;


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getting contacts Count
    public int getPhonesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // checkFav
    public long checkFav(int id) {

        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " WHERE " + PHONE_SID + "=" + id;


        Cursor c = db.rawQuery(selectQuery, null);
        c.getCount();
        Log.e("ERROR", "" + c.getCount());

        c.close();
        db.close();

        return c.getCount();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getting All news
    public ArrayList<PhoneClass> getAllPhones() {
        ArrayList<PhoneClass> all_phones = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_CONTACTS, null, null, null, null, null, PHONE_USER + " DESC");

        if (c.moveToFirst()) {
            do {
                PhoneClass phone = new PhoneClass();
                phone.setPHONE_SID(c.getString(c.getColumnIndex(PHONE_SID)));
                phone.setPHONE_NO(c.getString(c.getColumnIndex(PHONE_NO)));
                phone.setPHONE_USER(c.getString(c.getColumnIndex(PHONE_USER)));


                all_phones.add(phone);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return all_phones;
    }

}
