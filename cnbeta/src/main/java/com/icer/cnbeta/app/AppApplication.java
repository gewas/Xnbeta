package com.icer.cnbeta.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by icer on 2015/9/24.
 */
public class AppApplication extends Application {

    private static AppApplication appApplication;
    private RequestQueue appRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        initSingleton();
        initRequestQueue();
    }

    private void initSingleton() {
        appApplication = this;
    }

    public static AppApplication getInstance() {
        return appApplication;
    }

    private void initRequestQueue() {
        appRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public RequestQueue getAppRequestQueue() {
        return appRequestQueue;
    }


}
