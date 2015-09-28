package com.icer.cnbeta.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.icer.cnbeta.R;
import com.icer.cnbeta.app.AppConstants;
import com.icer.cnbeta.app.BaseActivity;
import com.icer.cnbeta.manager.RequestManager;
import com.icer.cnbeta.volley.ContentBean;

/**
 * Created by icer on 2015-09-28.
 */
public class ContentActivity extends BaseActivity {

    public static final String TAG = ContentActivity.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private Toolbar mToolbar;
    private TextView mTitleTv;
    private TextView mPubtimeTv;
    private TextView mSummaryTv;
    private LinearLayout mContentLl;

    private String mSid;
    private String mTitle;
    private String mPubtime;
    private String mSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initData();
        initView();
        regListener();
        initActionBar();
        requestContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestManager.getInstance().cancelRequest(TAG);
    }

    private void initData() {
        Intent intent = getIntent();
        mSid = intent.getStringExtra(AppConstants.SID);
        mTitle = intent.getStringExtra(AppConstants.TITLE);
        mPubtime = intent.getStringExtra(AppConstants.PUBTIME);
        mSummary = intent.getStringExtra(AppConstants.SUMMARY);
    }

    private void initView() {
        mTitleTv = (TextView) findViewById(R.id.content_title_tv);
        mTitleTv.setText(mTitle);
        mPubtimeTv = (TextView) findViewById(R.id.content_pubtime_tv);
        mPubtimeTv.setText(mPubtime);
        mSummaryTv = (TextView) findViewById(R.id.content_summary_tv);
        mSummaryTv.setText(mSummary);
        mContentLl = (LinearLayout) findViewById(R.id.content_ll);
    }

    private void regListener() {

    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setSubtitle(R.string.subtitle_content);
    }

    private void requestContent() {
        RequestManager.getInstance().requestContent(mSid, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                logI(TAG, s);
                ContentBean contentBean = JSON.parseObject(s, ContentBean.class);
                logI(TAG, contentBean.toString());
//                addOneText2ContentLayout(Html.fromHtml(contentBean.result.bodytext));
                addWebView2ContentLayout(contentBean.result.bodytext);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showToast("Load ");
            }
        }, TAG);
    }

    private void addWebView2ContentLayout(String data) {
        WebView wv = new WebView(this);
        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv.loadDataWithBaseURL(null, fixWebViewImage(data), "text/html", "utf-8", null);
        mContentLl.addView(wv);
    }

    private String fixWebViewImage(String htmlText) {
        String res = htmlText;
        int width = (int) (mTitleTv.getMeasuredWidth() - dp2px(13.34f));
        res = res.replace("<img", "<img width=\"" + (width / dp2px(1f)) + "\"");
        logI(TAG, res);
        return res;
    }
}
