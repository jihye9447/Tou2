package com.tou;

import android.app.Application;

/**
 * Created by Administrator on 2018-03-01.
 */

public class LockApplication extends Application {

    public boolean lockScreenShow=false;
    public int notificationId=1989;

    @Override
    public void onCreate() {
        super.onCreate();
    }

}