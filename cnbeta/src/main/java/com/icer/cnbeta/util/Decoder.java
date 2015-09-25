package com.icer.cnbeta.util;

public class Decoder {

    // 热门话题 clear
    static String topicNav() {
        return "app_key=10000" + "&format=json" + "&method=Article.NavList"
                + "&timestamp=" + getTimeStamp() + "&v=1.0";
    }

    // 新闻列表 clear
    static String list(String end_sid) {
        if (end_sid != null && end_sid.length() > 0) {
            return "app_key=10000" + "&end_sid=" + end_sid + "&format=json" + "&method=Article.Lists"
                    + "&timestamp=" + getTimeStamp() + "&topicid=0" + "&v=1.0";
        } else {
            return "app_key=10000" + "&format=json" + "&method=Article.Lists"
                    + "&timestamp=" + getTimeStamp() + "&v=1.0";
        }
    }

    // 新闻内容 clear
    static String content(String sid) {
        return "app_key=10000" + "&format=json" + "&method=Article.NewsContent"
                + "&sid=" + sid + "&timestamp=" + getTimeStamp() + "&v=1.0";
    }

    // 新闻评论 clear
    static String comment(String pageIndex, String sid) {
        return "app_key=10000" + "&format=json" + "&method=Article.Comment"
                + "&page=" + pageIndex + "&sid=" + sid + "&timestamp="
                + getTimeStamp() + "&v=1.0";
    }

    // 我的评论 clear
    static String myComment(String myComment, String sid) {
        return "app_key=10000" + "&content=" + myComment + "&format=json"
                + "&method=Article.DoCmt" + "&op=publish" + "&sid=" + sid
                + "&timestamp=" + getTimeStamp() + "&v=1.0";
    }

    // TOP10 本月TOP10 clear
    static String top10() {
        return "app_key=10000" + "&format=json" + "&method=Article.Top10"
                + "&timestamp=" + getTimeStamp() + "&v=1.0";
    }

    // 热门评论 clear
    static String recommendComment() {
        return "app_key=10000" + "&format=json"
                + "&method=Article.RecommendComment" + "&timestamp="
                + getTimeStamp() + "&v=1.0";
    }

    // 今日热读 clear
    static String todayRankRead() {
        return "app_key=10000" + "&format=json" + "&method=Article.TodayRank"
                + "&timestamp=" + getTimeStamp() + "&type=counter" + "&v=1.0";
    }

    // 今日热评 clear
    static String todayRankComment() {
        return "app_key=10000" + "&format=json" + "&method=Article.TodayRank"
                + "&timestamp=" + getTimeStamp() + "&type=comments" + "&v=1.0";
    }

    // 今日热推 clear
    static String todayRankRecommend() {
        return "app_key=10000" + "&format=json" + "&method=Article.TodayRank"
                + "&timestamp=" + getTimeStamp() + "&type=dig" + "&v=1.0";
    }

    // 主题新闻 clear
    static String topicList(String topicId) {
        return "app_key=10000" + "&format=json" + "&method=Article.Lists"
                + "&timestamp=" + getTimeStamp() + "&topicid=" + topicId
                + "&v=1.0";
    }

    private static String getTimeStamp() {
        return Long.valueOf(System.currentTimeMillis() / 1000L).toString();
    }

}
