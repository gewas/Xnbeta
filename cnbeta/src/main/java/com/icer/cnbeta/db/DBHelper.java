package com.icer.cnbeta.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.icer.cnbeta.app.AppConfig;
import com.icer.cnbeta.volley.entity.Latest;

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

    public ArrayList<Latest> getList() {
        ArrayList<Latest> res = null;
        Cursor cursor = mResolver.query(Uri.parse(DBProvider.URI_LIST), null, null, null, DBConstant.TableList.COLUMN_SID + " DESC");
        if (cursor != null) {
            int i = 0;
            while (cursor.moveToNext()) {
                if (res == null) res = new ArrayList<>();
                if (i == 20) break;
                Latest latest = new Latest();
                latest.sid = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_SID));
                latest.title = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_TITLE));
                latest.pubtime = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_PUBTIME));
                latest.summary = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_SUMMARY));
                latest.topic = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_TOPIC));
                latest.counter = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_COUNTER));
                latest.comments = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_COMMENTS));
                latest.ratings = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_RATINGS));
                latest.score = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_SCORE));
                latest.ratings_story = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_RATINGS_STORY));
                latest.score_story = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_SCORE_STORY));
                latest.topic_logo = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_TOPIC_LOGO));
                latest.thumb = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_THUMB));
                latest.setIsRead(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_IS_READ))));
                res.add(latest);
                i++;
            }
            cursor.close();
        }
        return res;
    }

    public int saveList(ArrayList<Latest> data) {
        int count = 0;

        return count;
    }

    private void insertList(Latest latest) {
        mResolver.insert(Uri.parse(DBProvider.URI_LIST), getListContentValues(latest));
    }

    private void updateList(Latest latest) {
    }

    private ContentValues getListContentValues(Latest latest) {
        ContentValues values = new ContentValues();
        values.put(DBConstant.TableList.COLUMN_SID, latest.sid);
        values.put(DBConstant.TableList.COLUMN_TITLE, latest.title);
        values.put(DBConstant.TableList.COLUMN_PUBTIME, latest.pubtime);
        values.put(DBConstant.TableList.COLUMN_SUMMARY, latest.summary);
        values.put(DBConstant.TableList.COLUMN_TOPIC, latest.topic);
        values.put(DBConstant.TableList.COLUMN_COUNTER, latest.counter);
        values.put(DBConstant.TableList.COLUMN_COMMENTS, latest.comments);
        values.put(DBConstant.TableList.COLUMN_RATINGS, latest.ratings);
        values.put(DBConstant.TableList.COLUMN_SCORE, latest.score);
        values.put(DBConstant.TableList.COLUMN_RATINGS_STORY, latest.ratings_story);
        values.put(DBConstant.TableList.COLUMN_SCORE_STORY, latest.score_story);
        values.put(DBConstant.TableList.COLUMN_TOPIC_LOGO, latest.topic_logo);
        values.put(DBConstant.TableList.COLUMN_THUMB, latest.thumb);
        values.put(DBConstant.TableList.COLUMN_IS_READ, latest.isRead());
        values.put(DBConstant.TableList.COLUMN_IS_COLLECTED, false);
        values.put(DBConstant.UNIVERSAL_COLUMN_DB_UPDATE_TIME, System.currentTimeMillis());
        return values;
    }

}