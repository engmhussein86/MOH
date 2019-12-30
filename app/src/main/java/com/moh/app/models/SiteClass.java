package com.moh.app.models;

/**
 * Created by pc-3 on 11/14/2016.
 */
public class SiteClass {
    int LOC_SID;
    String LOC_TITLE_AR, LOC_TITLE_EN;

    public SiteClass(int LOC_SID, String LOC_TITLE_AR, String LOC_TITLE_EN) {
        this.LOC_SID = LOC_SID;
        this.LOC_TITLE_AR = LOC_TITLE_AR;
        this.LOC_TITLE_EN = LOC_TITLE_EN;
    }

    public String getLOC_TITLE_AR() {
        return LOC_TITLE_AR;
    }

    public void setLOC_TITLE_AR(String LOC_TITLE_AR) {
        this.LOC_TITLE_AR = LOC_TITLE_AR;
    }

    public String getLOC_TITLE_EN() {
        return LOC_TITLE_EN;
    }

    public void setLOC_TITLE_EN(String LOC_TITLE_EN) {
        this.LOC_TITLE_EN = LOC_TITLE_EN;
    }

    public int getLOC_SID() {
        return LOC_SID;
    }

    public void setLOC_SID(int LOC_SID) {
        this.LOC_SID = LOC_SID;
    }
}
