package com.moh.app.models;

/**
 * Created by pc-3 on 12/20/2016.
 */
public class HinsUser {
    String ID_NO, FULL_NAME_AR, REL_TYPE_DESC;
    int IsDep, IMG_APPROVE;
    boolean uploaded;
    private String imageFile;

    public HinsUser(String ID_NO, String FULL_NAME_AR, String REL_TYPE_DESC, int IsDep) {
        this.ID_NO = ID_NO;
        this.FULL_NAME_AR = FULL_NAME_AR;
        this.REL_TYPE_DESC = REL_TYPE_DESC;
        this.IsDep = IsDep;
    }

    public HinsUser(String ID_NO, String FULL_NAME_AR, String REL_TYPE_DESC, int IsDep, int IMG_APPROVE) {
        this.ID_NO = ID_NO;
        this.FULL_NAME_AR = FULL_NAME_AR;
        this.REL_TYPE_DESC = REL_TYPE_DESC;
        this.IsDep = IsDep;
        this.IMG_APPROVE = IMG_APPROVE;
    }

    public int getIMG_APPROVE() {
        return IMG_APPROVE;
    }

    public void setIMG_APPROVE(int IMG_APPROVE) {
        this.IMG_APPROVE = IMG_APPROVE;
    }

    public int getIsDep() {
        return IsDep;
    }

    public void setIsDep(int isDep) {
        IsDep = isDep;
    }

    public String getID_NO() {
        return ID_NO;
    }

    public void setID_NO(String ID_NO) {
        this.ID_NO = ID_NO;
    }

    public String getFULL_NAME_AR() {
        return FULL_NAME_AR.trim();
    }

    public void setFULL_NAME_AR(String FULL_NAME_AR) {
        this.FULL_NAME_AR = FULL_NAME_AR;
    }

    public String getREL_TYPE_DESC() {
        return REL_TYPE_DESC.trim();
    }

    public void setREL_TYPE_DESC(String REL_TYPE_DESC) {
        this.REL_TYPE_DESC = REL_TYPE_DESC;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }
}
