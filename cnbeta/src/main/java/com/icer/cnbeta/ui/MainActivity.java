package com.icer.cnbeta.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.icer.cnbeta.R;
import com.icer.cnbeta.adapter.LatestListAdapter;
import com.icer.cnbeta.app.AppConstants;
import com.icer.cnbeta.app.BaseActivity;
import com.icer.cnbeta.db.DBHelper;
import com.icer.cnbeta.manager.RequestManager;
import com.icer.cnbeta.volley.NewsInfoListBean;
import com.icer.cnbeta.volley.entity.NewsInfo;

import java.util.ArrayList;

/**
 * Created by icer on 2015-09-24.
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    public static final String TAG = MainActivity.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;

    private LatestListAdapter mAdapter;
    private ArrayList<NewsInfo> mData;

    private DBHelper mDBHelper;

    private boolean mIsRequesting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initAdapter();
        initActionBar();
        regListener();
        loadDataFromDB();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        mIsRequesting = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_collections:
                goToActivity(new Intent(MainActivity.this, CollectionActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        mDBHelper = new DBHelper(this);
        mData = new ArrayList<>();
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setProgressViewOffset(false, -dp2pxInt(48), dp2pxInt(48));
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_lowest_bg));
        mListView = (ListView) findViewById(R.id.list);
    }

    private void initAdapter() {
        mAdapter = new LatestListAdapter(this, mData);
        mListView.setAdapter(mAdapter);
    }

    private void regListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);
    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.subtitle_latest);
    }

    private void loadDataFromDB() {
        mAdapter.addData(mDBHelper.getLocalNewsInfoList(false, null));
        if (mAdapter.getCount() == 0)
            requestListFromNet(null);
    }

    private void requestListFromNet(final String lastSid) {
        if (!mIsRequesting) {
            if (lastSid == null && !mSwipeRefreshLayout.isRefreshing())
                startRefresh();
            mIsRequesting = true;
            RequestManager.getInstance().requestLatest(lastSid, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    logI(TAG, s);
                    final NewsInfoListBean newsInfoListBean = JSON.parseObject(s, NewsInfoListBean.class);
                    if (lastSid == null) {
                        mDBHelper.saveNewsInfoList(newsInfoListBean.result, new Runnable() {
                            @Override
                            public void run() {
                                if (!mAdapter.refreshData(newsInfoListBean.result))
                                    showToastLong(getString(R.string.hint_already_latest));
                                stopRefresh();
                                mIsRequesting = false;
                            }
                        });
                    } else {
                        mDBHelper.saveNewsInfoList(newsInfoListBean.result, new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.addData(mDBHelper.getLocalNewsInfoList(false, lastSid));
                                showToast(getString(R.string.hint_loading_more_complete));
                                mIsRequesting = false;
                            }
                        });
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    logW(TAG, volleyError.toString());
                    showToast(AppConstants.HINT_LOADING_FAILED);
                    if (lastSid == null)
                        stopRefresh();
                    mIsRequesting = false;
                }
            }, TAG);
        }
    }

    private void startRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void stopRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (!mIsRequesting)
            requestListFromNet(null);
        else
            stopRefresh();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.onItemClick(position);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!mIsRequesting && mAdapter.getLastSid() != null && totalItemCount - visibleItemCount <= firstVisibleItem) {
            showToast(getString(R.string.hint_loading_more));
            requestListFromNet(mAdapter.getLastSid());
        }
    }
}
