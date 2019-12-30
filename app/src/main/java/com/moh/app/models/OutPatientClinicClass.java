package com.moh.app.models;

public class OutPatientClinicClass {

    String MRP_ID, MR_FULL_NAME_AR, MRP_DOB, VISIT_TIME, H_NAME_AR, LOC_NAME_AR, VISITTP_NAME_AR, DOC_FULL_NAME_AR;

    public OutPatientClinicClass(String MRP_ID, String MR_FULL_NAME_AR, String MRP_DOB, String VISIT_TIME, String H_NAME_AR, String LOC_NAME_AR, String VISITTP_NAME_AR, String DOC_FULL_NAME_AR) {
        this.MRP_ID = MRP_ID;
        this.MR_FULL_NAME_AR = MR_FULL_NAME_AR;
        this.MRP_DOB = MRP_DOB;
        this.VISIT_TIME = VISIT_TIME;
        this.H_NAME_AR = H_NAME_AR;
        this.LOC_NAME_AR = LOC_NAME_AR;
        this.VISITTP_NAME_AR = VISITTP_NAME_AR;
        this.DOC_FULL_NAME_AR = DOC_FULL_NAME_AR;
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

    public String getVISIT_TIME() {
        return VISIT_TIME;
    }

    public void setVISIT_TIME(String VISIT_TIME) {
        this.VISIT_TIME = VISIT_TIME;
    }

    public String getH_NAME_AR() {
        return H_NAME_AR;
    }

    public void setH_NAME_AR(String H_NAME_AR) {
        this.H_NAME_AR = H_NAME_AR;
    }

    public String getLOC_NAME_AR() {
        return LOC_NAME_AR;
    }

    public void setLOC_NAME_AR(String LOC_NAME_AR) {
        this.LOC_NAME_AR = LOC_NAME_AR;
    }

    public String getVISITTP_NAME_AR() {
        return VISITTP_NAME_AR;
    }

    public void setVISITTP_NAME_AR(String VISITTP_NAME_AR) {
        this.VISITTP_NAME_AR = VISITTP_NAME_AR;
    }

    public String getDOC_FULL_NAME_AR() {
        return DOC_FULL_NAME_AR;
    }

    public void setDOC_FULL_NAME_AR(String DOC_FULL_NAME_AR) {
        this.DOC_FULL_NAME_AR = DOC_FULL_NAME_AR;
    }
}
