package com.moh.app.models;

/**
 * Created by pc-3 on 1/21/2018.
 */

public class InjuriesClass {
    int InjuriesCount, Hos_No;
    String AK_NAME_AR;

    public InjuriesClass(int injuriesCount, int hos_No, String AK_NAME_AR) {
        this.InjuriesCount = injuriesCount;
        this.Hos_No = hos_No;
        this.AK_NAME_AR = AK_NAME_AR;
    }

    public int getInjuriesCount() {
        return InjuriesCount;
    }

    public void setInjuriesCount(int injuriesCount) {
        InjuriesCount = injuriesCount;
    }

    public int getHos_No() {
        return Hos_No;
    }

    public void setHos_No(int hos_No) {
        Hos_No = hos_No;
    }

    public String getAK_NAME_AR() {
        return AK_NAME_AR;
    }

    public void setAK_NAME_AR(String AK_NAME_AR) {
        this.AK_NAME_AR = AK_NAME_AR;
    }
}
