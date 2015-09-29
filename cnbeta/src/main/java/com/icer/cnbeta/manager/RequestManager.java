package com.icer.cnbeta.manager;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.icer.cnbeta.app.AppApplication;
import com.icer.cnbeta.app.AppConstants;
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

    public void requestLatest(String lastSid, Response.Listener<String> listener, Response.ErrorListener errorListener, Object tag) {
        StringRequest request = new StringRequest(URLUtil.getList(lastSid), listener, errorListener);
        request.setTag(tag);
        mRequestQueue.add(request);
    }

    public void requestContent(String sid, Response.Listener<String> listener, Response.ErrorListener errorListener, Object tag) {
        StringRequest request = new StringRequest(URLUtil.getContent(sid), listener, errorListener);
        request.setTag(tag);
        mRequestQueue.add(request);
    }

    public void requestImage(String url, Response.Listener<Bitmap> listener, int maxWidth, int maxHeight, ImageView.ScaleType scaleType, Response.ErrorListener errorListener, Object tag) {
        String urlString = url;
        if (urlString.startsWith("/"))
            urlString = AppConstants.IMAGE_SITE + urlString;
        ImageRequest request = new ImageRequest(urlString, listener, maxWidth, maxHeight, scaleType, Bitmap.Config.ARGB_8888, errorListener);
        mRequestQueue.add(request);
    }

    public void cancelRequest(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

}
