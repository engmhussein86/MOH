package com.moh.app.utils;

import com.moh.app.models.HinsUser;

import java.util.ArrayList;

public interface CallBack {
    void onSuccess(ArrayList<HinsUser> hinsArray);

    void onFail(String msg);
}
