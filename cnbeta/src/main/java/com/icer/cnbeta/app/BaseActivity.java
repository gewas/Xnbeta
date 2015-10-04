package com.icer.cnbeta.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.icer.cnbeta.R;
import com.icer.cnbeta.ui.MainActivity;

/**
 * Created by icer on 2015-09-24.
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Toast mToast;
    private boolean mIsToolBarInit;
    private boolean mIsBackground = true;
    private boolean mIsDestroyed;
    private long mLastClickNavigationMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translucentSystemBar();
        setLayoutFullScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsToolBarInit) {
            initToolBar();
        }
        mIsBackground = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsBackground = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsDestroyed = true;
    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.drawable.ic_launcher);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishActivity();
                }
            });
            mToolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
            mToolbar.setSubtitleTextColor(getResources().getColor(R.color.color_white));
            mToolbar.setOverflowIcon(getResources().getDrawable(android.R.drawable.ic_menu_more));
        }
        mIsToolBarInit = true;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public boolean isBackground() {
        return mIsBackground;
    }

    @Override
    public boolean isDestroyed() {
        return mIsDestroyed;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void translucentSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void setLayoutFullScreen() {
        setSystemUIFlag(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void setSystemUIFlag(int flag) {
        View decorView = getWindow().getDecorView();
        int uiOptions = flag;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void logI(String tag, String log) {
        if (AppConfig.IS_DEBUG_MODE)
            Log.i(tag, log);
    }

    public void logW(String tag, String log) {
        if (AppConfig.IS_DEBUG_MODE)
            Log.w(tag, log);
    }

    public void logE(String tag, String log) {
        if (AppConfig.IS_DEBUG_MODE)
            Log.e(tag, log);
    }

    public void showToast(int stringId) {
        showToast(getString(stringId));
    }

    public void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showToastLong(int stringId) {
        showToastLong(getString(stringId));
    }

    public void showToastLong(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        mToast.show();
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public int dp2pxInt(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void goToActivity(Intent intent) {
        if (intent.getIntExtra(AppConstants.POSITION, -1) != -1)
            startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_COLLECTION);
        else
            startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void finishActivity() {
        if (BaseActivity.this instanceof MainActivity)
            finishMainActivity();
        else {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    private void finishMainActivity() {
        long nowMillis = System.currentTimeMillis();
        if (nowMillis - mLastClickNavigationMillis > 2000) {
            mLastClickNavigationMillis = nowMillis;
            showToast(R.string.hint_double_click_finish);
        } else
            finish();
    }
}
