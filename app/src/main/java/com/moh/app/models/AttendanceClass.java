package com.moh.app.models;

import android.util.Log;

/**
 * Created by pc-3 on 1/26/2017.
 */
public class AttendanceClass {
    String TB_EMPBASICINFO_NO, ATTENDDAT, ATTENDTIM, ARRTIME, ARRIDATE, ATTDAY, ATTENDSTA;
    int ATTENDTYP;

    public AttendanceClass(String TB_EMPBASICINFO_NO, String ATTENDDAT, String ATTENDTIM, String ARRTIME, String ARRIDATE, String ATTDAY, String ATTENDSTA, int ATTENDTYP) {
        this.TB_EMPBASICINFO_NO = TB_EMPBASICINFO_NO;
        this.ATTENDDAT = ATTENDDAT;
        this.ATTENDTIM = ATTENDTIM;
        this.ARRTIME = ARRTIME;
        this.ARRIDATE = ARRIDATE;
        this.ATTDAY = ATTDAY;
        this.ATTENDSTA = ATTENDSTA;
        this.ATTENDTYP = ATTENDTYP;

        Log.e("const", "object created");
    }

    public String getTB_EMPBASICINFO_NO() {
        return TB_EMPBASICINFO_NO;
    }

    public void setTB_EMPBASICINFO_NO(String TB_EMPBASICINFO_NO) {
        this.TB_EMPBASICINFO_NO = TB_EMPBASICINFO_NO;
    }

    public String getATTENDDAT() {
        return ATTENDDAT;
    }

    public void setATTENDDAT(String ATTENDDAT) {
        this.ATTENDDAT = ATTENDDAT;
    }

    public String getATTENDTIM() {
        return ATTENDTIM;
    }

    public void setATTENDTIM(String ATTENDTIM) {
        this.ATTENDTIM = ATTENDTIM;
    }

    public String getARRTIME() {
        return ARRTIME;
    }

    public void setARRTIME(String ARRTIME) {
        this.ARRTIME = ARRTIME;
    }

    public String getARRIDATE() {
        return ARRIDATE;
    }

    public void setARRIDATE(String ARRIDATE) {
        this.ARRIDATE = ARRIDATE;
    }

    public String getATTDAY() {
        return ATTDAY;
    }

    public void setATTDAY(String ATTDAY) {
        this.ATTDAY = ATTDAY;
    }

    public String getATTENDSTA() {
        return ATTENDSTA;
    }

    public void setATTENDSTA(String ATTENDSTA) {
        this.ATTENDSTA = ATTENDSTA;
    }

    public int getATTENDTYP() {
        return ATTENDTYP;
    }

    public void setATTENDTYP(int ATTENDTYP) {
        this.ATTENDTYP = ATTENDTYP;
    }
}
