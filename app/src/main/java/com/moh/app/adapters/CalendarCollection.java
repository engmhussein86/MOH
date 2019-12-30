package com.moh.app.adapters;

import java.util.ArrayList;

/**
 * Created by pc-3 on 2/26/2017.
 */
public class CalendarCollection {

    public static ArrayList<CalendarCollection> date_collection_arr;
    public String date = "";
    public String event_message = "";

    public CalendarCollection(String date, String event_message) {

        this.date = date;
        this.event_message = event_message;

    }
}
