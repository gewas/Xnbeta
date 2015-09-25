package com.icer.cnbeta.manager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.icer.cnbeta.app.AppApplication;
import com.icer.cnbeta.util.URLUtil;

/**
 * Created by icer on 2015-09-25.
 */
public class RequestManager {

    public static final String TAG = RequestManager.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private static RequestManager appRequestManager;

    private RequestQueue mRequestQueue;

    private RequestManager() {
        mRequestQueue = AppApplication.getInstance().getAppRequestQueue();
    }

    public static RequestManager getInstance() {
        if (appRequestManager == null) {
            appRequestManager = new RequestManager();
        }
        return appRequestManager;
    }

    public void requestLatest(String lastSid, Response.Listener<String> listener, Response.ErrorListener errorListener, String tag) {
        StringRequest request = new StringRequest(URLUtil.getList(lastSid), listener, errorListener);
        request.setTag(tag);
        mRequestQueue.add(request);
    }

    public void cancelRequest(String tag) {
        mRequestQueue.cancelAll(tag);
    }

}
