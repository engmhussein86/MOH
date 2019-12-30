package com.moh.app.models;

/**
 * Created by pc-3 on 3/6/2017.
 */
public class ShiftClass {

    String TB_EMPBASICINFO_NO, TB_EMPSHIFT_SHIFTDATE, TB_SHIFT_NAME, TB_SHIFT_START, TB_SHIFT_END, NOTE, TB_SHIFT_TYPE_NAME;
    int TB_SHIFT_NO, SHIFT_TYPE_FK;

    public ShiftClass(String TB_EMPBASICINFO_NO, String TB_EMPSHIFT_SHIFTDATE, String TB_SHIFT_NAME, String TB_SHIFT_START, String TB_SHIFT_END, String NOTE, String TB_SHIFT_TYPE_NAME, int TB_SHIFT_NO, int SHIFT_TYPE_FK) {
        this.TB_EMPBASICINFO_NO = TB_EMPBASICINFO_NO;
        this.TB_EMPSHIFT_SHIFTDATE = TB_EMPSHIFT_SHIFTDATE;
        this.TB_SHIFT_NAME = TB_SHIFT_NAME;
        this.TB_SHIFT_START = TB_SHIFT_START;
        this.TB_SHIFT_END = TB_SHIFT_END;
        this.NOTE = NOTE;
        this.TB_SHIFT_TYPE_NAME = TB_SHIFT_TYPE_NAME;
        this.TB_SHIFT_NO = TB_SHIFT_NO;
        this.SHIFT_TYPE_FK = SHIFT_TYPE_FK;
    }

    public String getTB_EMPBASICINFO_NO() {
        return TB_EMPBASICINFO_NO;
    }

    public void setTB_EMPBASICINFO_NO(String TB_EMPBASICINFO_NO) {
        this.TB_EMPBASICINFO_NO = TB_EMPBASICINFO_NO;
    }

    public String getTB_EMPSHIFT_SHIFTDATE() {
        return TB_EMPSHIFT_SHIFTDATE;
    }

    public void setTB_EMPSHIFT_SHIFTDATE(String TB_EMPSHIFT_SHIFTDATE) {
        this.TB_EMPSHIFT_SHIFTDATE = TB_EMPSHIFT_SHIFTDATE;
    }

    public String getTB_SHIFT_NAME() {
        return TB_SHIFT_NAME;
    }

    public void setTB_SHIFT_NAME(String TB_SHIFT_NAME) {
        this.TB_SHIFT_NAME = TB_SHIFT_NAME;
    }

    public String getTB_SHIFT_START() {
        return TB_SHIFT_START.equalsIgnoreCase("null") ? " " : TB_SHIFT_START;
    }

    public void setTB_SHIFT_START(String TB_SHIFT_START) {
        this.TB_SHIFT_START = TB_SHIFT_START;
    }

    public String getTB_SHIFT_END() {

        return TB_SHIFT_END.equalsIgnoreCase("null") ? " " : TB_SHIFT_END;

    }

    public void setTB_SHIFT_END(String TB_SHIFT_END) {
        this.TB_SHIFT_END = TB_SHIFT_END;
    }

    public String getNOTE() {
        return NOTE;
    }

    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    public String getTB_SHIFT_TYPE_NAME() {
        return TB_SHIFT_TYPE_NAME;
    }

    public void setTB_SHIFT_TYPE_NAME(String TB_SHIFT_TYPE_NAME) {
        this.TB_SHIFT_TYPE_NAME = TB_SHIFT_TYPE_NAME;
    }

    public int getTB_SHIFT_NO() {
        return TB_SHIFT_NO;
    }

    public void setTB_SHIFT_NO(int TB_SHIFT_NO) {
        this.TB_SHIFT_NO = TB_SHIFT_NO;
    }

    public int getSHIFT_TYPE_FK() {
        return SHIFT_TYPE_FK;
    }

    public void setSHIFT_TYPE_FK(int SHIFT_TYPE_FK) {
        this.SHIFT_TYPE_FK = SHIFT_TYPE_FK;
    }
}
