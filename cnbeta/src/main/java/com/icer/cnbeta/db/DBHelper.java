package com.icer.cnbeta.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.icer.cnbeta.app.AppConfig;

import java.util.ArrayList;

/**
 * Created by icer on 2015-09-30.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "cnBeta.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, AppConfig.VERSION_DB);
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
        res += DBConstant.UNIVERSAL_COLUMN_PRIMARY_KEY + " INTEGER PRIMARY KEY, ";
        for (int i = 0; i < columns.size(); i++) {
            res += columns.get(i) + " TEXT";
            if (i != columns.size() - 1)
                res += ", ";
            else
                res += ");";
        }
        return res;
    }
}