package com.icer.cnbeta.app;

import android.app.Application;
import android.content.ContentResolver;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by icer on 2015/9/24.
 */
public class AppApplication extends Application {

    private static AppApplication appApplication;
    private RequestQueue appRequestQueue;
    private ContentResolver appContentResolver;

    @Override
    public void onCreate() {
        super.onCreate();
        initSingleton();
        initRequestQueue();
        initContentResolver();
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

    private void initContentResolver() {
        appContentResolver = getContentResolver();
    }

    public RequestQueue getAppRequestQueue() {
        return appRequestQueue;
    }

    public ContentResolver getAppContentResolver() {
        return appContentResolver;
    }
}
