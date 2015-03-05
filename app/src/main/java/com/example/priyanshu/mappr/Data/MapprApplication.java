package com.example.priyanshu.mappr.Data;

import android.app.Application;
import android.content.Context;

/**
 * Created by rocky on 5/3/15.
 */
public class MapprApplication extends Application {
    private static MapprApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MapprApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
