package com.icer.cnbeta.volley.entity;

/**
 * Created by icer on 2015-09-28.
 */
public class NewsContent {
    public String sid;
    public String catid;
    public String topic;
    public String aid;
    public String title;
    public String style;
    public String keywords;
    public String hometext;
    public String listorder;
    public String comments;
    public String counter;
    public String good;
    public String bad;
    public String score;
    public String ratings;
    public String score_story;
    public String ratings_story;
    public String elite;
    public String status;
    public String inputtime;
    public String updatetime;
    public String thumb;
    public String source;
    public String data_id;
    public String bodytext;
    public String time;

    @Override
    public String toString() {
        return "NewsContent{" +
                "sid='" + sid + '\'' +
                ", catid='" + catid + '\'' +
                ", topic='" + topic + '\'' +
                ", aid='" + aid + '\'' +
                ", title='" + title + '\'' +
                ", style='" + style + '\'' +
                ", keywords='" + keywords + '\'' +
                ", hometext='" + hometext + '\'' +
                ", listorder='" + listorder + '\'' +
                ", comments='" + comments + '\'' +
                ", counter='" + counter + '\'' +
                ", good='" + good + '\'' +
                ", bad='" + bad + '\'' +
                ", score='" + score + '\'' +
                ", ratings='" + ratings + '\'' +
                ", score_story='" + score_story + '\'' +
                ", ratings_story='" + ratings_story + '\'' +
                ", elite='" + elite + '\'' +
                ", status='" + status + '\'' +
                ", inputtime='" + inputtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", thumb='" + thumb + '\'' +
                ", source='" + source + '\'' +
                ", data_id='" + data_id + '\'' +
                ", bodytext='" + bodytext + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
