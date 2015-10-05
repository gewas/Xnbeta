package com.icer.cnbeta.volley;

import com.icer.cnbeta.volley.entity.NewsComment;

import java.util.ArrayList;

/**
 * Created by icer on 2015/10/5.
 */
public class NewsCommentsBean extends BaseBean {

    public ArrayList<NewsComment> result;

    @Override
    public String toString() {
        return super.toString() + "NewsCommentsBean{" +
                "result=" + result +
                '}';
    }
}
