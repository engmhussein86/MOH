package com.moh.app.models;

public class HosScheduleClass {
    String LOC_NAME_AR, FULL_NAME, CPT_NAME_AR;

    public HosScheduleClass(String LOC_NAME_AR, String FULL_NAME, String CPT_NAME_AR) {
        this.LOC_NAME_AR = LOC_NAME_AR;
        this.FULL_NAME = FULL_NAME;
        this.CPT_NAME_AR = CPT_NAME_AR;
    }

    public String getLOC_NAME_AR() {
        return LOC_NAME_AR;
    }

    public void setLOC_NAME_AR(String LOC_NAME_AR) {
        this.LOC_NAME_AR = LOC_NAME_AR;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public String getCPT_NAME_AR() {
        return CPT_NAME_AR;
    }

    public void setCPT_NAME_AR(String CPT_NAME_AR) {
        this.CPT_NAME_AR = CPT_NAME_AR;
    }
}
