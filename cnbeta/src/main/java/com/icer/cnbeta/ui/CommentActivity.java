package com.icer.cnbeta.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.AbsListView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.icer.cnbeta.R;
import com.icer.cnbeta.adapter.CommentAdapter;
import com.icer.cnbeta.app.AppConstants;
import com.icer.cnbeta.app.BaseActivity;
import com.icer.cnbeta.manager.RequestManager;
import com.icer.cnbeta.volley.NewsCommentsBean;
import com.icer.cnbeta.volley.entity.NewsComment;

import java.util.ArrayList;

/**
 * Created by icer on 2015/10/3.
 */
public class CommentActivity extends BaseActivity implements AbsListView.OnScrollListener {
    public static final String TAG = CommentActivity.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private Toolbar mToolbar;
    private ListView mListView;

    private CommentAdapter mAdapter;

    private String mSid;
    private int mPage = 1;
    private boolean mIsRequesting;
    private boolean mHasMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initData();
        initView();
        initAdapter();
        initActionBar();
        regListener();
        requestComments();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestManager.getInstance().cancelRequest(TAG);
    }

    private void initData() {
        mHasMore = true;
        mSid = getIntent().getStringExtra(AppConstants.SID);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.comment_list);
    }

    private void initAdapter() {
        mAdapter = new CommentAdapter(this, new ArrayList<NewsComment>());
        mListView.setAdapter(mAdapter);
    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.subtitle_comments);
    }

    private void regListener() {
        mListView.setOnScrollListener(this);
    }

    private void requestComments() {
        if (mHasMore) {
            mIsRequesting = true;
            RequestManager.getInstance().requestComment(mPage + "", mSid, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    logI(TAG, s);
                    NewsCommentsBean newsCommentsBean = JSON.parseObject(s, NewsCommentsBean.class);
                    logI(TAG, newsCommentsBean.toString());
                    if (newsCommentsBean.result != null && !newsCommentsBean.result.isEmpty()) {
                        mPage++;
                        mHasMore = true;
                        mAdapter.addData(newsCommentsBean.result);
                    } else {
                        mHasMore = false;
                    }
                    mIsRequesting = false;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    showToast(AppConstants.HINT_LOADING_FAILED);
                    mIsRequesting = false;
                }
            }, TAG);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mAdapter.getCount() >= 10 && !mIsRequesting && totalItemCount - visibleItemCount <= firstVisibleItem) {
            requestComments();
        }
    }
}
