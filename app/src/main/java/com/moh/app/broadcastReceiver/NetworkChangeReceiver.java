package com.moh.app.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.moh.app.DrawerActivity;
import com.moh.app.db.NewsDBHelper;
import com.moh.app.news.FragNews;
import com.moh.app.splash;
import com.moh.app.utils.ToolsUtils;

/**
 * Created by pc-3 on 8/31/2016.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    public NetworkChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        if (ToolsUtils.isConnectingToInternet(context)) {
            //Toast.makeText(context,"connected", Toast.LENGTH_SHORT).show();

            if (DrawerActivity.navItemIndex == 1) {
                Intent in = new Intent(context, splash.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }

        } else {
            //Toast.makeText(context,"not connected", Toast.LENGTH_SHORT).show();
            if (DrawerActivity.navItemIndex == 1) {
                NewsDBHelper helper = new NewsDBHelper(context);
                helper.deleteNews(-1);
                Log.e("Error", "deleting");

                for (int i = 0; i < FragNews.news.size(); i++) {
                    helper.addNews(FragNews.news.get(i));
                    Log.e("Error", "add" + i);
                }
            }

        }
    }
}

