package com.icer.cnbeta.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.icer.cnbeta.R;
import com.icer.cnbeta.app.AppApplication;
import com.icer.cnbeta.app.BaseActivity;
import com.icer.cnbeta.util.URLUtil;

/**
 * Created by icer on 2015-09-24.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initView();
        regListener();
        requestLatest();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_launcher);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        mToolbar.setSubtitleTextColor(getResources().getColor(R.color.color_white));
        mToolbar.setSubtitle("Demo");
        mToolbar.setOverflowIcon(getResources().getDrawable(android.R.drawable.ic_menu_more));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("SlideMenu");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                toast("settings");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

    }

    private void regListener() {

    }

    private void requestLatest() {
        StringRequest request = new StringRequest(URLUtil.getList(null), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                toast(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        AppApplication.getInstance().getAppRequestQueue().add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
