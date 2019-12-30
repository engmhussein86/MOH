package com.moh.app.models;

import android.util.Log;

/**
 * Created by pc-3 on 11/20/2016.
 */
public class PhoneClass {

    String PHONE_SID, PHONE_NO, PHONE_USER, JOB_TITLE, LOC_TITLE_AR;
    boolean fav;

    public PhoneClass() {
    }


    public PhoneClass(String PHONE_SID, String PHONE_NO, String PHONE_USER, String JOB_TITLE, boolean fav, String LOC_TITLE_AR) {
        this.PHONE_SID = PHONE_SID;
        this.PHONE_NO = PHONE_NO;
        this.PHONE_USER = PHONE_USER;
        this.JOB_TITLE = JOB_TITLE;
        this.fav = fav;
        this.LOC_TITLE_AR = LOC_TITLE_AR;

    }

    public String getJOB_TITLE() {
        Log.e("Error", JOB_TITLE);
        if (JOB_TITLE.equalsIgnoreCase("null") || JOB_TITLE.equalsIgnoreCase("")) {
            setJOB_TITLE("المسمى الوظيفي غير مدخل");
        }
        return JOB_TITLE;
    }

    public void setJOB_TITLE(String JOB_TITLE) {
        this.JOB_TITLE = JOB_TITLE;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }


    public String getPHONE_SID() {
        return PHONE_SID;
    }

    public void setPHONE_SID(String PHONE_SID) {
        this.PHONE_SID = PHONE_SID;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getPHONE_USER() {
        if (PHONE_USER.equalsIgnoreCase("null") || PHONE_USER.equalsIgnoreCase("")) {
            setPHONE_USER("المستخدم غير مدخل ");
        }
        return PHONE_USER;
    }

    public void setPHONE_USER(String PHONE_USER) {
        this.PHONE_USER = PHONE_USER;
    }

    public String getLOC_TITLE_AR() {
        return LOC_TITLE_AR;
    }

    public void setLOC_TITLE_AR(String LOC_TITLE_AR) {
        this.LOC_TITLE_AR = LOC_TITLE_AR;
    }
}
