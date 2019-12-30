package com.moh.app.models;

/**
 * Created by pc-3 on 5/7/2018.
 */

public class OperationClass {
    String MRP_ID, MR_FULL_NAME_AR, MRP_DOB, ICD_NAME_EN, OP_TYPE_NAME_AR, OP_STATUS_NAME_AR, OP_TIME, OP_ENTER_DATE, H_NAME_AR, DOCTOR_NAME, OP_PATIENT_MOBILE_NO;

    public OperationClass(String MRP_ID, String MR_FULL_NAME_AR, String MRP_DOB, String ICD_NAME_EN, String OP_TYPE_NAME_AR, String OP_STATUS_NAME_AR, String OP_TIME, String OP_ENTER_DATE, String h_NAME_AR, String DOCTOR_NAME, String OP_PATIENT_MOBILE_NO) {
        this.MRP_ID = MRP_ID;
        this.MR_FULL_NAME_AR = MR_FULL_NAME_AR;
        this.MRP_DOB = MRP_DOB;
        this.ICD_NAME_EN = ICD_NAME_EN;
        this.OP_TYPE_NAME_AR = OP_TYPE_NAME_AR;
        this.OP_STATUS_NAME_AR = OP_STATUS_NAME_AR;
        this.OP_TIME = OP_TIME;
        this.OP_ENTER_DATE = OP_ENTER_DATE;
        H_NAME_AR = h_NAME_AR;
        this.DOCTOR_NAME = DOCTOR_NAME;
        this.OP_PATIENT_MOBILE_NO = OP_PATIENT_MOBILE_NO;
    }

    public String getMRP_ID() {
        return MRP_ID;
    }

    public void setMRP_ID(String MRP_ID) {
        this.MRP_ID = MRP_ID;
    }

    public String getMR_FULL_NAME_AR() {
        return MR_FULL_NAME_AR;
    }

    public void setMR_FULL_NAME_AR(String MR_FULL_NAME_AR) {
        this.MR_FULL_NAME_AR = MR_FULL_NAME_AR;
    }

    public String getMRP_DOB() {
        return MRP_DOB;
    }

    public void setMRP_DOB(String MRP_DOB) {
        this.MRP_DOB = MRP_DOB;
    }

    public String getICD_NAME_EN() {
        return ICD_NAME_EN;
    }

    public void setICD_NAME_EN(String ICD_NAME_EN) {
        this.ICD_NAME_EN = ICD_NAME_EN;
    }

    public String getOP_TYPE_NAME_AR() {
        return OP_TYPE_NAME_AR;
    }

    public void setOP_TYPE_NAME_AR(String OP_TYPE_NAME_AR) {
        this.OP_TYPE_NAME_AR = OP_TYPE_NAME_AR;
    }

    public String getOP_STATUS_NAME_AR() {
        return OP_STATUS_NAME_AR;
    }

    public void setOP_STATUS_NAME_AR(String OP_STATUS_NAME_AR) {
        this.OP_STATUS_NAME_AR = OP_STATUS_NAME_AR;
    }

    public String getOP_TIME() {
        return OP_TIME;
    }

    public void setOP_TIME(String OP_TIME) {
        this.OP_TIME = OP_TIME;
    }

    public String getOP_ENTER_DATE() {
        return OP_ENTER_DATE;
    }

    public void setOP_ENTER_DATE(String OP_ENTER_DATE) {
        this.OP_ENTER_DATE = OP_ENTER_DATE;
    }

    public String getH_NAME_AR() {
        return H_NAME_AR;
    }

    public void setH_NAME_AR(String h_NAME_AR) {
        H_NAME_AR = h_NAME_AR;
    }

    public String getDOCTOR_NAME() {
        return DOCTOR_NAME;
    }

    public void setDOCTOR_NAME(String DOCTOR_NAME) {
        this.DOCTOR_NAME = DOCTOR_NAME;
    }

    public String getOP_PATIENT_MOBILE_NO() {
        return OP_PATIENT_MOBILE_NO;
    }

    public void setOP_PATIENT_MOBILE_NO(String OP_PATIENT_MOBILE_NO) {
        this.OP_PATIENT_MOBILE_NO = OP_PATIENT_MOBILE_NO;
    }
}
