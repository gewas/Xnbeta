package com.icer.cnbeta.volley.entity;

import android.database.Cursor;

import com.icer.cnbeta.db.DBConstant;

/**
 * Created by icer on 2015-09-25.
 */
public class NewsInfo {
    public String sid;
    public String title;
    public String pubtime;
    public String summary;
    public String topic;
    public String counter;
    public String comments;
    public String ratings;
    public String score;
    public String ratings_story;
    public String score_story;
    public String topic_logo;
    public String thumb;

    public NewsInfo() {

    }

    public NewsInfo(Cursor cursor) {
        sid = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_SID));
        title = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_TITLE));
        pubtime = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_PUBTIME));
        summary = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_SUMMARY));
        topic = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_TOPIC));
        counter = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_COUNTER));
        comments = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_COMMENTS));
        ratings = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_RATINGS));
        score = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_SCORE));
        ratings_story = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_RATINGS_STORY));
        score_story = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_SCORE_STORY));
        topic_logo = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_TOPIC_LOGO));
        thumb = cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_THUMB));
        setIsRead(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DBConstant.TableList.COLUMN_IS_READ))));
    }

    private boolean isRead;

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "sid='" + sid + '\'' +
                ", title='" + title + '\'' +
                ", pubtime='" + pubtime + '\'' +
                ", summary='" + summary + '\'' +
                ", topic='" + topic + '\'' +
                ", counter='" + counter + '\'' +
                ", comments='" + comments + '\'' +
                ", ratings='" + ratings + '\'' +
                ", score='" + score + '\'' +
                ", ratings_story='" + ratings_story + '\'' +
                ", score_story='" + score_story + '\'' +
                ", topic_logo='" + topic_logo + '\'' +
                ", thumb='" + thumb + '\'' +
                '}';
    }
}
