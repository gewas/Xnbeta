package com.icer.cnbeta.volley.entity;

/**
 * Created by icer on 2015-09-25.
 */
public class Latest {
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

    @Override
    public String toString() {
        return "Latest{" +
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
