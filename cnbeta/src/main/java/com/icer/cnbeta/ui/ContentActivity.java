package com.icer.cnbeta.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.icer.cnbeta.R;
import com.icer.cnbeta.app.BaseActivity;

/**
 * Created by icer on 2015-09-28.
 */
public class ContentActivity extends BaseActivity {

    public static final String TAG = ContentActivity.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        regListener();
        initActionBar();
        requestContent();
    }

    private void initData() {

    }

    private void initView() {

    }

    private void regListener() {

    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setSubtitle(R.string.subtitle_content);
    }

    private void requestContent() {
    }

}
