package com.icer.cnbeta.volley;

import com.icer.cnbeta.volley.entity.Latest;

import java.util.ArrayList;

/**
 * Created by icer on 2015-09-25.
 */
public class LatestListBean extends BaseBean {

    public ArrayList<Latest> result;

    @Override
    public String toString() {
        return super.toString() + "LatestListBean{" +
                "result=" + result +
                '}';
    }
}
