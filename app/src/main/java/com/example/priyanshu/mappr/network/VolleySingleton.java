package com.example.priyanshu.mappr.network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.priyanshu.mappr.Data.MapprApplication;

/**
 * Created by rocky on 5/3/15.
 */
public class VolleySingleton {

    public static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MapprApplication.getAppContext());
    }

    public static VolleySingleton getInstance() {
        if(sInstance == null)
            sInstance = new VolleySingleton();

        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
