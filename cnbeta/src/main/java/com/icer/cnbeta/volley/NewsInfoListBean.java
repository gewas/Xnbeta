package com.icer.cnbeta.volley;

import com.icer.cnbeta.volley.entity.NewsInfo;

import java.util.ArrayList;

/**
 * Created by icer on 2015-09-25.
 */
public class NewsInfoListBean extends BaseBean {

    public ArrayList<NewsInfo> result;

    @Override
    public String toString() {
        return super.toString() + "NewsInfoListBean{" +
                "result=" + result +
                '}';
    }
}
