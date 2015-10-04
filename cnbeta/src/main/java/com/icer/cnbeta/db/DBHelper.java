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
import com.icer.cnbeta.volley.entity.NewsContent;
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
        String orderBy = DBConstant.TableList.COLUMN_SID + " DESC";
        if (lastSid != null) {
            selection = DBConstant.TableList.COLUMN_SID + "<?";
            selectionArgs = new String[]{lastSid};
        }
        if (isCollected) {
            selection = DBConstant.TableList.COLUMN_IS_COLLECTED + "=?";
            selectionArgs = new String[]{true + ""};
            if (lastSid != null) {
                selection += " and " + DBConstant.TableList.COLUMN_SID + "<?";
                selectionArgs = new String[]{true + "", lastSid};
            }
        }
        Cursor cursor = mResolver.query(Uri.parse(DBProvider.URI_LIST), null, selection, selectionArgs, orderBy);
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
        values.put(DBConstant.UNIVERSAL_COLUMN_DB_UPDATE_TIME, System.currentTimeMillis() + "");
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
            values.remove(DBConstant.UNIVERSAL_COLUMN_DB_UPDATE_TIME);
        }
        return values;
    }

    public void updateNewsIsCollected(String sid, boolean bool) {
        ContentValues values = new ContentValues();
        values.put(DBConstant.TableList.COLUMN_IS_COLLECTED, bool + "");
        values.put(DBConstant.UNIVERSAL_COLUMN_DB_UPDATE_TIME, System.currentTimeMillis() + "");
        mResolver.update(Uri.parse(DBProvider.URI_LIST), values, DBConstant.TableList.COLUMN_SID + "=?", new String[]{sid});
    }

    public boolean isNewsCollected(String sid) {
        boolean res = false;
        Cursor cursor = mResolver.query(Uri.parse(DBProvider.URI_LIST), null,
                DBConstant.TableList.COLUMN_SID + "=?", new String[]{sid}, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                res = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_IS_COLLECTED)));
            }
            cursor.close();
        }
        return res;
    }

    public void saveNewsContent(NewsContent newsContent) {
        if (!queryNewsContent(newsContent.sid))
            insertNewsContent(newsContent);
    }

    public NewsContent getLocalNewsContent(String sid) {
        NewsContent newsContent = null;
        Cursor cursor = mResolver.query(Uri.parse(DBProvider.URI_CONTENT), null, DBConstant.TableContent.COLUMN_SID + "=?", new String[]{sid}, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                newsContent = new NewsContent(cursor);
            }
            cursor.close();
        }
        return newsContent;
    }

    private boolean queryNewsContent(String sid) {
        boolean res = false;
        Cursor cursor = mResolver.query(Uri.parse(DBProvider.URI_CONTENT), null, DBConstant.TableContent.COLUMN_SID + "=?", new String[]{sid}, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                res = true;
            }
            cursor.close();
        }
        return res;
    }

    private void insertNewsContent(NewsContent newsContent) {
        mResolver.insert(Uri.parse(DBProvider.URI_CONTENT), getContentValuesNewsContent(newsContent));
    }

    private ContentValues getContentValuesNewsContent(NewsContent newsContent) {
        ContentValues values = new ContentValues();
        values.put(DBConstant.TableContent.COLUMN_SID, newsContent.sid);
        values.put(DBConstant.TableContent.COLUMN_CATID, newsContent.catid);
        values.put(DBConstant.TableContent.COLUMN_TOPIC, newsContent.topic);
        values.put(DBConstant.TableContent.COLUMN_AID, newsContent.aid);
        values.put(DBConstant.TableContent.COLUMN_TITLE, newsContent.title);
        values.put(DBConstant.TableContent.COLUMN_STYLE, newsContent.style);
        values.put(DBConstant.TableContent.COLUMN_KEYWORDS, newsContent.keywords);
        values.put(DBConstant.TableContent.COLUMN_HOMETEXT, newsContent.hometext);
        values.put(DBConstant.TableContent.COLUMN_LISTORDER, newsContent.listorder);
        values.put(DBConstant.TableContent.COLUMN_COMMENTS, newsContent.comments);
        values.put(DBConstant.TableContent.COLUMN_COUNTER, newsContent.counter);
        values.put(DBConstant.TableContent.COLUMN_GOOD, newsContent.good);
        values.put(DBConstant.TableContent.COLUMN_BAD, newsContent.bad);
        values.put(DBConstant.TableContent.COLUMN_SCORE, newsContent.score);
        values.put(DBConstant.TableContent.COLUMN_RATINGS, newsContent.ratings);
        values.put(DBConstant.TableContent.COLUMN_SCORE_STORY, newsContent.score_story);
        values.put(DBConstant.TableContent.COLUMN_RATINGS_STORY, newsContent.ratings_story);
        values.put(DBConstant.TableContent.COLUMN_ELITE, newsContent.elite);
        values.put(DBConstant.TableContent.COLUMN_STATUS, newsContent.status);
        values.put(DBConstant.TableContent.COLUMN_INPUTTIME, newsContent.inputtime);
        values.put(DBConstant.TableContent.COLUMN_UPDATETIME, newsContent.updatetime);
        values.put(DBConstant.TableContent.COLUMN_THUMB, newsContent.thumb);
        values.put(DBConstant.TableContent.COLUMN_SOURCE, newsContent.source);
        values.put(DBConstant.TableContent.COLUMN_DATA_ID, newsContent.data_id);
        values.put(DBConstant.TableContent.COLUMN_BODYTEXT, newsContent.bodytext);
        values.put(DBConstant.TableContent.COLUMN_TIME, newsContent.time);
        values.put(DBConstant.UNIVERSAL_COLUMN_DB_UPDATE_TIME, System.currentTimeMillis());
        return values;
    }

}