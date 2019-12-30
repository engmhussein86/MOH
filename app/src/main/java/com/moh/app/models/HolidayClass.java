package com.moh.app.models;

/**
 * Created by pc-3 on 4/25/2017.
 */
public class HolidayClass {
    String TB_EMPBASICINFO_NO, TB_EMPBASICINFO_ID, FULL_NAME_AR, TB_EMPHOLIDAYS_STARTDATE, TB_EMPHOLIDAYS_ENDDATE, TB_CHOLIDAY_NAME, C_ASSIST_NAME_AR;
    int TB_EMPHOLIDAYS_NO, TB_EMPHOLIDAYS_HCOUNT;

    public HolidayClass(int TB_EMPHOLIDAYS_NO, String TB_EMPBASICINFO_NO, String FULL_NAME_AR, int TB_EMPHOLIDAYS_HCOUNT, String TB_EMPHOLIDAYS_STARTDATE, String TB_EMPHOLIDAYS_ENDDATE, String TB_CHOLIDAY_NAME, String C_ASSIST_NAME_AR) {
        this.TB_EMPBASICINFO_NO = TB_EMPBASICINFO_NO;
        this.FULL_NAME_AR = FULL_NAME_AR;
        this.TB_EMPHOLIDAYS_HCOUNT = TB_EMPHOLIDAYS_HCOUNT;
        this.TB_EMPHOLIDAYS_STARTDATE = TB_EMPHOLIDAYS_STARTDATE;
        this.TB_EMPHOLIDAYS_ENDDATE = TB_EMPHOLIDAYS_ENDDATE;
        this.TB_EMPHOLIDAYS_NO = TB_EMPHOLIDAYS_NO;
        this.TB_CHOLIDAY_NAME = TB_CHOLIDAY_NAME;
        this.C_ASSIST_NAME_AR = C_ASSIST_NAME_AR;
    }

    public int getTB_EMPHOLIDAYS_NO() {
        return TB_EMPHOLIDAYS_NO;
    }

    public void setTB_EMPHOLIDAYS_NO(int TB_EMPHOLIDAYS_NO) {
        this.TB_EMPHOLIDAYS_NO = TB_EMPHOLIDAYS_NO;
    }

    public String getTB_EMPBASICINFO_ID() {
        return TB_EMPBASICINFO_ID;
    }

    public void setTB_EMPBASICINFO_ID(String TB_EMPBASICINFO_ID) {
        this.TB_EMPBASICINFO_ID = TB_EMPBASICINFO_ID;
    }

    public String getTB_EMPBASICINFO_NO() {
        return TB_EMPBASICINFO_NO;
    }

    public void setTB_EMPBASICINFO_NO(String TB_EMPBASICINFO_NO) {
        this.TB_EMPBASICINFO_NO = TB_EMPBASICINFO_NO;
    }

    public String getFULL_NAME_AR() {
        return FULL_NAME_AR;
    }

    public void setFULL_NAME_AR(String FULL_NAME_AR) {
        this.FULL_NAME_AR = FULL_NAME_AR;
    }

    public int getTB_EMPHOLIDAYS_HCOUNT() {
        return TB_EMPHOLIDAYS_HCOUNT;
    }

    public void setTB_EMPHOLIDAYS_HCOUNT(int TB_EMPHOLIDAYS_HCOUNT) {
        this.TB_EMPHOLIDAYS_HCOUNT = TB_EMPHOLIDAYS_HCOUNT;
    }

    public String getTB_EMPHOLIDAYS_STARTDATE() {
        return TB_EMPHOLIDAYS_STARTDATE;
    }

    public void setTB_EMPHOLIDAYS_STARTDATE(String TB_EMPHOLIDAYS_STARTDATE) {
        this.TB_EMPHOLIDAYS_STARTDATE = TB_EMPHOLIDAYS_STARTDATE;
    }

    public String getTB_EMPHOLIDAYS_ENDDATE() {
        return TB_EMPHOLIDAYS_ENDDATE;
    }

    public void setTB_EMPHOLIDAYS_ENDDATE(String TB_EMPHOLIDAYS_ENDDATE) {
        this.TB_EMPHOLIDAYS_ENDDATE = TB_EMPHOLIDAYS_ENDDATE;
    }

    public String getTB_CHOLIDAY_NAME() {
        return TB_CHOLIDAY_NAME;
    }

    public void setTB_CHOLIDAY_NAME(String TB_CHOLIDAY_NAME) {
        this.TB_CHOLIDAY_NAME = TB_CHOLIDAY_NAME;
    }

    public String getC_ASSIST_NAME_AR() {
        return C_ASSIST_NAME_AR;
    }

    public void setC_ASSIST_NAME_AR(String c_ASSIST_NAME_AR) {
        C_ASSIST_NAME_AR = c_ASSIST_NAME_AR;
    }
}
