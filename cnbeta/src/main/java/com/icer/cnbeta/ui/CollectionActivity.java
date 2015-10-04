package com.icer.cnbeta.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.icer.cnbeta.R;
import com.icer.cnbeta.adapter.CollectionListAdapter;
import com.icer.cnbeta.app.AppConstants;
import com.icer.cnbeta.app.BaseActivity;
import com.icer.cnbeta.db.DBHelper;
import com.icer.cnbeta.volley.entity.NewsInfo;

import java.util.ArrayList;

/**
 * Created by icer on 2015/10/3.
 */
public class CollectionActivity extends BaseActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    public static final String TAG = CollectionActivity.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private Toolbar mToolbar;
    private ListView mCollectionLv;
    private CollectionListAdapter mAdapter;
    private DBHelper mDBHelper;
    private boolean mIsLoadingMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
        initAdapter();
        initActionBar();
        regListener();
        loadDataFromDB(null);
    }

    private void initView() {
        mCollectionLv = (ListView) findViewById(R.id.collection_list);
    }

    private void initAdapter() {
        mAdapter = new CollectionListAdapter(this, new ArrayList<NewsInfo>());
        mCollectionLv.setAdapter(mAdapter);
    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.subtitle_collections);
    }

    private void regListener() {
        mCollectionLv.setOnItemClickListener(this);
        mCollectionLv.setOnScrollListener(this);
    }

    private void loadDataFromDB(String lastSid) {
        mIsLoadingMore = true;
        if (mDBHelper == null)
            mDBHelper = new DBHelper(this);
        ArrayList<NewsInfo> data = mDBHelper.getLocalNewsInfoList(true, lastSid);
        if (data != null && !data.isEmpty())
            mAdapter.addData(data);
        mIsLoadingMore = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUEST_CODE_FOR_COLLECTION) {
            if (resultCode >= AppConstants.RESUET_CODE_UNCOLLECT) {
                mAdapter.delItemAtPosition(resultCode - AppConstants.RESUET_CODE_UNCOLLECT);
            }
        }
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
        if (mAdapter.getCount() >= 20 && !mIsLoadingMore && mAdapter.getLastSid() != null && totalItemCount - visibleItemCount <= firstVisibleItem) {
            loadDataFromDB(mAdapter.getLastSid());
        }
    }
}
