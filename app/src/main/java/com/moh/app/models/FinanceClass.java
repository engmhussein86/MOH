package com.moh.app.models;

import android.util.Log;

/**
 * Created by pc-3 on 12/29/2016.
 */
public class FinanceClass {

    String ENTRY_DATE, PATIENT_ID, HOS_NAME;
    int AMOUNT, ADDED_COSTS1, PAYED_AMOUNT;

    public FinanceClass(String ENTRY_DATE, String HOS_NAME, String PATIENT_ID, int AMOUNT, int ADDED_COSTS1, int PAYED_AMOUNT) {
        this.ENTRY_DATE = ENTRY_DATE;
        this.HOS_NAME = HOS_NAME;
        this.PATIENT_ID = PATIENT_ID;
        this.AMOUNT = AMOUNT;
        this.ADDED_COSTS1 = ADDED_COSTS1;
        this.PAYED_AMOUNT = PAYED_AMOUNT;

        Log.e("FinanceClass", "new object");
    }

    public String getENTRY_DATE() {
        return ENTRY_DATE;
    }

    public void setENTRY_DATE(String ENTRY_DATE) {
        this.ENTRY_DATE = ENTRY_DATE;
    }

    public String getPATIENT_ID() {
        return PATIENT_ID;
    }

    public void setPATIENT_ID(String PATIENT_ID) {
        this.PATIENT_ID = PATIENT_ID;
    }

    public String getHOS_NAME() {
        return HOS_NAME;
    }

    public void setHOS_NAME(String HOS_NAME) {
        this.HOS_NAME = HOS_NAME;
    }

    public int getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(int AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public int getADDED_COSTS1() {
        return ADDED_COSTS1;
    }

    public void setADDED_COSTS1(int ADDED_COSTS1) {
        this.ADDED_COSTS1 = ADDED_COSTS1;
    }

    public int getPAYED_AMOUNT() {
        return PAYED_AMOUNT;
    }

    public void setPAYED_AMOUNT(int PAYED_AMOUNT) {
        this.PAYED_AMOUNT = PAYED_AMOUNT;
    }
}
