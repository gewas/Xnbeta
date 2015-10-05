package com.icer.cnbeta.volley.entity;

/**
 * Created by icer on 2015/10/5.
 */
public class NewsComment {
    public String tid;
    public String pid;
    public String username;
    public String content;
    public String created_time;
    public String support;
    public String against;

    @Override
    public String toString() {
        return "NewsComment{" +
                "tid='" + tid + '\'' +
                ", pid='" + pid + '\'' +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", created_time='" + created_time + '\'' +
                ", support='" + support + '\'' +
                ", against='" + against + '\'' +
                '}';
    }
}
