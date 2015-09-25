package com.icer.cnbeta.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.icer.cnbeta.app.AppConfig;


/**
 * Created by icer on 2015/4/19.
 */
public class PreferenceManager {
    public static final String TAG = PreferenceManager.class.getSimpleName();
    public static final int ID = PreferenceManager.class.getName().hashCode();

    public static PreferenceManager mPreferenceManager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public static final String KEY_NO_LIST_IMAGE = "no_list_image";
    public static final String KEY_NO_CONTENT_IMAGE = "no_content_image";
    public static final String KEY_CONTENT_TEXT_SIZE = "content_text_size";

    private static Boolean settingNoListImage;
    private static Boolean settingNoContentImage;
    private static Integer settingContentTextSize;

    private PreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        firstRun();
    }

    public static PreferenceManager getInstance(Context context) {
        if (mPreferenceManager == null)
            mPreferenceManager = new PreferenceManager(context);
        getLastPref();
        return mPreferenceManager;
    }

    /**
     * 第一次运行,初始化设置
     */
    private static void firstRun() {
        if (mPreferenceManager.mSharedPreferences.getAll().size() == 0) {
            mPreferenceManager.mEditor.putBoolean(KEY_NO_LIST_IMAGE, false);
            mPreferenceManager.mEditor.putBoolean(KEY_NO_CONTENT_IMAGE, false);
            mPreferenceManager.mEditor.putInt(KEY_CONTENT_TEXT_SIZE, 16);
            mPreferenceManager.mEditor.commit();
        }
    }

    private static void getLastPref() {
        settingNoListImage = mPreferenceManager.mSharedPreferences.getBoolean(KEY_NO_LIST_IMAGE, false);
        settingNoContentImage = mPreferenceManager.mSharedPreferences.getBoolean(KEY_NO_CONTENT_IMAGE, false);
        settingContentTextSize = mPreferenceManager.mSharedPreferences.getInt(KEY_CONTENT_TEXT_SIZE, 16);
        if (AppConfig.IS_DEBUG_MODE) {
            Log.i("不加载列表图片", settingNoListImage + "");
            Log.i("不加载新闻图片", settingNoContentImage + "");
            Log.i("新闻文字大小", settingContentTextSize + "");
        }
    }

    public static void updatePref(Boolean noListImage, Boolean noContentImage, Integer contentTextSize) {
        boolean isChanged = false;
        if (noListImage != null) {
            settingNoListImage = noListImage;
            mPreferenceManager.mEditor.putBoolean(KEY_NO_LIST_IMAGE, noListImage);
            isChanged = true;
        }
        if (noContentImage != null) {
            settingNoContentImage = noContentImage;
            mPreferenceManager.mEditor.putBoolean(KEY_NO_CONTENT_IMAGE, noContentImage);
            isChanged = true;
        }
        if (contentTextSize != null && contentTextSize > 0) {
            settingContentTextSize = contentTextSize;
            mPreferenceManager.mEditor.putInt(KEY_CONTENT_TEXT_SIZE, contentTextSize);
            isChanged = true;
        }
        if (isChanged)
            mPreferenceManager.mEditor.commit();
    }

    public static Boolean isNoListImage() {
        return settingNoListImage;
    }

    public static Boolean isNoContentImage() {
        return settingNoContentImage;
    }

    public static Integer getSettingContentTextSize() {
        return settingContentTextSize;
    }
}
