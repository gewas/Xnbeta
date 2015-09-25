package com.icer.cnbeta.volley;

import com.icer.cnbeta.volley.entity.Latest;

import java.util.ArrayList;

/**
 * Created by icer on 2015-09-25.
 */
public class LatestListBean extends BaseBean {
    public static final String RESULT_SUCCESS = "success";

    public ArrayList<Latest> result;

    public boolean isSucceed() {
        return RESULT_SUCCESS.equals(result);
    }

    @Override
    public String toString() {
        return "LatestListBean{" +
                "result=" + result +
                '}' + super.toString();
    }
}
