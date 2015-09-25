package com.icer.cnbeta.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by icer on 2015-09-24.
 */
public class BaseActivity extends AppCompatActivity {
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translucentSystemBar();
        setLayoutFullScreen();
    }

    public void translucentSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void setLayoutFullScreen() {
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

    public void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

}
