package com.icer.cnbeta.db;

import java.util.ArrayList;

/**
 * Created by icer on 2015-09-25.
 */
public class DBConstant {

    public static final String UNIVERSAL_COLUMN_PRIMARY_KEY = "_ID";
    public static final String UNIVERSAL_COLUMN_DB_UPDATE_TIME = "db_updateTime";

    public static class TableList {

        public static final String TABLE_NAME = "t_list";

        public static final String COLUMN_SID = "sid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PUBTIME = "pubtime";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_TOPIC = "topic";
        public static final String COLUMN_COUNTER = "counter";
        public static final String COLUMN_COMMENTS = "comments";
        public static final String COLUMN_RATINGS = "ratings";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_RATINGS_STORY = "ratings_story";
        public static final String COLUMN_SCORE_STORY = "score_story";
        public static final String COLUMN_TOPIC_LOGO = "topic_logo";
        public static final String COLUMN_THUMB = "thumb";
        public static final String COLUMN_IS_READ = "isRead";
        public static final String COLUMN_IS_COLLECTED = "isCollected";
    }

    public static ArrayList<String> getColumnsList() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add(TableList.COLUMN_SID);
        columns.add(TableList.COLUMN_TITLE);
        columns.add(TableList.COLUMN_PUBTIME);
        columns.add(TableList.COLUMN_SUMMARY);
        columns.add(TableList.COLUMN_TOPIC);
        columns.add(TableList.COLUMN_COUNTER);
        columns.add(TableList.COLUMN_COMMENTS);
        columns.add(TableList.COLUMN_RATINGS);
        columns.add(TableList.COLUMN_SCORE);
        columns.add(TableList.COLUMN_RATINGS_STORY);
        columns.add(TableList.COLUMN_SCORE_STORY);
        columns.add(TableList.COLUMN_TOPIC_LOGO);
        columns.add(TableList.COLUMN_THUMB);
        columns.add(TableList.COLUMN_IS_READ);
        columns.add(TableList.COLUMN_IS_COLLECTED);
        columns.add(UNIVERSAL_COLUMN_DB_UPDATE_TIME);
        return columns;
    }

    public static class TableContent {

        public static final String TABLE_NAME = "t_content";

        public static final String COLUMN_SID = "sid";
        public static final String COLUMN_CATID = "catid";
        public static final String COLUMN_TOPIC = "topic";
        public static final String COLUMN_AID = "aid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_STYLE = "style";
        public static final String COLUMN_KEYWORDS = "keywords";
        public static final String COLUMN_HOMETEXT = "hometext";
        public static final String COLUMN_LISTORDER = "listorder";
        public static final String COLUMN_COMMENTS = "comments";
        public static final String COLUMN_COUNTER = "counter";
        public static final String COLUMN_GOOD = "good";
        public static final String COLUMN_BAD = "bad";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_RATINGS = "ratings";
        public static final String COLUMN_SCORE_STORY = "score_story";
        public static final String COLUMN_RATINGS_STORY = "ratings_story";
        public static final String COLUMN_ELITE = "elite";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_INPUTTIME = "inputtime";
        public static final String COLUMN_UPDATETIME = "updatetime";
        public static final String COLUMN_THUMB = "thumb";
        public static final String COLUMN_SOURCE = "source";
        public static final String COLUMN_DATA_ID = "data_id";
        public static final String COLUMN_BODYTEXT = "bodytext";
        public static final String COLUMN_TIME = "time";
    }

    public static ArrayList<String> getColumnsContent() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add(TableContent.COLUMN_SID);
        columns.add(TableContent.COLUMN_CATID);
        columns.add(TableContent.COLUMN_TOPIC);
        columns.add(TableContent.COLUMN_AID);
        columns.add(TableContent.COLUMN_TITLE);
        columns.add(TableContent.COLUMN_STYLE);
        columns.add(TableContent.COLUMN_KEYWORDS);
        columns.add(TableContent.COLUMN_HOMETEXT);
        columns.add(TableContent.COLUMN_LISTORDER);
        columns.add(TableContent.COLUMN_COMMENTS);
        columns.add(TableContent.COLUMN_COUNTER);
        columns.add(TableContent.COLUMN_GOOD);
        columns.add(TableContent.COLUMN_BAD);
        columns.add(TableContent.COLUMN_SCORE);
        columns.add(TableContent.COLUMN_RATINGS);
        columns.add(TableContent.COLUMN_SCORE_STORY);
        columns.add(TableContent.COLUMN_RATINGS_STORY);
        columns.add(TableContent.COLUMN_ELITE);
        columns.add(TableContent.COLUMN_STATUS);
        columns.add(TableContent.COLUMN_INPUTTIME);
        columns.add(TableContent.COLUMN_UPDATETIME);
        columns.add(TableContent.COLUMN_THUMB);
        columns.add(TableContent.COLUMN_SOURCE);
        columns.add(TableContent.COLUMN_DATA_ID);
        columns.add(TableContent.COLUMN_BODYTEXT);
        columns.add(TableContent.COLUMN_TIME);
        columns.add(UNIVERSAL_COLUMN_DB_UPDATE_TIME);
        return columns;
    }
}
