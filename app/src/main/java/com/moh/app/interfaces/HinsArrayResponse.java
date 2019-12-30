package com.moh.app.interfaces;

import com.moh.app.models.HinsUser;

import java.util.ArrayList;

public interface HinsArrayResponse {
    ArrayList<HinsUser> onSuccess(ArrayList<HinsUser> hins);

    void onFail(String msg);
}
