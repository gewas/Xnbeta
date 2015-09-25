package com.icer.cnbeta.util;

import android.util.Log;

import com.icer.cnbeta.app.AppConfig;
import com.icer.cnbeta.app.AppConstants;


public class URLUtil {
    public static final String TAG = URLUtil.class.getSimpleName();
    public static final int ID = URLUtil.class.getName().hashCode();

    private static String getRealUrlString(String str) {
        String url = "http://api.cnbeta.com/capi?" + str + "&sign="
                + Coder.coder(str + AppConstants.SECRET_KEY);
        if (AppConfig.IS_DEBUG_MODE)
            Log.e(TAG, url);
        return url;
    }

    /**
     * 热门话题
     */
    public static String getNav() {
        return getRealUrlString(Decoder.topicNav());
    }

    /**
     * 最新新闻
     */
    public static String getList(String end_sid) {
        return getRealUrlString(Decoder.list(end_sid));
    }

    /**
     * 新闻内容
     *
     * @param sid 新闻的sid
     */
    public static String getContent(String sid) {
        return getRealUrlString(Decoder.content(sid));
    }

    /**
     * 新闻评论
     *
     * @param pageIndex 评论页码,从1开始,第一次进入评论设置为1
     * @param sid       新闻的sid
     */
    public static String getComment(String pageIndex, String sid) {
        return getRealUrlString(Decoder.comment(pageIndex, sid));
    }

    /**
     * 我的评论!!参数有误!!
     *
     * @param myComment 评论内容
     * @param sid       新闻sid
     */
    public static String getMyComment(String myComment, String sid) {
        return getRealUrlString(Decoder.myComment(myComment, sid));
    }

    /**
     * 本月TOP10
     */
    public static String getTop10() {
        return getRealUrlString(Decoder.top10());
    }

    /**
     * 热门评论
     */
    public static String getRecommendComment() {
        return getRealUrlString(Decoder.recommendComment());
    }

    /**
     * 今日热读
     */
    public static String getTodayRead() {
        return getRealUrlString(Decoder.todayRankRead());
    }

    /**
     * 今日热评
     */
    public static String getTodayComment() {
        return getRealUrlString(Decoder.todayRankComment());
    }

    /**
     * 今日热推
     */
    public static String getTodayRecommend() {
        return getRealUrlString(Decoder.todayRankRecommend());
    }

    /**
     * 主题新闻
     *
     * @param topicId 话题id
     */
    public static String getTopicList(String topicId) {
        return getRealUrlString(Decoder.topicList(topicId));
    }

}
