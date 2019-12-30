package com.moh.app.models;

public class LabOrderClass {
    String ORDER_REQUEST_DATE, CATEGORY_NAME_AR, ORDER_STATUS_NAME_AR, H_NAME_AR;
    int ORDER_CD, D_DETAILS_CD;

    public LabOrderClass(String ORDER_REQUEST_DATE, String CATEGORY_NAME_AR, String ORDER_STATUS_NAME_AR, String h_NAME_AR, int ORDER_CD, int d_DETAILS_CD) {
        this.ORDER_REQUEST_DATE = ORDER_REQUEST_DATE;
        this.CATEGORY_NAME_AR = CATEGORY_NAME_AR;
        this.ORDER_STATUS_NAME_AR = ORDER_STATUS_NAME_AR;
        H_NAME_AR = h_NAME_AR;
        this.ORDER_CD = ORDER_CD;
        D_DETAILS_CD = d_DETAILS_CD;
    }

    public String getORDER_REQUEST_DATE() {
        return ORDER_REQUEST_DATE;
    }

    public void setORDER_REQUEST_DATE(String ORDER_REQUEST_DATE) {
        this.ORDER_REQUEST_DATE = ORDER_REQUEST_DATE;
    }

    public String getCATEGORY_NAME_AR() {
        return CATEGORY_NAME_AR;
    }

    public void setCATEGORY_NAME_AR(String CATEGORY_NAME_AR) {
        this.CATEGORY_NAME_AR = CATEGORY_NAME_AR;
    }

    public String getORDER_STATUS_NAME_AR() {
        return ORDER_STATUS_NAME_AR;
    }

    public void setORDER_STATUS_NAME_AR(String ORDER_STATUS_NAME_AR) {
        this.ORDER_STATUS_NAME_AR = ORDER_STATUS_NAME_AR;
    }

    public String getH_NAME_AR() {
        return H_NAME_AR;
    }

    public void setH_NAME_AR(String h_NAME_AR) {
        H_NAME_AR = h_NAME_AR;
    }

    public int getORDER_CD() {
        return ORDER_CD;
    }

    public void setORDER_CD(int ORDER_CD) {
        this.ORDER_CD = ORDER_CD;
    }

    public int getD_DETAILS_CD() {
        return D_DETAILS_CD;
    }

    public void setD_DETAILS_CD(int d_DETAILS_CD) {
        D_DETAILS_CD = d_DETAILS_CD;
    }
}
