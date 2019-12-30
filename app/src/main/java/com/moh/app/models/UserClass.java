package com.moh.app.models;

/**
 * Created by pc-3 on 12/1/2016.
 */
public class UserClass {
    int USER_CODE;
    String USER_FULL_NAME, USER_ID;

    public UserClass(int USER_CODE, String USER_FULL_NAME, String USER_ID) {
        this.USER_CODE = USER_CODE;
        this.USER_FULL_NAME = USER_FULL_NAME;
        this.USER_ID = USER_ID;
    }

    public int getUSER_CODE() {
        return USER_CODE;
    }

    public void setUSER_CODE(int USER_CODE) {
        this.USER_CODE = USER_CODE;
    }

    public String getUSER_FULL_NAME() {
        return USER_FULL_NAME;
    }

    public void setUSER_FULL_NAME(String USER_FULL_NAME) {
        this.USER_FULL_NAME = USER_FULL_NAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }
}
