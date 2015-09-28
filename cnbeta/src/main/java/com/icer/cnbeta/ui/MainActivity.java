package com.icer.cnbeta.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.icer.cnbeta.R;
import com.icer.cnbeta.adapter.LatestListAdapter;
import com.icer.cnbeta.app.BaseActivity;
import com.icer.cnbeta.manager.RequestManager;
import com.icer.cnbeta.volley.LatestListBean;
import com.icer.cnbeta.volley.entity.Latest;

import java.util.ArrayList;

/**
 * Created by icer on 2015-09-24.
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    public static final String TAG = MainActivity.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;

    private LatestListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initAdapter();
        initActionBar();
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
        stopRefresh();
    }

    private void initData() {

    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setProgressViewOffset(true, -dp2pxInt(24), dp2pxInt(64));
        mListView = (ListView) findViewById(R.id.list);
    }

    private void initAdapter() {
        mAdapter = new LatestListAdapter(this, new ArrayList<Latest>());
        mListView.setAdapter(mAdapter);
    }

    private void regListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setSubtitle(R.string.subtitle_latest);
    }

    private void requestLatest() {
        if (!mSwipeRefreshLayout.isRefreshing())
            startRefresh();
        RequestManager.getInstance().requestLatest(null, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                logI(TAG, s);
                LatestListBean latestListBean = JSON.parseObject(s, LatestListBean.class);
                if (!mAdapter.refreshData(latestListBean.result))
                    showToastLong(getString(R.string.hint_already_latest));
                stopRefresh();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                logW(TAG, volleyError.toString());
                stopRefresh();
            }
        }, TAG);
    }

    private void startRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void stopRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        requestLatest();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.onItemClick(position);
    }
}
