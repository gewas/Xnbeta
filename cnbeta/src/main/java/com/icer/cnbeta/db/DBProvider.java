package com.icer.cnbeta.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.icer.cnbeta.app.AppApplication;

/**
 * Database ContentProvider
 * Created by icer on 2015-09-30.
 */
public class DBProvider extends ContentProvider {

    public static final String AUTHORITIES = "com.icer.cnbeta.provider.db";

    private final int Match_CODE_LIST = 1;
    private final int Match_CODE_CONTENT = 2;

    private SQLiteDatabase mSQLiteDatabase;
    private UriMatcher mUriMatcher;

    @Override
    public boolean onCreate() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITIES, DBConstant.TableList.TABLE_NAME, Match_CODE_LIST);
        mUriMatcher.addURI(AUTHORITIES, DBConstant.TableContent.TABLE_NAME, Match_CODE_CONTENT);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (mSQLiteDatabase == null)
            mSQLiteDatabase = new DBHelper(AppApplication.getInstance().getApplicationContext()).getWritableDatabase();
        Cursor cursor = null;
        switch (mUriMatcher.match(uri)) {
            case Match_CODE_LIST:
                cursor = mSQLiteDatabase.query(DBConstant.TableList.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case Match_CODE_CONTENT:
                cursor = mSQLiteDatabase.query(DBConstant.TableContent.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        String typeMIME = "vnd.android.cursor";
        switch (mUriMatcher.match(uri)) {
            case Match_CODE_LIST:
                typeMIME += ".dir/vnd." + AUTHORITIES + "." + DBConstant.TableList.TABLE_NAME;
                break;

            case Match_CODE_CONTENT:
                typeMIME += ".dir/vnd." + AUTHORITIES + "." + DBConstant.TableContent.TABLE_NAME;
                break;
        }
        return typeMIME;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (mSQLiteDatabase == null)
            mSQLiteDatabase = new DBHelper(AppApplication.getInstance().getApplicationContext()).getWritableDatabase();
        switch (mUriMatcher.match(uri)) {
            case Match_CODE_LIST:
                mSQLiteDatabase.insert(DBConstant.TableList.TABLE_NAME, null, values);
                break;

            case Match_CODE_CONTENT:
                mSQLiteDatabase.insert(DBConstant.TableContent.TABLE_NAME, null, values);
                break;
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (mSQLiteDatabase == null)
            mSQLiteDatabase = new DBHelper(AppApplication.getInstance().getApplicationContext()).getWritableDatabase();
        int count = 0;
        switch (mUriMatcher.match(uri)) {
            case Match_CODE_LIST:
                count = mSQLiteDatabase.delete(DBConstant.TableList.TABLE_NAME, selection, selectionArgs);
                break;

            case Match_CODE_CONTENT:
                count = mSQLiteDatabase.delete(DBConstant.TableContent.TABLE_NAME, selection, selectionArgs);
                break;
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (mSQLiteDatabase == null)
            mSQLiteDatabase = new DBHelper(AppApplication.getInstance().getApplicationContext()).getWritableDatabase();
        int count = 0;
        switch (mUriMatcher.match(uri)) {
            case Match_CODE_LIST:
                count = mSQLiteDatabase.update(DBConstant.TableList.TABLE_NAME, values, selection, selectionArgs);
                break;

            case Match_CODE_CONTENT:
                count = mSQLiteDatabase.update(DBConstant.TableContent.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        return count;
    }
}
