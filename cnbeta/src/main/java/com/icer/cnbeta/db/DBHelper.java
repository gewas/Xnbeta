package com.icer.cnbeta.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.icer.cnbeta.app.AppConfig;
import com.icer.cnbeta.manager.AsyncManager;
import com.icer.cnbeta.volley.entity.NewsInfo;

import java.util.ArrayList;

/**
 * Created by icer on 2015-09-30.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "cnBeta.db";

    private ContentResolver mResolver;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, AppConfig.VERSION_DB);
        mResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getSqlCreateTable(DBConstant.TableList.TABLE_NAME, DBConstant.getColumnsList()));
        db.execSQL(getSqlCreateTable(DBConstant.TableContent.TABLE_NAME, DBConstant.getColumnsContent()));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String getSqlCreateTable(String tableName, ArrayList<String> columns) {
        String res = "CREATE TABLE " + tableName + " (";
        res += DBConstant.UNIVERSAL_COLUMN_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        for (int i = 0; i < columns.size(); i++) {
            res += columns.get(i) + " TEXT";
            if (i != columns.size() - 1)
                res += ", ";
            else
                res += ");";
        }
        return res;
    }

    public ArrayList<NewsInfo> getLocalNewsInfoList(boolean isCollected, String lastSid) {
        ArrayList<NewsInfo> res = null;
        String selection = null;
        String[] selectionArgs = null;
        if (lastSid != null) {
            selection = DBConstant.TableList.COLUMN_SID + "<?";
            selectionArgs = new String[]{lastSid};
        }
        if (isCollected) {
            selection = DBConstant.TableList.COLUMN_IS_COLLECTED + "=?";
            selectionArgs = new String[]{true + ""};
        }
        Cursor cursor = mResolver.query(Uri.parse(DBProvider.URI_LIST), null, selection, selectionArgs, DBConstant.TableList.COLUMN_SID + " DESC");
        if (cursor != null) {
            int i = 0;
            while (cursor.moveToNext()) {
                if (res == null) res = new ArrayList<>();
                if (i == 20) break;
                res.add(new NewsInfo(cursor));
                i++;
            }
            cursor.close();
        }
        return res;
    }

    public void saveNewsInfoList(final ArrayList<NewsInfo> data, Runnable onSaveFinish) {
        AsyncManager.postTask(new Runnable() {
            @Override
            public void run() {
                for (NewsInfo newsInfo : data) {
                    if (queryNewsInfo(newsInfo)) {
                        updateNewsInfo(newsInfo, null);
                    } else {
                        insertNewsInfo(newsInfo);
                    }
                }
            }
        }, onSaveFinish);
    }

    public void updateNewsInfoIsRead(NewsInfo newsInfo) {
        ContentValues values = new ContentValues();
        values.put(DBConstant.TableList.COLUMN_IS_READ, true + "");
        updateNewsInfo(newsInfo, values);
    }

    private boolean queryNewsInfo(NewsInfo newsInfo) {
        boolean res = false;
        Cursor cursor = mResolver.query(Uri.parse(DBProvider.URI_LIST), null,
                DBConstant.TableList.COLUMN_SID + "=?", new String[]{newsInfo.sid}, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                res = true;
            }
            cursor.close();
        }
        return res;
    }

    private void insertNewsInfo(NewsInfo newsInfo) {
        mResolver.insert(Uri.parse(DBProvider.URI_LIST), getContentValuesNewsInfo(newsInfo, false));
    }

    private void updateNewsInfo(NewsInfo newsInfo, ContentValues updateValues) {
        ContentValues values = getContentValuesNewsInfo(newsInfo, true);
        if (updateValues != null)
            values.putAll(updateValues);
        mResolver.update(Uri.parse(DBProvider.URI_LIST), values,
                DBConstant.TableList.COLUMN_SID + "=?", new String[]{newsInfo.sid});
    }

    private ContentValues getContentValuesNewsInfo(NewsInfo newsInfo, boolean isUpdate) {
        ContentValues values = new ContentValues();
        values.put(DBConstant.TableList.COLUMN_SID, newsInfo.sid);
        values.put(DBConstant.TableList.COLUMN_TITLE, newsInfo.title);
        values.put(DBConstant.TableList.COLUMN_PUBTIME, newsInfo.pubtime);
        values.put(DBConstant.TableList.COLUMN_SUMMARY, newsInfo.summary);
        values.put(DBConstant.TableList.COLUMN_TOPIC, newsInfo.topic);
        values.put(DBConstant.TableList.COLUMN_COUNTER, newsInfo.counter);
        values.put(DBConstant.TableList.COLUMN_COMMENTS, newsInfo.comments);
        values.put(DBConstant.TableList.COLUMN_RATINGS, newsInfo.ratings);
        values.put(DBConstant.TableList.COLUMN_SCORE, newsInfo.score);
        values.put(DBConstant.TableList.COLUMN_RATINGS_STORY, newsInfo.ratings_story);
        values.put(DBConstant.TableList.COLUMN_SCORE_STORY, newsInfo.score_story);
        values.put(DBConstant.TableList.COLUMN_TOPIC_LOGO, newsInfo.topic_logo);
        values.put(DBConstant.TableList.COLUMN_THUMB, newsInfo.thumb);
        values.put(DBConstant.TableList.COLUMN_IS_READ, false + "");
        values.put(DBConstant.TableList.COLUMN_IS_COLLECTED, false + "");
        values.put(DBConstant.UNIVERSAL_COLUMN_DB_UPDATE_TIME, System.currentTimeMillis());
        if (isUpdate) {
            values.remove(DBConstant.TableList.COLUMN_SID);
            values.remove(DBConstant.TableList.COLUMN_TITLE);
            values.remove(DBConstant.TableList.COLUMN_PUBTIME);
            values.remove(DBConstant.TableList.COLUMN_SUMMARY);
            values.remove(DBConstant.TableList.COLUMN_TOPIC);
            values.remove(DBConstant.TableList.COLUMN_TOPIC_LOGO);
            values.remove(DBConstant.TableList.COLUMN_THUMB);
            values.remove(DBConstant.TableList.COLUMN_IS_READ);
            values.remove(DBConstant.TableList.COLUMN_IS_COLLECTED);
        }
        return values;
    }

}