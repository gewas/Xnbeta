package com.icer.cnbeta.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.icer.cnbeta.R;
import com.icer.cnbeta.app.BaseActivity;
import com.icer.cnbeta.manager.RequestManager;

/**
 * Created by icer on 2015-09-24.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initToolBar();
        regListener();
        requestLatest();
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

    }

    private void initView() {

    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_launcher);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        mToolbar.setSubtitleTextColor(getResources().getColor(R.color.color_white));
        mToolbar.setSubtitle(R.string.subtitle_latest);
        mToolbar.setOverflowIcon(getResources().getDrawable(android.R.drawable.ic_menu_more));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("SlideMenu");
            }
        });

    }

    private void regListener() {

    }

    private void requestLatest() {
        RequestManager.getInstance().requestLatest(null, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                showToast(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
