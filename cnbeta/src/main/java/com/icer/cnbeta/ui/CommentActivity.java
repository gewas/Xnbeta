package com.icer.cnbeta.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.icer.cnbeta.R;
import com.icer.cnbeta.app.BaseActivity;

/**
 * Created by icer on 2015/10/3.
 */
public class CommentActivity extends BaseActivity {
    public static final String TAG = CommentActivity.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initData();
        initView();
        initAdapter();
        initActionBar();
        regListener();
    }

    private void initData() {

    }

    private void initView() {

    }

    private void initAdapter() {

    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.subtitle_comments);
    }

    private void regListener() {

    }
}
