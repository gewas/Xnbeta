package com.icer.cnbeta.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.icer.cnbeta.app.AppConfig;


/**
 * Created by icer on 2015/4/19.
 */
public class PreferenceManager {
    public static final String TAG = PreferenceManager.class.getCanonicalName();
    public static final int ID = TAG.hashCode();

    public static PreferenceManager appPreferenceManager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public static final String KEY_NO_LIST_IMAGE = "no_list_image";
    public static final String KEY_NO_CONTENT_IMAGE = "no_content_image";

    private static Boolean settingNoListImage;
    private static Boolean settingNoContentImage;

    private PreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        firstRun();
    }

    public static PreferenceManager getInstance(Context context) {
        if (appPreferenceManager == null)
            appPreferenceManager = new PreferenceManager(context);
        getLastPref();
        return appPreferenceManager;
    }

    private static void firstRun() {
        if (appPreferenceManager.mSharedPreferences.getAll().size() == 0) {
            appPreferenceManager.mEditor.putBoolean(KEY_NO_LIST_IMAGE, false);
            appPreferenceManager.mEditor.putBoolean(KEY_NO_CONTENT_IMAGE, false);
            appPreferenceManager.mEditor.commit();
        }
    }

    private static void getLastPref() {
        settingNoListImage = appPreferenceManager.mSharedPreferences.getBoolean(KEY_NO_LIST_IMAGE, false);
        settingNoContentImage = appPreferenceManager.mSharedPreferences.getBoolean(KEY_NO_CONTENT_IMAGE, false);
        if (AppConfig.IS_DEBUG_MODE) {
            Log.i("不加载列表图片", settingNoListImage + "");
            Log.i("不加载新闻图片", settingNoContentImage + "");
        }
    }

    public static void updatePref(Boolean noListImage, Boolean noContentImage) {
        boolean isChanged = false;
        if (noListImage != null) {
            settingNoListImage = noListImage;
            appPreferenceManager.mEditor.putBoolean(KEY_NO_LIST_IMAGE, noListImage);
            isChanged = true;
        }
        if (noContentImage != null) {
            settingNoContentImage = noContentImage;
            appPreferenceManager.mEditor.putBoolean(KEY_NO_CONTENT_IMAGE, noContentImage);
            isChanged = true;
        }
        if (isChanged)
            appPreferenceManager.mEditor.commit();
    }

    public static Boolean isNoListImage() {
        return settingNoListImage;
    }

    public static Boolean isNoContentImage() {
        return settingNoContentImage;
    }
}
